package service;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pojo.Provider;

/**
 * 供应商业务接口
 * @author customer
 *
 */
public interface ProviderService {
	/**
	 * 查询所有信息
	 * @return
	 */
	List<Provider> findAll();
	/**
	 * 通过条件查询供应商信息
	 * @param proCode
	 * @param proName
	 * @return
	 */
	List<Provider> findByAll(Map<String,Object> map);
	/**
	 * 总记录数+分页
	 * @param map
	 * @return
	 */
	Integer findByCount(Map<String,Object> map);
	/**
	 * 查询单个对象
	 * @param id
	 * @return
	 */
	Provider findProvOne(Integer id);
	/**
	 * 添加操作
	 * @param provider
	 * @return
	 */
	Integer addProvider(Provider provider);
	/**
	 * 删除供应商
	 * @param id
	 * @return
	 */
	Integer deleteProvider(Integer id);
	/**
	 * 更新操作
	 * @param provider
	 * @return
	 */
	Integer updateProvider(Provider provider);
}
