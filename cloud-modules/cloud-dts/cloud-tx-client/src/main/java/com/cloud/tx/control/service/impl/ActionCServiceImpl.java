package com.cloud.tx.control.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cloud.core.utils.encode.Base64Utils;
import com.cloud.tx.compensate.service.CompensateService;
import com.cloud.tx.control.service.IActionService;
import com.cloud.tx.framework.utils.SerializerUtils;
import com.cloud.tx.model.TransactionInvocation;

/**
 * 通知补偿
 * create by lorne on 2017/11/13
 */
@Service(value = "c")
public class ActionCServiceImpl implements IActionService {


    private Logger logger = LoggerFactory.getLogger(ActionCServiceImpl.class);


    @Autowired
    private CompensateService compensateService;

    @Override
    public String execute(JSONObject resObj, String json) {

        String cmd = resObj.toJSONString();

        logger.debug("accept compensate data ->" + cmd);


        String data = resObj.getString("d");

        String groupId = resObj.getString("g");

        int startState = resObj.getInteger("ss");

        byte[] bytes = Base64Utils.decode(data);

        TransactionInvocation invocation = SerializerUtils.parserTransactionInvocation(bytes);

        if (invocation != null) {
            logger.info("compensate method ->" + invocation.getMethodStr());

            boolean res = compensateService.invoke(invocation, groupId,startState);

            logger.info("compensate res ->" + res);

            if (res) {
                return "1";
            } else {
                return "0";
            }
        }

        return "-1";
    }


}
