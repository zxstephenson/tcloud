/**
 * <p>文件名称: CustomShiroRealm.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.controller.template;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.software.bean.User;
import com.software.service.UserService;
import com.szkingdom.jros.shiro.realm.CustomRealm;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月3日
 */

public class CustomShiroRealm extends CustomRealm
{

    @Autowired
    private UserService userService;
  
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
          PrincipalCollection principals)
    {
        System.out.println("用户授权............");
      
        String username = principals.getPrimaryPrincipal().toString();
      
        Set<String> roles = userService.findRoleNameByUsername(username);
        Set<String> resources = userService.findResourceNameByUsername(username);
      
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();            
        info.setRoles(roles);
        info.setStringPermissions(resources);
      
        System.out.println(roles);
      
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
          AuthenticationToken token) throws AuthenticationException
    {
        String username = token.getPrincipal().toString();
      
        System.out.println("用户认证：" + username);
      
        User user = userService.findUserByUsername(username);
        if(user == null){
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user.getUsername(), user.getPassword(), getName());
      
        return info;
    }
  

}
