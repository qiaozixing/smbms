/**
 *  UserService TODO()
 */
package service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pojo.User;
import tools.Page;

/**
 * @author ggy
 *
 */
public interface UserService {
	
	User findByName(String name);
	/**
	 * 查询单个对象
	 * @param id
	 * @return
	 */
	User findById(@Param("id") Integer id);
	/**
	 * 分页+查询
	 * @param map
	 * @return
	 */
	Page<User> findByPage(Map<String,Object> map);
	/**
	 * 通过条件查询信息
	 * @param userName
	 * @param roleId
	 * @return
	 */
	List<User> findBy(String  userName,Integer roleId);
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	Integer saveUser(User user);	
	/**
	 * deleteUser TODO(删除用户)
	 * @param id
	 * @return
	 */
	Integer deleteUser(Integer id);	
	/**
	 * updateUser TODO(用户修改)
	 * @param user
	 * @return
	 */
	Integer updateUser(User user);
	/**
	 * 修改
	 * @param id
	 * @return
	 */
	Integer updatePwd(String pwd,Integer id);
}	
