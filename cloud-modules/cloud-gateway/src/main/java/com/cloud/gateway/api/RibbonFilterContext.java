package com.cloud.gateway.api;

import java.util.Map;
/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月30日
 */
public interface RibbonFilterContext {


    /**
     * Adds the context attribute.
     *
     * @param key   the attribute key
     * @param value the attribute value
     * @return the context instance
     */
    RibbonFilterContext add(String key, String value);

    /**
     * Retrieves the context attribute.
     *
     * @param key the attribute key
     * @return the attribute value
     */
    String get(String key);

    /**
     * Removes the context attribute.
     *
     * @param key the context attribute
     * @return the context instance
     */
    RibbonFilterContext remove(String key);

    /**
     * Retrieves the attributes.
     *
     * @return the attributes
     */
    Map<String, String> getAttributes();

}
