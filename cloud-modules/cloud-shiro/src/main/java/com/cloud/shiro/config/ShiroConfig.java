package com.cloud.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;

import com.cloud.common.constant.Constants;
import com.cloud.shiro.authority.RedisCacheManager;
import com.cloud.shiro.authority.ShiroCache;
import com.cloud.shiro.reaml.MyShiroRealm;
import com.cloud.shiro.session.RedisSessionDAO;

/**
 * 
 * @author April.Chen
 */
@Configuration
public class ShiroConfig {


    @Bean
    public AuthenticationManager authenticationManagerBean()
    {
        return new OAuth2AuthenticationManager();
    }
    
    @Bean
    public DefaultWebSecurityManager securityManager(ShiroProperties shiroProperties) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
//        securityManager.setRealms(realms);//配置多个Realm，
        securityManager.setSessionManager(sessionManager(shiroProperties));
        securityManager.setCacheManager(redisCacheManager());
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());//修改策略，所有成功Realm成功才算成功。
        securityManager.setAuthenticator(authenticator);
        return securityManager;
    }
    
    @Bean
    public MyShiroRealm myShiroRealm() {
    	MyShiroRealm realm =  new MyShiroRealm();
//    	realm.setCachingEnabled(cachingEnabled);
//    	realm.setAuthorizationCachingEnabled(authenticationCachingEnabled);
//    	realm.setAuthenticationCachingEnabled(authenticationCachingEnabled);
    	realm.setCredentialsMatcher(hashedCredentialsMatcher());
    	return realm;
    }

    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }
    
    @Bean
    public <K, V> ShiroCache<K, V> shiroCache(){
    	return new ShiroCache<K, V>();
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(){
    	return new RedisSessionDAO();
    }
    
    @Bean
    public SessionManager sessionManager(ShiroProperties shiroProperties) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        //全局session过期时间，如果不设置，默认为30分钟，单位为毫秒
        sessionManager.setGlobalSessionTimeout(shiroProperties.getGlobalSessionTimeout());
        sessionManager.setCacheManager(redisCacheManager());
//        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
//        sessionManager.setSessionValidationSchedulerEnabled(sessionValidationSchedulerEnabled);
        return sessionManager;
    }

    
    /**
     * 加入注解的使用，不加入这个注解不生效
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(ShiroProperties shiroProperties) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager(shiroProperties));
        return new AuthorizationAttributeSourceAdvisor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(ShiroProperties shiroProperties) {
        
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager(shiroProperties));
        
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        /**
         * 如果当前请求匹配多个key，shiro只会取第一个匹配的key，
         */
        Map<String,String> map = new LinkedHashMap<String, String>();
        map.put("/webjars/**", "anon");
        map.put("/logout","logout"); //退出
        //对所有用户认证
        map.put("/**","authc");
//        map.put("/**", "anon");
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    
    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(Constants.ALGORITHM_NAME);//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(Constants.HASH_ITERATIONS);//散列的次数，比如散列两次，相当于 md5(md5(""));
        //是否存储散列后的密码为16进制
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
}