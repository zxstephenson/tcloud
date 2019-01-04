package com.cloud.tx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cloud.core.utils.config.ConfigUtils;
import com.cloud.tx.config.service.TxManagerTxUrlService;

/**
 * create by lorne on 2017/11/13
 */
@Component
public class ConfigReader {


    private Logger logger = LoggerFactory.getLogger(ConfigReader.class);


    private TxManagerTxUrlService txManagerTxUrlService;

    @Autowired
    private ApplicationContext spring;


    public String getTxUrl() {

        try {
            txManagerTxUrlService =  spring.getBean(TxManagerTxUrlService.class);
        }catch (Exception e){
            logger.debug("load default txManagerTxUrlService ");
        }

        if(txManagerTxUrlService == null){
            txManagerTxUrlService = new TxManagerTxUrlService() {

                private final String configName = "tx.properties";

                private final String configKey = "url";

                @Override
                public String getTxUrl() {
                    return ConfigUtils.getString(configName,configKey);
                }
            };

            logger.debug("load default txManagerTxUrlService");
        }else{
            logger.debug("load txManagerTxUrlService");
        }

        return txManagerTxUrlService.getTxUrl();
    }


}
