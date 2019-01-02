package com.cloud.common.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月19日
 */
public class StringUtil
{
    public static boolean isNotEmpty(Object str){
        return !isEmpty(str);
    }
    
    public static boolean isEmpty(@Nullable Object str) 
    {
        return StringUtils.isEmpty(str);
    }
    
    public static String uncapitalize(String str)
    {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str,
            boolean capitalize)
    {
        if (!hasLength(str))
        {
            return str;
        }

        char baseChar = str.charAt(0);
        char updatedChar;
        if (capitalize)
        {
            updatedChar = Character.toUpperCase(baseChar);
        } else
        {
            updatedChar = Character.toLowerCase(baseChar);
        }
        if (baseChar == updatedChar)
        {
            return str;
        }

        char[] chars = str.toCharArray();
        chars[0] = updatedChar;
        return new String(chars, 0, chars.length);
    }
    
    public static boolean hasLength(String str)
    {
        return (str != null && !str.isEmpty());
    }
    
    /**
     * 检查输入字符串是否为一个数值类型
     * @param str
     * @return 如果输入为一个数值类型返回true，相反返回false
     */
    public static boolean isNumeric(String str)
    {
        if (str != null && !"".equals(str.trim()))
        {
            return str.matches("^[0-9]*$");
        } else
        {
            return false;
        }
    }
}
