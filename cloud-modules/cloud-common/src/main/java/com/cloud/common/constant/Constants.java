package com.cloud.common.constant;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */

public interface Constants
{
    public static final String SUCCESS = "0000";
    
    public static final String SUCCESS_MSG = "成功";
    
    public static final String ERROR = "-9999";
    
    public static final String PREFIX = "jmsa:meta:";
    
    public static final String SERVICE_API = "api:";
    
    //指定散列算法为md5
    public static final String ALGORITHM_NAME = "MD5";
    
    //散列迭代次数
    public static final int HASH_ITERATIONS = 2; 

    public final static String REDIS_CONNECTION_ERROR_CODE = "-2252";
    
    public final static String REDIS_CONNECTION_ERROR_MSG = "redis连接创建失败";
    
    public final static String REDIS_EXCUTE_ERROR_CODE = "-2253";
    
    public final static String REDIS_EXCUTE_ERROR_MSG = "redis操作执行失败";
    
    public final static String REDIS_CONFIG_ERROR_CODE = "-2257";
    
    public final static String REDIS_CONFIG_ERROR_MSG = "未配置redis参数[{0}]值";
    
    /**
     * 服务灰度规则Redis中存储的Key值
     */
    public static final String JMSA_GRAY_SERVICES_RULES = "JMSA:GRAY:SERVICES:RULES";

}

