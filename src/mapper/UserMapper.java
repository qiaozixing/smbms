/**
 *  UserMapper TODO()
 */
package mapper;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pojo.Role;
import pojo.User;

/**
 * @author ggy
 *
 */
public interface UserMapper {
	/**
	 * findByName TODO(登录)
	 * @param name
	 * @return
	 */
	User findByName(@Param("name")String name);
	/**
	 * 查询单个对象
	 * @param id
	 * @return
	 */
	User findById(@Param("id") Integer id);
	/**
	 * findByQuery TODO(动态查询+分页)
	 * @param map
	 * @return
	 */
	List<User> findByQuery(Map<String, Object> map);
	/**
	 * 通过条件查询信息
	 * @param userName
	 * @param roleId
	 * @return
	 */
	List<User> findBy(@Param("userName") String  userName,@Param("userRole") Integer roleId);
	/**
	 * countByQuery TODO(总条数)
	 * @param map
	 * @return
	 */
	Integer findByCount(Map<String, Object> map);
	/**
	 * saveUser  TODO(用户新增)
	 * @param user
	 * @return
	 */
	Integer saveUser(User user);
	/**
	 * updateUser TODO(用户修改)
	 * @param user
	 * @return
	 */
	Integer updateUser(User user);
	/**
	 * deleteUser TODO(删除用户)
	 * @param id
	 * @return
	 */
	Integer deleteUser(@Param("id")Integer id);	
	/**
	 * 查询角色表
	 * @return
	 */
	List<Role> findByRoleAll();
	/**
	 * 修改
	 * @param id
	 * @return
	 */
	Integer updatePwd(@Param("pwd") String pwd,@Param("id") Integer id);
	
	
}
