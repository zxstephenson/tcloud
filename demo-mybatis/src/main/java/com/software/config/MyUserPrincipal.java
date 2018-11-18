package com.software.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.software.bean.Role;
import com.software.domain.UserInfo;

public class MyUserPrincipal implements UserDetails {

	private static final long serialVersionUID = 6046486412564944382L;

	private UserInfo userInfo;
	
	public MyUserPrincipal(UserInfo userInfo){
		this.userInfo = userInfo;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		List<Role> roles = this.userInfo.getRoles();
		if(null != roles)
		{
			roles.parallelStream().forEach(role -> {
				SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRolename());
				list.add(simpleGrantedAuthority);
			});
		}
		return list;
	}

	@Override
	public String getPassword() {
		return this.userInfo.getPassword();
	}

	@Override
	public String getUsername() {
		return this.userInfo.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
