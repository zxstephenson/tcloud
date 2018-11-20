package com.cloud.metrics;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月19日
 */

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MetricsStreamClient {
    String OUTPUT = "metricsStreamOutput";

    @Output(OUTPUT)
    MessageChannel metricsStreamOutput();
}
