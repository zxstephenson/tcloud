package com.cloud.security.filter;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

public class SecurityMetadataSourceFilter implements FilterInvocationSecurityMetadataSource {

	
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();
	
    private Map<String, Collection<ConfigAttribute>> requestMap;

    /*
     * 这个例子放在构造方法里初始化url权限数据，我们只要保证在 getAttributes()之前初始好数据就可以了
     */
    public SecurityMetadataSourceFilter() {
        
    }

    public Map<String, Collection<ConfigAttribute>> getRequestMap() {
		return requestMap;
	}
    
	public void setRequestMap(Map<String, Collection<ConfigAttribute>> requestMap) {
		this.requestMap = requestMap;
	}

	/**
     * 该方法用于获取当前请求资源所需要的权限信息，这里主要还是角色信息。 
     * 也即是表示当前请求的资源哪些角色有权限访问。初始的资源-角色对应的信息
     * 存储在成员变量requestMap中，requestMap的初始数据在InitSystemAuthority中完成。
     * InitSystemAuthority会在系统启动后进行初始化。requestMap为一个Map类型的key即为
     * 资源信息，value则为能够访问该资源的所有角色的列表信息；
     * 为了实现资源的动态管理，在修改了资源的访问权限后需要及时的修改当前对象中requestMap的值。
     *  
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();

        for(Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap.entrySet()){
            if(antPathMatcher.match(entry.getKey(),url)){
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}