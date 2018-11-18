package com.software.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.bean.Resource;
import com.software.bean.Role;
import com.software.bean.User;
import com.software.domain.UserInfo;
import com.software.mapper.ResourceMapper;
import com.software.mapper.RoleResourceMapper;
import com.software.mapper.UserMapper;
import com.software.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	@Autowired
	private RoleResourceMapper roleResourceMapper;
	
	@Override
	public UserInfo findUserByUsername(String username) {
		return userMapper.findUserByUsername(username);
	}

	@Override
	public List<User> findAllUser() {
		return null;
	}

	@Override
	public void addUserInfo(User user) {
		
	}

	@Override
	public List<Role> queryAllRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Resource> queryAllResource() {
		return resourceMapper.queryAllResource();
	}

	@Override
	public List<String> queryRoleNameByResourceId(String resourceId) {

		return roleResourceMapper.queryRoleNameByResourceId(resourceId);
	}

	@Override
	public List<String> queryRolename() {
		// TODO Auto-generated method stub
		return null;
	}

}
