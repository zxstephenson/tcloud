package com.cloud.config.client.locator;

import java.util.Map;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月20日
 */

public class NativePropertySource extends EnumerablePropertySource<Map<String,String>> {

    public NativePropertySource(String name, Map<String, String> source) {
        super(name, source);
    }

    //获取所有的配置名字
    @Override
    public String[] getPropertyNames() {
        return source.keySet().toArray(new String[source.size()]);
    }

    //根据配置返回对应的属性
    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }
}
