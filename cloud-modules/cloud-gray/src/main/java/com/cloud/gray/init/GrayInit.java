package com.cloud.gray.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
import com.cloud.gray.enumeration.GrayConfigModel;
import com.cloud.gray.lb.GrayRule;
import com.cloud.gray.schedule.FetchRuleScheduler;

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
    private JedisCommands jedisCommand;

    @Autowired
    private FetchRuleScheduler scheduler;

    @Autowired
    private GrayProperties grayProperties;

    @Value("${spring.application.name:}")
    private String serviceId;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        DTSLock lock = new DTSLock(Constants.JMSA_GRAY_SERVICES_RULES);
        try
        {
            while(!lock.lock())
            {
                Thread.sleep(10);
            }
            GrayServicesRulesInfo grayServicesRulesInfo = scheduler.scheduleFetchServiceGrayRules();
    
            Map<String, String> metadata = eurekaRegistration.getMetadata();
            System.out.println("==================>metadata =  " + JsonUtil.beanToJson(metadata));
            //如果isGray的值为true，则表示当前实例为一个灰度实例
            if(metadata != null && "true".equalsIgnoreCase(metadata.get("isGray")))
            {
                //通过本地配置文件维护的方式
                if(grayProperties.getModel() == GrayConfigModel.LOCAL)
                {
                    constRuleDataForLocal(grayServicesRulesInfo);
                }else if(grayProperties.getModel() == GrayConfigModel.REMOTE) 
                {
                    //通过运维管理系统维护的方式
                    
                }
                
            }else 
            {
                //如果当前实例不为灰度实例，则检查当前服务是否有对应的灰度规则，存在则检查灰度实例列表中是否存在当前实例，如果存在则将其移除
                GrayServiceInfo grayServiceInfo = grayServicesRulesInfo.getServiceGrayStrategyMap().get(serviceId);
                if (grayServiceInfo != null)
                {
                    // 获取当前实例的instanceId
                    String currentInstanceId = eurekaRegistration.getInstanceConfig().getInstanceId();
                    List<String> instanceIdList = grayServiceInfo.getInstanceIdList();
                    instanceIdList.removeIf(item -> currentInstanceId.equals(item)); //将当前实例从灰度实例列表中删除
                }
            }
            curGrayServicesRulesInfo = grayServicesRulesInfo;
            
            Thread thread = new Thread(new Runnable(){
                
                @Override
                public void run()
                {
                    //将服务实例的策略信息写入Redis中 
                    writeRuleInfo(grayServicesRulesInfo);
                }
            });
            
            thread.start();
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
    }
    
    /**
     * 实例通过本地配置文件的方式设置灰度规则
     * @param grayServicesRulesInfo
     */
    private void constRuleDataForLocal(GrayServicesRulesInfo grayServicesRulesInfo){
        GrayServiceInfo grayServiceInfo = grayServicesRulesInfo.getServiceGrayStrategyMap().get(serviceId);
        // 获取当前实例的instanceId
        String currentInstanceId = eurekaRegistration.getInstanceConfig().getInstanceId();
        
        // 获取配置文件中配置的灰度规则
        String grayStrategy = grayProperties.getGrayStrategyClassName();
        Object instance = ReflectionUtil.createInstance(grayStrategy);

        if (instance == null || !(instance instanceof GrayRule))
        {
            log.warn("当前配置的灰度规则异常，请检查配置的规则是否正确，当前配置值为:{}", grayStrategy);
            return;
        }
   
        if (grayServiceInfo == null)
        {
            grayServiceInfo = new GrayServiceInfo();
            grayServiceInfo.setGrayable(true);
            grayServiceInfo.setServiceId(serviceId);

            StrategyInfo strategyInfo = new StrategyInfo();
            strategyInfo.setStrategyImpClassName(grayStrategy);
            strategyInfo.setStrategyDetail(grayProperties.getGrayStrategyDetail());
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
    }
    
    
    /**
     * 将服务实例的策略信息写入Redis中
     */
    private boolean writeRuleInfo(GrayServicesRulesInfo grayServicesRulesInfo)
    {
        try
        {
            String  result = jedisCommand.set(SerializationUtils.serialize(Constants.JMSA_GRAY_SERVICES_RULES), 
                                SerializationUtils.serialize(grayServicesRulesInfo));
            System.out.println("==============>" + this.getClass() + " : result = " + result);
            return true;
        } catch (RedisDBException e)
        {
            log.error("启动服务回写灰度规则失败!", e);
            e.printStackTrace();
        }
        
        return false;
    }
    
    
}
