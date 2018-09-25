package com.cloud.bus.consumer.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

import com.cloud.bus.consumer.channels.InputChannel;
import com.cloud.common.bus.listeners.MessageListener;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */
@EnableBinding(value={InputChannel.class})
public class ConsumerClient
{
    
    @Autowired
    private List<MessageListener> listeners;
    
    @StreamListener(InputChannel.BROADCAST_INPUT)
    public void inputBroadcast(Message<Object> message){
        
        for(MessageListener messageListener : listeners){
            messageListener.receiveBroadcastMessage(message);
        }
    }
    
    @StreamListener(InputChannel.GROUPMESSAGE_INPUT)
    public void inputGroupMessage(Message<Object> message){
        for(MessageListener messageListener : listeners){
            messageListener.receiveGroupMessage(message);
        }
    }
    
    
}
