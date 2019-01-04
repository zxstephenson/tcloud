/**
 * <p>文件名称: Msg.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.model;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class Msg extends JsonModel
{

    private int state;
    private String msg;
    private Object res;

    public Msg()
    {
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Msg(int state, Object res)
    {
        super();
        this.state = state;
        this.res = res;
    }

    public Msg(int state)
    {
        super();
        this.state = state;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public Object getRes()
    {
        return res;
    }

    public void setRes(Object res)
    {
        this.res = res;
    }

}
