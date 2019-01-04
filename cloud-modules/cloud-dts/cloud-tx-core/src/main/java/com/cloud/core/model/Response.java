/**
 * <p>文件名称: Response.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.model;

import com.cloud.core.Code;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class Response extends JsonModel
{

    private int code;
    private String msg;
    private Object data;

    public Response(int code, String msg, Object data)
    {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response()
    {
    }

    public Response(Object data)
    {
        super();
        this.code = Code.code_success;
        this.data = data;
    }

    public Response(int code, Object data)
    {
        super();
        this.code = code;
        this.data = data;
    }

    public Response(int code, String msg)
    {
        super();
        this.code = code;
        this.msg = msg;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public void setSuccessMsg(String msg)
    {
        this.code = Code.code_success;
        this.msg = msg;
    }

    public void setSuccessData(Object data)
    {
        this.code = Code.code_success;
        this.data = data;
    }

    public void setErrorMsg(String msg)
    {
        this.code = Code.code_error;
        this.msg = msg;
    }

    public void setCodeMsg(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public void setCodeMsgData(int code, String msg, Object data)
    {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setCodeData(int code, Object data)
    {
        this.code = code;
        this.data = data;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

}
