package mapper;

import java.util.List;


import pojo.Role;

public interface RoleMapper {
	/**
	 * 查询所有角色信息
	 * @return
	 */
	List<Role> findByRoleAll();
}
