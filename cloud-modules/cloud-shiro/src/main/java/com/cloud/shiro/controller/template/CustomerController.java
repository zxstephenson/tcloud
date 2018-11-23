/**
 * <p>文件名称: CustomerController.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.controller.template;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.software.bean.User;
import com.software.bean.UserRole;
import com.software.service.UserService;
import com.szkingdom.jros.shiro.session.SessionHandler;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年8月30日
 */

@Controller
public class CustomerController
{
    @Autowired
    private UserService userService;
    
    @Autowired
    private SessionHandler sessionHandler;
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(User user){
        
        Subject subject = SecurityUtils.getSubject();
        
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        token.setRememberMe(user.isRememberMe());
        System.out.println("******************isRememberMe = " + token.isRememberMe());
        try{
            subject.login(token);  
            if(subject.isAuthenticated()){
                System.out.println("hasRole = " + subject.hasRole("admin"));
                return "index";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "login";
        }
        
        return "login";
    }
    
    @RequestMapping("/toAddUserRolePage")
    public String toAddUserRolePage(){
        
        return "addUserRole";
    }
    
    @RequestMapping("/testSetAttribute/{key}/{value}")
    @ResponseBody
    public void testSetAttribute(@PathVariable String key , @PathVariable String value){
        sessionHandler.setAttribute(key, value);
    }
    
    @RequestMapping("/testGetAttribute/{name}")
    @ResponseBody
    public String testGetAttribute(@PathVariable String name){
        return (String)sessionHandler.getAttribute(name);
    }
    
    
    @RequestMapping("/checkRole/{roleName}")
    @ResponseBody
    public String checkRole(@PathVariable String roleName,
    		HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        
        Session session = subject.getSession();
        
        System.out.println("Shirosession = " + session);
        System.out.println("httpSession = " + request.getSession());
        System.out.println("sessionId = " + session.getId().toString());
        System.out.println("httpSessionId = " + request.getSession().getId());
        System.out.println("==============================================");
        session.stop();
//        sessionHolder.clear
        session = subject.getSession();
        System.out.println("Shirosession = " + session);
        System.out.println("httpSession = " + request.getSession());
        System.out.println("sessionId = " + session.getId().toString());
        System.out.println("httpSessionId = " + request.getSession().getId());
        
        
        return "===========checkRole=========="+roleName+" = " + subject.hasRole(roleName);
    }
    
    @RequiresRoles("admin")
    @RequestMapping("testAdminRole")
    @ResponseBody
    public String testAdminRole(){
        return "testAdminRole success";
    }
    
    @RequiresRoles("admin1")
    @RequestMapping("/testAdmin1Role")
    @ResponseBody
    public String testAdmin1Role(){
        Subject subject = SecurityUtils.getSubject();
        return "testAdmin1Role success = " + subject.hasRole("admin1");
    }
    
    @RequestMapping(value="/addUserRole", method=RequestMethod.POST)
    @ResponseBody
    public String addUserRole(UserRole userRole){
        userService.addUserRole(userRole);
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipals().toString();
//        sessionHolder.clearCachedAuthorizationInfo(subject.getPrincipals());
        return "addUserRole success " + username;
    }
    
    
    @RequestMapping("/checkPermission/${permission}")
    @ResponseBody
    public String testRoleManager(@PathVariable String permission){
        
        return "=============has testRoleManager=========="+permission+" = " + SecurityUtils.getSubject().isPermitted(permission);
    }
    
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "logout";
    }
}
