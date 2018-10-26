package com.cloud.bean;
/**
 * <p>文件名称: ParamDataType.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author yyh
 * @version 3.1.0 2018年4月18日
 */
public enum ParamDataType implements BaseEnum<ParamDataType, String>
{
    /** 参数类型 */
    /* PARAM_LONG, 
    PARAM_CHAR, 
    PARAM_NUMBER, 
    PARAM_BIGDECIMAL, 
    PARAM_INT, 
    PARAM_SHORT, 
    PARAM_FLOAT, 
    PARAM_DOUBLE, 
    PARAM_STRING, 
    PARAM_CLOB, 
    PARAM_BLOB, 
    UNDEFINED;*/
    
    PARAM_LONG("long","long"), 
    PARAM_CHAR("char","char"), 
    PARAM_NUMBER("number","number"), 
    PARAM_BIGDECIMAL("bigdecimal","bigdecimal"), 
    PARAM_INT("int","int"), 
    PARAM_SHORT("short","short"), 
    PARAM_FLOAT("float","float"), 
    PARAM_DOUBLE("double","double"), 
    PARAM_STRING("string","string"), 
    PARAM_CLOB("clob","clob"), 
    PARAM_BLOB("blob","blob"), 
    PARAM_BEAN("bean", "bean"),
    UNDEFINED("undefined","undefined");
    
    private String value;
    private String displayName;
    
    static Map<String, ParamDataType> enumMap = new HashMap<String, ParamDataType>();
    static
    {
        for (ParamDataType type : ParamDataType.values())
        {
            enumMap.put(type.getValue(), type);
        }
    }

    private ParamDataType(String value,String displayName) { 
        this.value=value; 
        this.displayName=displayName; 
       } 

    public static ParamDataType fromValue(String value)
    {
        if ("long".equalsIgnoreCase(value))
        {
            return PARAM_LONG;
        } else if ("char".equalsIgnoreCase(value))
        {
            return PARAM_CHAR;
        } else if ("number".equalsIgnoreCase(value))
        {
            return PARAM_NUMBER;
        } else if ("bigdecimal".equalsIgnoreCase(value))
        {
            return PARAM_BIGDECIMAL;
        } else if ("int".equalsIgnoreCase(value))
        {
            return PARAM_INT;
        } else if ("short".equalsIgnoreCase(value))
        {
            return PARAM_SHORT;
        } else if ("float".equalsIgnoreCase(value))
        {
            return PARAM_FLOAT;
        } else if ("double".equalsIgnoreCase(value))
        {
            return PARAM_DOUBLE;
        } else if ("string".equalsIgnoreCase(value))
        {
            return PARAM_STRING;
        } else if ("clob".equalsIgnoreCase(value))
        {
            return PARAM_CLOB;
        } else if ("blob".equalsIgnoreCase(value))
        {
            return PARAM_BLOB;
        }else if ("bean".equalsIgnoreCase(value))
        {
            return PARAM_BEAN;
        }
        return UNDEFINED;
    }

    public static String convertToString(ParamDataType type)
    {
        switch (type)
        {
        case PARAM_LONG:
            return "long";
        case PARAM_CHAR:
            return "char";
        case PARAM_NUMBER:
            return "number";
        case PARAM_BIGDECIMAL:
            return "bigdecimal";
        case PARAM_INT:
            return "int";
        case PARAM_SHORT:
            return "short";
        case PARAM_FLOAT:
            return "float";
        case PARAM_DOUBLE:
            return "double";
        case PARAM_STRING:
            return "string";
        case PARAM_CLOB:
            return "clob";
        case PARAM_BLOB:
            return "blob";
        }
        // UNDEFINED
        return "undefined";
    }

    public static Class getParameterClass(ParamDataType type)
    {
        switch (type)
        {
        case PARAM_LONG:
            return Long.class;
        case PARAM_CHAR:
            return String.class;
        case PARAM_NUMBER:
            return Number.class;
        case PARAM_BIGDECIMAL:
            return BigDecimal.class;
        case PARAM_INT:
            return Integer.class;
        case PARAM_SHORT:
            return Short.class;
        case PARAM_FLOAT:
            return Float.class;
        case PARAM_DOUBLE:
            return Double.class;
        case PARAM_STRING:
            return String.class;
        case PARAM_CLOB:
            return Clob.class;
        case PARAM_BLOB:
            return Blob.class;
        }
        // UNDEFINED
        return null;
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

}
