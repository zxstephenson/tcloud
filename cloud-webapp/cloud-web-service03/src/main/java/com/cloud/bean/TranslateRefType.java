package com.cloud.bean;
/**
 * <p>文件名称: TranslateRefType.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author yyh
 * @version 3.1.0 2018年4月18日
 */
public enum TranslateRefType implements BaseEnum<TranslateRefType, String>
{
    /** 翻译字典的读取方式 */
    // DICTIONARY, CACHE, INTERFACE;
    DICTIONARY("dictionary", "dictionary"), 
    CACHE("cache","cache"), 
    INTERFACE("interface", "interface");

    private String value;
    private String displayName;

    static Map<String, TranslateRefType> enumMap = new HashMap<String, TranslateRefType>();
    static
    {
        for (TranslateRefType type : TranslateRefType.values())
        {
            enumMap.put(type.getValue(), type);
        }
    }

    private TranslateRefType(String value, String displayName)
    {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue()
    {
        return value;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public static TranslateRefType fromValue(String value)
    {
        if ("dictionary".equalsIgnoreCase(value))
        {
            return DICTIONARY;
        } else if ("cache".equalsIgnoreCase(value))
        {
            return CACHE;
        } else if ("interface".equalsIgnoreCase(value))
        {
            return INTERFACE;
        }
        return null;
    }
}
