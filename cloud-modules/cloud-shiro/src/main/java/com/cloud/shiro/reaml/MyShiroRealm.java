package com.cloud.shiro.reaml;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.shiro.domain.Permission;
import com.cloud.shiro.domain.Role;
import com.cloud.shiro.domain.User;
import com.cloud.shiro.service.LoginService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月23日
 */

//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm{

  //用于用户查询
  @Autowired
  private LoginService loginService;

  //角色权限和对应权限添加
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
      //获取登录用户名
      String name= (String) principalCollection.getPrimaryPrincipal();
      //查询用户名称
      User user = loginService.findByName(name);
      //添加角色和权限
      SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
      for (Role role : user.getRoles()) {
          //添加角色
          simpleAuthorizationInfo.addRole(role.getRoleName());
          System.out.println("==============>roleName = " + role.getRoleName());
          for (Permission permission : role.getPermissions()) {
              //添加权限
              simpleAuthorizationInfo.addStringPermission(permission.getPermission());
              System.out.println("==============>Permission = " + permission.getPermission());
          }
      }
      return simpleAuthorizationInfo;
  }

  //用户认证
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
      //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
      if (authenticationToken.getPrincipal() == null) {
          return null;
      }
      //获取用户信息
      String username = authenticationToken.getPrincipal().toString();
      User user = loginService.findByName(username);
//      this.setSession("currentUser",authenticationToken.getPrincipal().toString());
      if (user == null) {
          
          //这里返回后会报出对应异常
          throw new UnknownAccountException();//没有找到账号
          
      } else if(Boolean.TRUE.equals(user.getLocked())){
          
          throw new LockedAccountException(); //帐号锁定
          
      }else {
          System.err.println("********************");
          System.err.println("******************** user = " + user.toString());
          System.err.println("********************");
          //这里验证authenticationToken和simpleAuthenticationInfo的信息
          SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, 
        		  user.getPassword(), ByteSource.Util.bytes(user.getCredentialSalt()), getName());
          
          return simpleAuthenticationInfo;
      }
  }
  
  /**
   * 将一些数据放到ShiroSession中,以便于其它地方使用
   *   比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
   */
  private void setSession(Object key,Object value){
      Subject subject = SecurityUtils.getSubject();
      if (null != subject) {
          Session session = subject.getSession();
          if (null != session) {
              session.setAttribute(key,value);
          }
      }
  }
  
}
