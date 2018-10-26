package com.cloud.common.bean;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */
public class ResponseData
{
    private ResponseHeader header = new ResponseHeader();
    
    private Object body;

    public ResponseData(){
        
    }
    
    public ResponseData(String code, String msg){
        this.header = new ResponseHeader(code, msg);
    }
    
    /**
     * @return the header
     */
    public ResponseHeader getHeader()
    {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(ResponseHeader header)
    {
        this.header = header;
    }

    /**
     * @return the body
     */
    public Object getBody()
    {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(Object body)
    {
        this.body = body;
    }
    
}
