package com.cloud.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cloud.context.ContextHolder;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月27日
 */

public class PasswordUtil
{
    private static BCryptPasswordEncoder passwordEncoder;
    
    private static BCryptPasswordEncoder getPasswordEncoder(){
        if(passwordEncoder == null) {
            
            synchronized(PasswordUtil.class){
                if(passwordEncoder == null) {
                    passwordEncoder = ContextHolder.getBean(BCryptPasswordEncoder.class);
                }
            }
        }
        
        return passwordEncoder;
    }
    
    /**
     * 对输入的源密码进行加密
     * @param srcPassword
     * @return
     */
    public static String encode(String srcPassword){
        
        return getPasswordEncoder().encode(srcPassword);
    }
}
