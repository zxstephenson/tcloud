package com.cloud.tx.springcloud.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.cloud.tx.listener.service.InitService;


@Component
public class ServerListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private Logger logger = LoggerFactory.getLogger(ServerListener.class);

    private int serverPort;

    @Autowired
    private InitService initService;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        logger.info("onApplicationEvent -> onApplicationEvent. "+event.getEmbeddedServletContainer());
        this.serverPort = event.getEmbeddedServletContainer().getPort();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 若连接不上txmanager start()方法将阻塞
                initService.start();
            }
        });
        thread.setName("TxInit-thread");
        thread.start();
    }

    public int getPort() {
        return this.serverPort;
    }
    
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
