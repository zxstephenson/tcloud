package com.software.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.software.domain.UserInfo;
import com.software.service.UserService;

public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo = null;
		try{
			userInfo = userService.findUserByUsername(username);
			
			System.err.println("==================");
			System.err.println("==================>" + userInfo);
			System.err.println("==================");
			if(userInfo == null)
			{
				throw new UsernameNotFoundException("user not found");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new MyUserPrincipal(userInfo);
	}

}
