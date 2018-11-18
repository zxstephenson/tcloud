package com.software.service;

import java.util.List;

import com.software.bean.Resource;
import com.software.bean.Role;
import com.software.bean.User;
import com.software.domain.UserInfo;

public interface UserService {

	/**
	 * 根据用户名查询用户信息，包括权限角色信息
	 * @param username
	 * @return
	 */
	public UserInfo findUserByUsername(String username);
	
	public List<User> findAllUser();
	
	public void addUserInfo(User user);
	
	/**
	 * 查询所有的角色信息
	 * @return
	 */
	public List<Role> queryAllRoles();
	
	/**
	 * 查询所有的Resource信息
	 * @return
	 */
	public List<Resource> queryAllResource();
	
	/**
	 * 根据资源id查询对应的角色名
	 * @param resourceId
	 * @return
	 */
	public List<String> queryRoleNameByResourceId(String resourceId);
	
	public List<String> queryRolename();
}
