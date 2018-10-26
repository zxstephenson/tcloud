package com.cloud.common.bean;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */

public class RequestData
{
    @NotNull
    @Valid
    private RequestHeader head;
    
    private Map<String, Object> body = new HashMap<String, Object>();

    /**
     * @return the head
     */
    public RequestHeader getHead()
    {
        return head;
    }

    /**
     * @param head the head to set
     */
    public void setHead(RequestHeader head)
    {
        this.head = head;
    }

    /**
     * @return the body
     */
    public Map<String, Object> getBody()
    {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(Map<String, Object> body)
    {
        this.body = body;
    }
    
}
