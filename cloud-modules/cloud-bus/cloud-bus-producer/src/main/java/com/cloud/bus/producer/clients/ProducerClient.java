package com.cloud.bus.producer.clients;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import com.cloud.bus.producer.channels.OutputChannel;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */
@EnableBinding(value={OutputChannel.class})
public class ProducerClient
{
    @Autowired
    private OutputChannel outputChannel;
    
    /**
     * 广播消息，使用默认headers
     * @param msgBody
     * @return
     */
    public boolean sendBroadcastMessage(Object msgBody){
        return sendBroadcastMessage(null, msgBody);
    }
    
    /**
     * 广播消息，传入指定的headers
     * @param headers
     * @param msgBody
     * @return
     */
    public boolean sendBroadcastMessage(Map<String, Object> headers, Object msgBody){
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<Object> message = MessageBuilder.createMessage(msgBody, messageHeaders);
        return outputChannel.broadcastOutput().send(message);
    }
    
    /**
     * 按分组发送消息，不传递headers信息，使用默认headers
     * @param msgBody
     * @return
     */
    public boolean sendGroupMessage(Object msgBody){
        return sendGroupMessage(null, msgBody);
    }
    
    /**
     * 按分组发送消息，传入指定headers信息
     * @param headers
     * @param msgBody
     * @return
     */
    public boolean sendGroupMessage(Map<String, Object> headers, Object msgBody){
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        Message<Object> message = MessageBuilder.createMessage(msgBody, messageHeaders);
        return outputChannel.groupMessageOutput().send(message);
    }
}
