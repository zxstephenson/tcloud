package com.cloud.common.bean;

import java.util.HashMap;
import java.util.Map;

import com.cloud.common.constant.Constants;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月26日
 */

public class ResponseHeader
{
    private String code;
    
    private String msg;
    
    private Map<String, Object> extend = new HashMap<String, Object>();

    public ResponseHeader(){
        this.code = Constants.SUCCESS;
        this.msg = Constants.SUCCESS_MSG;
    }
    
    public ResponseHeader(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    
    /**
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @return the msg
     */
    public String getMsg()
    {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    /**
     * @return the extend
     */
    public Map<String, Object> getExtend()
    {
        return extend;
    }

    /**
     * @param extend the extend to set
     */
    public void setExtend(Map<String, Object> extend)
    {
        this.extend = extend;
    }
    
}
