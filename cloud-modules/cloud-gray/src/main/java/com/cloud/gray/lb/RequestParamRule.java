package com.cloud.gray.lb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.cloud.common.bean.RequestData;
import com.cloud.common.bean.RequestHeader;
import com.cloud.common.utils.JsonUtil;
import com.cloud.common.utils.StringUtil;
import com.cloud.context.ContextHolder;
import com.cloud.gray.RibbonRequestContext;
import com.cloud.gray.bean.GrayRequestParamsRuleBean;
import com.cloud.gray.bean.GrayServiceInfo;
import com.cloud.gray.bean.StrategyInfo;
import com.cloud.gray.enumeration.KVRelation;
import com.cloud.gray.enumeration.RuleRelation;
import com.netflix.loadbalancer.Server;

/**
 * 根据本次的请求参数信息确定路由的实例
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月27日
 */
public class RequestParamRule implements GrayRule
{
    private Random random = new Random();

    @SuppressWarnings("unchecked")
    public Server chooseServer(Object key, GrayLoadBalancer grayLoadBalancer,
            GrayServiceInfo grayServiceInfo)
    {
        Server server = null;

        List<Server> allServerList = grayLoadBalancer.getAllServers();// 获取全部server集合
        List<Server> grayInstanceList = new ArrayList<>();// 存储当前服务所有的灰度实例
        List<Server> normalInstanceList = new ArrayList<>();// 存储当前服务所有的常规实例

        // 根据当前获取到的服务实例列表信息，将所有实例分到灰度实例列表和常规实例列表中
        normalAndGrayInstanceGrouping(grayInstanceList, normalInstanceList,
                allServerList, grayServiceInfo);

        // RibbonRequestContext将当前请求对象存储在当前线程中
        RibbonRequestContext ribbonRequestContext = ContextHolder.getBean(RibbonRequestContext.class);
        if (ribbonRequestContext != null)
        {
            Object obj = ribbonRequestContext.get("httpEntity");
            HttpEntity<RequestData> httpEntity = null;
            if (obj != null && obj instanceof HttpEntity)
            {
                httpEntity = (HttpEntity<RequestData>) obj;
            }
            StrategyInfo strategyInfo = grayServiceInfo.getStrategyInfo();
            String strategyDetail = strategyInfo.getStrategyDetail();

            List<GrayRequestParamsRuleBean> listConditions = parseConditions(strategyDetail);
            RuleRelation preRuleRelation = null; // 前一个条件配置的与后一个条件的关系（AND/OR）
            boolean preResult = true; // 前一个条件执行的结果
            for (GrayRequestParamsRuleBean ruleBean : listConditions)
            {
                boolean curResult = validateRuleCondtion(ruleBean, httpEntity);
                if (preRuleRelation == null)
                {
                    preRuleRelation = ruleBean.getNextRuleRelation();
                    preResult = curResult;
                    continue;
                }
                if (RuleRelation.OR.getValue().equalsIgnoreCase(preRuleRelation.getValue()))
                {
                    preResult = preResult || curResult;
                } else if (RuleRelation.AND.getValue().equalsIgnoreCase(preRuleRelation.getValue()))
                {
                    preResult = preResult && curResult;
                }
            }

            // 如果规则条件检查结果为true则随机返回一个灰度实例
            if (preResult)
            {
                // 从灰度实例列表中随机选择一个返回
                int num = random.nextInt(grayInstanceList.size());
                return grayInstanceList.get(num);
            }
        }
        // 从常规实例中随机选择一个返回
        int num = random.nextInt(normalInstanceList.size());
        server = normalInstanceList.get(num);

        return server;
    }

    public boolean validateRuleCondtion(GrayRequestParamsRuleBean ruleBean,
            HttpEntity<RequestData> httpEntity)
    {
        String region = ruleBean.getRegion();
        String key = ruleBean.getKey();
        String value = ruleBean.getValue();
        KVRelation kvRelation = ruleBean.getKvRelation();

        // http header参数
        if ("header".equalsIgnoreCase(region))
        {
            HttpHeaders httpHeaders = httpEntity.getHeaders();
            List<String> listValue = httpHeaders.get(key);
            if (listValue != null && !listValue.isEmpty())
            {
                boolean eachResult = false;
                for (String headerValue : listValue)
                {
                    eachResult = validateKVRelation(value, kvRelation, headerValue);
                    if (eachResult)
                    {
                        return true;
                    }
                }
                return false;
            }
        } else if ("requestHead".equalsIgnoreCase(region))
        {
            RequestData requestData = httpEntity.getBody();
            RequestHeader requestHead = requestData.getHead();
            Map<String, Object> extend = requestHead.getExtend();
            Object headValue = extend.get(key);
            if (headValue == null)
            {
                return false;
            }
            return validateKVRelation(value, kvRelation, String.valueOf(headValue));
        } else if ("requestBody".equalsIgnoreCase(region))
        {
            RequestData requestData = httpEntity.getBody();
            Map<String, Object> requestBody = requestData.getBody();
            Object bodyValue = requestBody.get(key);
            if (bodyValue == null)
            {
                return false;
            }
            return validateKVRelation(value, kvRelation, String.valueOf(bodyValue));
        }

        return false;
    }

    private boolean validateKVRelation(String ruleValue, KVRelation kvRelation, String inputValue)
    {
        if (KVRelation.EQ.getValue().equalsIgnoreCase(kvRelation.getValue()))
        {
            return inputValue.compareTo(ruleValue) == 0;
        } else if (KVRelation.LT.getValue().equalsIgnoreCase(kvRelation.getValue()))
        {
            // 此处配置的值是数值类型还是字符串类型
            if (StringUtil.isNumeric(ruleValue))
            {
                double rvalue = Double.parseDouble(ruleValue);
                double ivalue = Double.parseDouble(inputValue);
                return ivalue < rvalue;
            } else
            {
                return inputValue.compareTo(ruleValue) < 0;
            }

        } else if (KVRelation.GT.getValue().equalsIgnoreCase(kvRelation.getValue()))
        {
            if (StringUtil.isNumeric(ruleValue))
            {
                double rvalue = Double.parseDouble(ruleValue);
                double ivalue = Double.parseDouble(inputValue);
                return ivalue > rvalue;
            } else
            {
                return inputValue.compareTo(ruleValue) > 0;
            }
        }

        return false;
    }

    private List<GrayRequestParamsRuleBean> parseConditions(
            String strategyDetail)
    {
        List<GrayRequestParamsRuleBean> list = new ArrayList<>();
        String[] conditions = strategyDetail.split(";");
        for (String condition : conditions)
        {
            GrayRequestParamsRuleBean bean = JsonUtil.jsonToBean(condition, GrayRequestParamsRuleBean.class);
            list.add(bean);
        }

        return list;
    }

    private void normalAndGrayInstanceGrouping(List<Server> grayInstanceList,
            List<Server> normalInstanceList, List<Server> allServerList,
            GrayServiceInfo grayServiceInfo)
    {

        List<String> grayInstanceIdList = grayServiceInfo.getInstanceIdList();
        Server tempServer = null;
        for (Server s : allServerList)
        {
            String instanceId = s.getMetaInfo().getInstanceId();
            for (String grayInstanceId : grayInstanceIdList)
            {
                if (instanceId.equalsIgnoreCase(grayInstanceId))
                {
                    tempServer = s;
                }
            }
            if (tempServer != null)
            {
                grayInstanceList.add(s);
                tempServer = null;
            } else
            {
                normalInstanceList.add(s);
            }
        }
    }
}
