package com.cloud.gray.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.cloud.cacheL2.dao.JedisCommands;
import com.cloud.cacheL2.exception.RedisDBException;
import com.cloud.cacheL2.lock.DTSLock;
import com.cloud.common.constant.Constants;
import com.cloud.common.utils.JsonUtil;
import com.cloud.common.utils.ReflectionUtil;
import com.cloud.gray.bean.GrayServiceInfo;
import com.cloud.gray.bean.GrayServicesRulesInfo;
import com.cloud.gray.bean.StrategyInfo;
import com.cloud.gray.config.GrayProperties;
import com.cloud.gray.lb.GrayRule;
import com.cloud.gray.schedule.Scheduler;

/**
 * 当服务启动完成后，检查当前实例是否为灰度实例，如果是则相关信息更新到Redis中。 以便其他服务实例能够同步到最新的灰度规则数据；
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月26日
 */

@Component
public class GrayInit implements ApplicationRunner
{
    private static final Logger log = LoggerFactory.getLogger(GrayInit.class);

    @Autowired
    private EurekaRegistration eurekaRegistration;

    @Autowired
    private GrayServicesRulesInfo curGrayServicesRulesInfo;
    
    @Autowired
    private JedisCommands jedisCommands;
    
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private GrayProperties grayProperties;

    @Value("${spring.application.name:}")
    private String serviceId;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        GrayServicesRulesInfo grayServicesRulesInfo = scheduler.scheduleFetchServiceGrayRules();
        if(grayServicesRulesInfo != null)
        {
            reConstRuleData(grayServicesRulesInfo);
            System.err.println("***************"+ this.getClass() + " : " + JsonUtil.beanToJson(grayServicesRulesInfo));
            
            Thread thread = new Thread(new Runnable(){
    
                @Override
                public void run()
                {
                    /**
                     * 将服务实例的策略信息写入Redis中 
                     */
                    writeRuleInfo(grayServicesRulesInfo);
                }
            });
            
            thread.start();
        }else 
        {
            reConstRuleData(curGrayServicesRulesInfo);
            writeRuleInfo(curGrayServicesRulesInfo);
        }
    }
    
    private void reConstRuleData(GrayServicesRulesInfo grayServicesRulesInfo){
        GrayServiceInfo grayServiceInfo = grayServicesRulesInfo.getServiceGrayStrategyMap().get(serviceId);
        // 获取当前实例的instanceId
//        eurekaRegistration.getInstanceConfig().getInitialStatus();
        CloudEurekaClient client = eurekaRegistration.getEurekaClient();
        String currentInstanceId = client.getApplicationInfoManager().getInfo().getInstanceId();
//        String currentInstanceId = eurekaRegistration.getInstanceConfig().getInstanceId();
        /**
         * 如果当前实例是一个灰度实例，更新Redis中存储的灰度规则数据
         */
        if (grayProperties.isGrayable())
        {
            String grayStrategy = grayProperties.getGrayStrategyClassName();
            Object instance = ReflectionUtil.createInstance(grayStrategy);

            if (instance == null || !(instance instanceof GrayRule))
            {
                log.warn("当前配置的灰度规则异常，请检查配置的规则是否正确，当前配置值为:{}", grayStrategy);
                return;
            }
            
            StrategyInfo strategyInfo = new StrategyInfo();
            strategyInfo.setStrategyImpClassName(grayStrategy);
            strategyInfo.setStrategyDetail(grayProperties.getGrayStrategyDetail());
            
            if (grayServiceInfo == null)
            {
                grayServiceInfo = new GrayServiceInfo();
                grayServiceInfo.setGrayable(true);
                grayServiceInfo.setServiceId(serviceId);
                grayServiceInfo.setStrategyInfo(strategyInfo);
                
                List<String> instanceIdList = new ArrayList<>();
                instanceIdList.add(currentInstanceId);
                grayServiceInfo.setInstanceIdList(instanceIdList);
                grayServicesRulesInfo.getServiceGrayStrategyMap().put(serviceId, grayServiceInfo);
            }else 
            {
                grayServiceInfo.setGrayable(true);
                grayServiceInfo.getStrategyInfo().setStrategyImpClassName(grayProperties.getGrayStrategyClassName());
                grayServiceInfo.getStrategyInfo().setStrategyDetail(grayProperties.getGrayStrategyDetail());
                Optional<String> optional = grayServiceInfo.getInstanceIdList().parallelStream().filter(item -> item.equals(currentInstanceId)).findAny();
                if(optional == null || !optional.isPresent())
                {
                    grayServiceInfo.getInstanceIdList().add(currentInstanceId);                        
                }
            }
        }else //当前启动的实例不是灰度实例
        {
            if (grayServiceInfo != null)
            {
                List<String> instanceIdList = grayServiceInfo.getInstanceIdList();
                instanceIdList.removeIf(item -> currentInstanceId.equals(item)); //将当前实例从灰度实例列表中删除
            }
        }
    }
    
    
    /**
     * 将服务实例的策略信息写入Redis中
     */
    private boolean writeRuleInfo(GrayServicesRulesInfo grayServicesRulesInfo)
    {
        curGrayServicesRulesInfo.setServiceGrayStrategyMap(grayServicesRulesInfo.getServiceGrayStrategyMap());
        DTSLock lock = new DTSLock(Constants.JMSA_GRAY_SERVICES_RULES);
        try
        {
            while(!lock.lock())
            {
                Thread.sleep(10);
            }
            String result = jedisCommands.set(SerializationUtils.serialize(Constants.JMSA_GRAY_SERVICES_RULES), 
                                SerializationUtils.serialize(grayServicesRulesInfo));
            System.out.println("==============>" + this.getClass() + " : result = " + result);
            return true;
        } catch (RedisDBException | InterruptedException e)
        {
            log.error("写入灰度实例规则失败.", e);
            e.printStackTrace();
        }finally{
            try
            {
                lock.unlock();
            } catch (RedisDBException e)
            {
                log.error("释放分布式锁失败.", e);
                e.printStackTrace();
            }   
        }
        
        return true;
    }
    
    
}
