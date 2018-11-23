/**
 * <p>文件名称: CacheHandler.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.controller.template;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月6日
 */

public interface CacheHandler
{

    public void set(byte[] key, byte[] value);
    
    public void setex(byte[] key, byte[] value, int seconds);
    
    public byte[] get(byte[] key);
    
    public void del(byte[] key);
    
    public void hset(byte[] key, byte[] field, byte[] value);
    
    public void hsetex(byte[] key, byte[] field, byte[] value, int seconds);
    
    public byte[] hget(byte[] key, byte[] field);

    public byte[] hgetex(byte[] key, byte[] field, int seconds);
    
    
}
