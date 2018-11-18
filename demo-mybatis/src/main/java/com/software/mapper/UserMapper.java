package com.software.mapper;

import java.util.List;

import com.software.bean.User;
import com.software.domain.UserInfo;

public interface UserMapper {

	public UserInfo findUserByUsername(String username);
	
	public List<User> findAllUser();
	
	public void addUserInfo(User user);
}
