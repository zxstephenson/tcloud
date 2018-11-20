package com.cloud.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloud.security.service.MyUserDetailsSerivce;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月12日
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //启用方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private MyUserDetailsSerivce myUserDetailsSerivce;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/", "/index.html").permitAll()
//                .antMatchers("/").hasAnyRole("")
                .anyRequest().authenticated() //其他地址的访问均需要验证权限
                .and()
            .formLogin()
                .loginPage("/login.html")
                .failureUrl("/login-error.html").permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/index.html")
                .permitAll();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
//        auth.userDetailsService(myUserDetailsSerivce).passwordEncoder(passwordEncoder());
        auth.userDetailsService(myUserDetailsSerivce);
    }
    
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
