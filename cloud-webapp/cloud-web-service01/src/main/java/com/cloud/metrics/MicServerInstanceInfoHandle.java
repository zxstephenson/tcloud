package com.cloud.metrics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 获取微服务实例
 * 
 * @author zyg
 *
 */
@Component
@SpringBootApplication
@EnableScheduling
public class MicServerInstanceInfoHandle {

    protected final static Logger logger = LoggerFactory.getLogger(MicServerInstanceInfoHandle.class);

    final String pathtail = "/metrics/mem.*|heap.*|threads.*|gc.*|nonheap.*|classes.*";

    Map<String, String> tags;

    ThreadPoolExecutor threadpool;

    @Autowired
    DiscoveryClient dc;

    @Autowired
    RestTemplate restTemplate;

    final static LinkedBlockingQueue<Map<String, Object>> jsonMetrics = new LinkedBlockingQueue<>(1000);

    /**
     * 初始化实例 可以吧相关参数设置到配置文件
     */
    public MicServerInstanceInfoHandle() {
        
        tags = new HashMap<String, String>();
        threadpool = new ThreadPoolExecutor(4, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

    }

    @Autowired
    private InfluxDBConnect influxDB;

    /**
     * metrics数据获取
     */
    @Scheduled(fixedDelay = 2000)
    public void metricsDataObtain() {
        logger.info("开始获取metrics数据");
        List<String> servicelist = dc.getServices();
        for (String str : servicelist) {

            List<ServiceInstance> silist = dc.getInstances(str);

            for (ServiceInstance serviceInstance : silist) {
                threadpool.execute(new MetricsHandle(serviceInstance));
            }
        }
    }

    /**
     * 将数据插入到influxdb数据库
     */
    @Scheduled(fixedDelay = 5000)
    public void metricsDataToInfluxDB() {
        logger.info("开始批量将metrics数据insert-influxdb");
        ArrayList<Map<String, Object>> metricslist = new ArrayList<>();
        MicServerInstanceInfoHandle.jsonMetrics.drainTo(metricslist);

        if (!metricslist.isEmpty()) {
            logger.info("批量插入条数:{}", metricslist.size());
            influxDB.batchinsert("metrics", tags, metricslist);
        }

        logger.info("结束批量metrics数据insert");
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory achrf = new SimpleClientHttpRequestFactory();
        achrf.setConnectTimeout(10000);
        achrf.setReadTimeout(10000);
        restTemplate.setRequestFactory(achrf);
        return restTemplate;

    }

    class MetricsHandle extends Thread {
        
        private ServiceInstance serviceInstanc;
        
        public MetricsHandle(ServiceInstance serviceInstance){
            serviceInstanc=serviceInstance;
        }
        
        @Override
        public void run() {

            try {
                
                logger.info("获取  {}:{}:{} 应用metrics数据",serviceInstanc.getServiceId(),serviceInstanc.getHost(),serviceInstanc.getPort());
                
                @SuppressWarnings("unchecked")
                Map<String, Object> mapdata = restTemplate
                        .getForObject(serviceInstanc.getUri().toString() + pathtail, Map.class);
                mapdata.put("instanceId", serviceInstanc.getServiceId() + ":" + serviceInstanc.getHost() + ":"
                        + serviceInstanc.getPort());
                mapdata.put("type", "metrics");
                mapdata.put("currentTime", System.currentTimeMillis() * 1000000);
                MicServerInstanceInfoHandle.jsonMetrics.add(mapdata);

            } catch (Exception e) {
                logger.error("instanceId:{},host:{},port:{},path:{},exception:{}", serviceInstanc.getServiceId(),
                        serviceInstanc.getHost(), serviceInstanc.getPort(), serviceInstanc.getUri(),
                        e.getMessage());
            }
        }
    }

}