package com.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月28日
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user_1").password("123456").authorities("USER")
                .and()
                .withUser("user_2").password("123456").authorities("USER");
   }

   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
       AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }*/
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
         return manager;
    }
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                .withUser("user_1").password(passwordEncoder().encode("123456")).authorities("USER")
                .and()
                .passwordEncoder(passwordEncoder())
                .withUser("user_2").password(passwordEncoder().encode("123456")).authorities("USER");
   }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
   /* @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
        return manager;
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatchers().anyRequest()
            .and()
                .authorizeRequests()
                .antMatchers("/oauth/*").permitAll();
    }
}
