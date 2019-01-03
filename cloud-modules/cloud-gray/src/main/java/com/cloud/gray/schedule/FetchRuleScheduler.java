package com.cloud.gray.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.cloud.cacheL2.dao.JedisCommands;
import com.cloud.common.constant.Constants;
import com.cloud.common.utils.ConvertUtil;
import com.cloud.common.utils.JsonUtil;
import com.cloud.gray.bean.GrayServicesRulesInfo;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月26日
 */
@Component
public class FetchRuleScheduler
{
    @Autowired
    private JedisCommands jedisCommands;

    @Autowired
    private GrayServicesRulesInfo grayServicesRulesInfo;

    /**
     * 获取服务的灰度策略数据，每两分钟从redis中获取一次
     */
    @Scheduled(fixedDelay = 2 * 60 * 1000, initialDelay=2 * 60 * 1000)
    public GrayServicesRulesInfo scheduleFetchServiceGrayRules()
    {
        try
        {
            byte[] object = jedisCommands.get(SerializationUtils.serialize(Constants.JMSA_GRAY_SERVICES_RULES));
            if(object == null)
            {
                return grayServicesRulesInfo;
            }
            GrayServicesRulesInfo newGrayServicesRulesInfo = ConvertUtil.convertToObject(SerializationUtils.deserialize(object), GrayServicesRulesInfo.class);
            if(newGrayServicesRulesInfo != null)
            {
                grayServicesRulesInfo = newGrayServicesRulesInfo;
            }
            System.out.println("===>" + this.getClass() + " : " + JsonUtil.beanToJson(grayServicesRulesInfo));
            return grayServicesRulesInfo;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
