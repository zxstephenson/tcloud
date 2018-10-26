package com.cloud.bean;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    Qinchao
 * @version   3.1.0 2018年7月20日
 * @see
 * @since 3.1.0
 */

public interface BaseEnum<E extends Enum<?>, T> {
    public T getValue();
    public String getDisplayName();
}
