package service.impl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.ProviderMapper;
import pojo.Provider;
import service.ProviderService;
@Service("providerService")
public class ProviderServiceImpl implements ProviderService {
	@Autowired
	private ProviderMapper providerMapper;
	
	public void setProviderMapper(ProviderMapper providerMapper) {
		this.providerMapper = providerMapper;
	}
	/**
	 * 通过条件查询信息
	 */
	public List<Provider> findByAll(Map<String,Object> map) {
		return providerMapper.findByAll(map);
	}
	/**
	 * 添加供应商
	 */
	public Integer addProvider(Provider provider) {
		return providerMapper.addProvider(provider);
	}
	/**
	 * 查询单个供应商
	 */
	public Provider findProvOne(Integer id) {
		return providerMapper.findProvOne(id);
	}
	/**
	 * 查询全部供应商信息
	 */
	public List<Provider> findAll() {
		return providerMapper.findAll();
	}
	/**
	 * 删除供应商
	 */
	public Integer deleteProvider(Integer id) {
		return providerMapper.deleteProvider(id);
	}
	/**
	 * 更新供应商
	 */
	public Integer updateProvider(Provider provider) {
		return providerMapper.updateProvider(provider);
	}
	/**
	 * 总记录数+分页
	 */
	public Integer findByCount(Map<String, Object> map) {
		return providerMapper.findByCount(map);
	}

}
