package com.cloud.tx.springcloud.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.tx.aop.bean.TxTransactionLocal;
import com.netflix.ribbon.RequestTemplate;

/**
 * Created by lorne on 2017/6/26.
 */
public class TransactionRestTemplateInterceptor implements RequestInterceptor {


    private Logger logger = LoggerFactory.getLogger(TransactionRestTemplateInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {

        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
        String groupId = txTransactionLocal == null ? null : txTransactionLocal.getGroupId();

        logger.info("LCN-SpringCloud TxGroup info -> groupId:"+groupId);

        if (txTransactionLocal != null) {
            requestTemplate.header("tx-group", groupId);
        }
    }

}
