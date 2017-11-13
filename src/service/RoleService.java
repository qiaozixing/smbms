package service;

import java.util.List;


import pojo.Role;

public interface RoleService {
	/**
	 * 查询所有角色信息
	 * @return
	 */
	List<Role> findByRoleAll();
}
