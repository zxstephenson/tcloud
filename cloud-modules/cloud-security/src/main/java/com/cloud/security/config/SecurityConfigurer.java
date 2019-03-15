package com.cloud.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.cloud.security.filter.DynamicalUrlFilter;
import com.cloud.security.filter.SecurityMetadataSourceFilter;
import com.cloud.security.service.impl.DaoUserDetailsServiceImpl;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月25日
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .antMatchers("/resources/**", "/templates/views/**").permitAll()
            .antMatchers("/toRegister","/register").permitAll()
            .anyRequest().authenticated()
            .and()
                .formLogin()        //设置自定义登录页面
                .loginPage("/toLogin")
                .loginProcessingUrl("/login") //设置登录提交地址
                .defaultSuccessUrl("/index")  
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/toLogin")
                .permitAll()
            .and().csrf().disable();//csrf().disable() 系统默认csrf是开启的，因此会出现POST方式提交请求报403的问题
        http.addFilterAfter(dynamicallyUrlInterceptor(), FilterSecurityInterceptor.class);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(getUserDetailsService()).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public UserDetailsService getUserDetailsService(){
        return new DaoUserDetailsServiceImpl();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public DynamicalUrlFilter dynamicallyUrlInterceptor(){
        DynamicalUrlFilter interceptor = new DynamicalUrlFilter();
        interceptor.setSecurityMetadataSource(securityMetadataSourceFilter());

        //配置RoleVoter决策
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
        decisionVoters.add(new RoleVoter());
        //设置认证决策管理器
        interceptor.setAccessDecisionManager(new AffirmativeBased(decisionVoters));
        return interceptor;
    }
    
    @Bean
    public SecurityMetadataSourceFilter securityMetadataSourceFilter(){
        return new SecurityMetadataSourceFilter();
    }
}
