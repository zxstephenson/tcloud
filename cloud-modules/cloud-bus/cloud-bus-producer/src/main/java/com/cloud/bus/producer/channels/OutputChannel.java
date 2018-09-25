package com.cloud.bus.producer.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */

public interface OutputChannel
{

    public static final String BROADCAST_OUTPUT = "broadcastOutput";

    public static final String GROUPMESSAGE_OUTPUT = "groupMessageOutput";
    
    @Output(BROADCAST_OUTPUT)
    MessageChannel broadcastOutput();
    
    @Output(GROUPMESSAGE_OUTPUT)
    MessageChannel groupMessageOutput();
    
}
