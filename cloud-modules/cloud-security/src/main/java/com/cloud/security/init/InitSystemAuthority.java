package com.cloud.security.init;

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

import com.cloud.security.bean.PermissionRoleInfo;
import com.cloud.security.filter.SecurityMetadataSourceFilter;
import com.cloud.security.service.UserService;

/**
 * 在服务启动完成后初始化服务中所有的资源与角色的关系，将对应关系存储到
 * SecurityMetadataSourceFilter中的requestMap成员变量中
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月27日
 */
@Component
public class InitSystemAuthority implements ApplicationRunner {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityMetadataSourceFilter metadataSource;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Map<String, Collection<ConfigAttribute>> map = new HashMap<>();
		
		List<PermissionRoleInfo> list = userService.queryAllPermissionRoleInfo();
		if(list != null)
		{
		    list.stream().forEach(permissionRoleInfo ->{
		        String resource = permissionRoleInfo.getResource();
		        List<String> listRoles = permissionRoleInfo.getRoleName();
		        String[] arrays = new String[listRoles.size()];
                arrays = listRoles.toArray(arrays);
		        List<ConfigAttribute> listAttrs = SecurityConfig.createList(arrays);
		        map.put(resource, listAttrs);
		    });
		}
		metadataSource.setRequestMap(map);
	}

}
