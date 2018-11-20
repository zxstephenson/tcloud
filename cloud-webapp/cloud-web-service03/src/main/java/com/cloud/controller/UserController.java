package com.cloud.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.AvailableTag;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.MetricResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月20日
 */
@RestController
public class UserController
{
    @Autowired
    private MetricsEndpoint metricsEndpoint;
    
    /**
     * /actuator/metrics/jvm.memory.max?tag=area:nonheap&tag=id:Metaspace
     * @param name
     * @param tags
     * @return
     */
    @GetMapping("/test/{name}")
    public Object test(@PathVariable String name, List<String> tags){
        MetricResponse metricResponse = metricsEndpoint.metric(name, tags);
        metricResponse.getMeasurements();
        List<AvailableTag> listTag = metricResponse.getAvailableTags();
        listTag.stream().forEach(item -> {
            String tagName = item.getTag();
            Set<String> sets = item.getValues();
            sets.stream().forEach(value -> {
                System.out.println("tagName = " + tagName + " :  value = " + value);
            });
        });
        return metricsEndpoint.metric(name, null);
    }
    
}
