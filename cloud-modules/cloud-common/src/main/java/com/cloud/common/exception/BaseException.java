/**
 * <p>文件名称: BaseException.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.common.exception;

public class BaseException extends Exception
{
    private static final long serialVersionUID = 7626802571889856858L;
    /**
     * 错误代码
     */
    protected String errorCode = "";

    /**
     * 基本构造方法
     */
    public BaseException()
    {
    }

    /**
     * 根据错误码构造
     * 
     * @param errorCode
     *            错误码
     */
    public BaseException(String errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * 根据错误码与消息的构造
     * 
     * @param errorCode
     *            错误码
     * @param msg
     *            错误消息
     */
    public BaseException(String errorCode, String msg)
    {
        super(msg);
        this.errorCode = errorCode;
    }

    /**
     * 根据消息与Throwable的构造
     * 
     * @param msg
     *            错误消息
     * @param throwable
     *            异常基类
     */
    public BaseException(String msg, Throwable throwable)
    {
        super(msg, throwable);
    }

    /**
     * 根据错误码、消息与Throwable的构造
     * 
     * @param errorCode
     *            错误码
     * @param msg
     *            错误消息
     * @param throwable
     *            异常基类
     */
    public BaseException(String errorCode, String msg, Throwable throwable)
    {
        super(msg, throwable);
        this.errorCode = errorCode;
    }

    /**
     * 获取错误码
     *
     * @return 返回错误码
     */
    public String getErrorCode()
    {
        return errorCode;
    }

    /**
     * 设置错误码
     * 
     * @param errorCode
     *            错误码
     */
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
}
