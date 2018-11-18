package com.software.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class InitSystemMappings implements ApplicationRunner{

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;
	
	/**
	 * 存储所有自定义的url地址
	 */
	private static List<String> listMappings = new ArrayList<String>(); 
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();  
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {  
            RequestMappingInfo info = m.getKey();  
            PatternsRequestCondition p = info.getPatternsCondition();  
            for(String url : p.getPatterns()) {  
            	listMappings.add(url);
            }
        }
	}

	public static List<String> getListMappings() {
		return listMappings;
	}
	
	
}
