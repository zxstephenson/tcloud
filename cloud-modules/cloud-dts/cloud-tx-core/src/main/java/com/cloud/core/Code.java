/**
 * <p>文件名称: Code.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public interface Code
{

    /**
     * 执行成功
     */
    int code_success = 40000;

    /**
     * 执行失败
     */
    int code_error = 40010;

    /**
     * 没有授权
     */
    int code_no_authorization = 30000;

    /**
     * 请求方式不正确
     */
    int code_method_is_not_post = 31000;

    /**
     * 传入参数不符合接口要求
     */
    int code_params_error = 40100;

    /**
     * 数据库执行异常
     */
    int code_db_error = 40200;

}
