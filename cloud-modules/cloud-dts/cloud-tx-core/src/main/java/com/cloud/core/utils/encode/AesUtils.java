/**
 * <p>文件名称: AesUtils.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.encode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具类 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class AesUtils
{

    private final static String IV_PARAMETER_STR = "0102030405060708";

    private static final Logger log = LoggerFactory.getLogger(AesUtils.class);

    public static String encode(String sSrc, String sKey) throws Exception
    {
        if (sKey == null)
        {
            log.error("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16)
        {
            log.error("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AesUtils");
        Cipher cipher = Cipher.getInstance("AesUtils/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER_STR.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
        return Base64Utils.encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    public static String decode(String sSrc, String sKey) throws Exception
    {
        // 判断Key是否正确
        if (sKey == null)
        {
            log.error("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16)
        {
            log.error("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("UTF-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AesUtils");
        Cipher cipher = Cipher.getInstance("AesUtils/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER_STR.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = Base64Utils.decode(sSrc);// 先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, "utf-8");
    }

}