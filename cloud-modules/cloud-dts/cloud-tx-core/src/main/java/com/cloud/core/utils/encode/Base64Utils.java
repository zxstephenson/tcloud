/**
 * <p>文件名称: Base64Utils.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.encode;

import org.apache.axis.encoding.Base64;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class Base64Utils
{

    public static String encode(byte[] bs)
    {
        return Base64.encode(bs);
    }

    public static byte[] decode(String str)
    {
        return Base64.decode(str);
    }

}
