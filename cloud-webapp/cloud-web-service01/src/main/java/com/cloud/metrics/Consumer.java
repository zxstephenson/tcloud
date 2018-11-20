package com.cloud.metrics;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author zyg
 *
 */
@Component
public class Consumer {
    protected final static Logger logger = LoggerFactory.getLogger(Consumer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private InfluxDBConnect influxDB;

//    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void sendToSubject(org.springframework.amqp.core.Message message) {

        String payload = new String(message.getBody());
        logger.info(payload);

        if (payload.startsWith("\"")) {
            // Legacy payload from an Angel client
            payload = payload.substring(1, payload.length() - 1);
            payload = payload.replace("\\\"", "\"");
        }
        try {
            if (payload.startsWith("[")) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> list = this.objectMapper.readValue(payload, List.class);
                for (Map<String, Object> map : list) {
                    sendMap(map);
                }
            } else {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = this.objectMapper.readValue(payload, Map.class);
                sendMap(map);
            }
        } catch (IOException ex) {
            logger.error("Error receiving hystrix stream payload: " + payload, ex);
        }
    }

    private void sendMap(Map<String, Object> map) {
        Map<String, Object> data = getPayloadData(map);
        data.remove("latencyExecute");
        data.remove("latencyTotal");
        Map<String, String> tags = new HashMap<String, String>();
        
        tags.put("type", data.get("type").toString());
        tags.put("name", data.get("name").toString());
        tags.put("instanceId", data.get("instanceId").toString());
        //tags.put("group", data.get("group").toString());
        
        
        influxDB.insert("testaaa", tags, data);

        // for (String key : data.keySet()) {
        // logger.info("{}:{}",key,data.get(key));
        // }


    }

    public static Map<String, Object> getPayloadData(Map<String, Object> jsonMap) {
        @SuppressWarnings("unchecked")
        Map<String, Object> origin = (Map<String, Object>) jsonMap.get("origin");
        String instanceId = null;
        if (origin.containsKey("id")) {
            instanceId = origin.get("host") + ":" + origin.get("id").toString();
        }
        if (!StringUtils.hasText(instanceId)) {
            // TODO: instanceid template
            instanceId = origin.get("serviceId") + ":" + origin.get("host") + ":" + origin.get("port");
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) jsonMap.get("data");
        data.put("instanceId", instanceId);
        return data;
    }

}