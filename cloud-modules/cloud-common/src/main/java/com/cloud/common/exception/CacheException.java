/**
 * <p>文件名称: CacheException.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.common.exception;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月19日
 */

public class CacheException extends BaseException
{
    private static final long serialVersionUID = -5314856701445174638L;

    public CacheException(){
        super();
    }
    
    public CacheException(String code, String msg){
        super(code, msg);
    }
    
}
