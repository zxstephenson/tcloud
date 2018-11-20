package com.cloud.metrics;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月19日
 */

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.AvailableTag;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.MetricResponse;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;


@EnableScheduling
public class MetricsStreamTask {
    private final static Logger log = LoggerFactory.getLogger(MetricsStreamTask.class);

    private MessageChannel outboundChannel;

    private ServiceInstance registration;

    private MetricsStreamProperties properties;

    private MetricsEndpoint metricsEndpoint;

    // Visible for testing
    final LinkedBlockingQueue<String> jsonMetrics;

    private final JsonFactory jsonFactory = new JsonFactory();

    public MetricsStreamTask(MessageChannel outboundChannel,
                             ServiceInstance registration, MetricsStreamProperties properties, MetricsEndpoint metricsEndpoint) {
        Assert.notNull(outboundChannel, "outboundChannel may not be null");
        Assert.notNull(registration, "registration may not be null");
        Assert.notNull(properties, "properties may not be null");
        Assert.notNull(metricsEndpoint, "properties may not be null");
        this.outboundChannel = outboundChannel;
        this.registration = registration;
        this.properties = properties;
        this.jsonMetrics = new LinkedBlockingQueue<>(properties.getSize());
        this.metricsEndpoint=metricsEndpoint;
    }
    // TODO: use integration to split this up?
    @Scheduled(fixedRateString = "${metrics.stream.queue.sendRate:1000}")
    public void sendMetrics() {

        log.info("推送metrics信息");
        
        ArrayList<String> metrics = new ArrayList<>();
        this.jsonMetrics.drainTo(metrics);

        if (!metrics.isEmpty()) {
            if (log.isTraceEnabled()) {
                log.trace("sending stream Metrics metrics size: " + metrics.size());
            }
            for (String json : metrics) {
                // TODO: batch all metrics to one message
                try {
                    // TODO: remove the explicit content type when s-c-stream can handle
                    // that for us
                    this.outboundChannel.send(MessageBuilder.withPayload(json)
                            .setHeader(MessageHeaders.CONTENT_TYPE,
                                    this.properties.getContentType())
                            .build());
                }
                catch (Exception ex) {
                    if (log.isTraceEnabled()) {
                        log.trace("failed sending stream Metrics metrics: " + ex.getMessage());
                    }
                }
            }
        }
    }

    
    
    @Scheduled(fixedRateString = "${metrics.stream.queue.gatherRate:1000}")
    public void gatherMetrics() {
        log.info("开始获取metrics信息");
        try {
            
            StringWriter jsonString = new StringWriter();
            JsonGenerator json = this.jsonFactory.createGenerator(jsonString);
            json.writeStartObject();
            json.writeObjectField("instanceId",registration.getServiceId() + ":" + registration.getHost() + ":"
                    + registration.getPort());
            json.writeObjectField("type", "metrics");
            json.writeObjectField("currentTime",System.currentTimeMillis());
//            Map<String, Object> map = (Map<String, Object>) metricsEndpoint.metric(this.properties.getPathTail(), null);
            /*MetricResponse metricResponse = metricsEndpoint.metric(this.properties.getPathTail(), null);
            List<AvailableTag> listTag = metricResponse.getAvailableTags();
            listTag.stream().forEach(item -> {
                item.getTag()
            });
            for (String str : map.keySet()) {
                json.writeObjectField(str, map.get(str));
            }*/
            
            json.writeEndObject();
            json.close();
            
            
            // output to stream
            this.jsonMetrics.add(jsonString.getBuffer().toString());
            
        }
        catch (Exception ex) {
            log.error("Error adding metrics metrics to queue", ex);
        }
    }

}