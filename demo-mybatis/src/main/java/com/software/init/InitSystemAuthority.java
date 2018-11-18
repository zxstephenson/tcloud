package com.software.init;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Component;

import com.software.bean.Resource;
import com.software.security.MyFilterSecurityMetadataSource;
import com.software.service.UserService;

//@Component
public class InitSystemAuthority implements ApplicationRunner {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MyFilterSecurityMetadataSource metadataSource;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Map<String, Collection<ConfigAttribute>> map = new HashMap<>();
		
		List<Resource> listResources = userService.queryAllResource();
		if(null != listResources){
			listResources.parallelStream().forEach(resource -> {
				String resourceId = resource.getId();
				String resourceUrl = resource.getResourceUrl();
				List<String> listRoleName = userService.queryRoleNameByResourceId(resourceId);
				String[] arrays = new String[listRoleName.size()];
				arrays = listRoleName.toArray(arrays);
				List<ConfigAttribute> listAttrs = SecurityConfig.createList(arrays);
				map.put(resourceUrl, listAttrs);
			});
		}
		
		metadataSource.setRequestMap(map);
        
//        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/test01");
//        SecurityConfig config = new SecurityConfig("ROLE_USER");
//        ArrayList<ConfigAttribute> configs = new ArrayList<>();
//        configs.add(config);
        //roleName
//        List<ConfigAttribute> listAttr = SecurityConfig.createList("");
//        map.put(matcher,listAttr);
	}

}
