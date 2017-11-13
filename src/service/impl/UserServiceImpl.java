/**
 *  UserServiceImpl TODO()
 */
package service.impl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.UserMapper;
import pojo.Role;
import pojo.User;
import service.UserService;
import tools.Page;

/**
 * @author ggy
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	public User findByName(String name) {
		return userMapper.findByName(name);
	}
	
	public List<Role> findByRoleAll() {
		return userMapper.findByRoleAll();
	}
	
	public Integer saveUser(User user) {
		return userMapper.saveUser(user);
	}
	public List<User> findBy(String userName, Integer roleId) {
		return userMapper.findBy(userName, roleId);
	}
	public Integer deleteUser(Integer id) {
		return userMapper.deleteUser(id);
	}
	public Integer updateUser(User user) {
		return userMapper.updateUser(user);
	}
	public User findById(Integer id) {
		return userMapper.findById(id);
	}
	public Page<User> findByPage(Map<String, Object> map) {
		List<User> list = userMapper.findByQuery(map);
		Integer totalCount = userMapper.findByCount(map);
		Page<User> page = new Page<User>();
		page.setList(list);
		page.setTotalCount(totalCount);
		return page;
	}
	public Integer updatePwd(String pwd,Integer id) {
		return userMapper.updatePwd(pwd,id);
	}

}
