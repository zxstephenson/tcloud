package com.software.mapper;

import java.util.List;

public interface RoleResourceMapper {

	public List<String> queryRoleNameByResourceId(String resourceId);
}
