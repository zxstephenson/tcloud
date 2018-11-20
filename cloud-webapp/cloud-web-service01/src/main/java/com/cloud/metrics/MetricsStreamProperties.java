package com.cloud.metrics;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月19日
 */

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("metrics.stream.queue")
public class MetricsStreamProperties {

    private boolean enabled = true;

    private boolean prefixMetricName = true;

    private boolean sendId = true;

    private String destination = "springCloudMetricsStream";

    private String contentType = "application/json";
    
    private String pathTail = "mem.*|heap.*|threads.*|gc.*|nonheap.*|classes.*";

    private long sendRate = 1000;

    private long gatherRate = 1000;

    private int size = 1000;


    public String getPathTail() {
        return pathTail;
    }

    public void setPathTail(String pathTail) {
        this.pathTail = pathTail;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPrefixMetricName() {
        return prefixMetricName;
    }

    public void setPrefixMetricName(boolean prefixMetricName) {
        this.prefixMetricName = prefixMetricName;
    }

    public boolean isSendId() {
        return sendId;
    }

    public void setSendId(boolean sendId) {
        this.sendId = sendId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSendRate() {
        return sendRate;
    }

    public void setSendRate(long sendRate) {
        this.sendRate = sendRate;
    }

    public long getGatherRate() {
        return gatherRate;
    }

    public void setGatherRate(long gatherRate) {
        this.gatherRate = gatherRate;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
