package com.cloud.tx.control;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.cloud.tx.aop.bean.TxTransactionLocal;
import com.cloud.tx.framework.utils.SocketManager;
import com.cloud.tx.model.Request;

/**
 * create by lorne on 2017/12/5
 */
public class LCNTransactionAspectSupport {



    private static LCNTransactionAspectSupport instance = null;

    private LCNTransactionAspectSupport(){}

    public static LCNTransactionAspectSupport currentTransactionStatus() {
        if (instance == null) {
            synchronized (LCNTransactionAspectSupport.class) {
                if(instance==null){
                    instance = new LCNTransactionAspectSupport();
                }
            }
        }
        return instance;
    }


    public boolean setRollbackOnly(){
        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
        if(txTransactionLocal==null){
            return false;
        }

        if(StringUtils.isEmpty(txTransactionLocal.getGroupId())){
            return false;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("g", txTransactionLocal.getGroupId());
        Request request = new Request("rg", jsonObject.toString());
        String json =  SocketManager.getInstance().sendMsg(request);
        try {
            return Integer.parseInt(json)==1;
        }catch (Exception e){
            return false;
        }
    }

}
