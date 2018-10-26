package com.cloud.common.bean;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */

public class RequestHeader
{
    @NotEmpty
    private String code;
    
    private Map<String, Object> extend = new HashMap<String, Object>();

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
