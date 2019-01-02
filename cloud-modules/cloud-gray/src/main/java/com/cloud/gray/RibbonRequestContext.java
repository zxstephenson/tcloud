package com.cloud.gray;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年12月27日
 */
@Component
public class RibbonRequestContext
{
    private ThreadLocal<Map<String, Object>> context = new ThreadLocal<Map<String, Object>>(){
        protected java.util.Map<String,Object> initialValue() {
            return new HashMap<String, Object>();
        };
    };
    
    public Map<String, Object> getAttributes()
    {
        return context.get();
    }
    
    public void add(String key, Object value)
    {
        context.get().put(key, value);
    }
    
    public Object get(String key)
    {
        return context.get().get(key);
    }
    public void remove()
    {
        context.remove();
    }
}
