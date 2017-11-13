package service;

import java.util.Map;


import org.apache.ibatis.annotations.Param;

import pojo.Bill;
import tools.Page;

public interface BillService {
	/**
	 * 分页+查询
	 * @param map
	 * @return
	 */
	Page<Bill> findByPage(Map<String,Object> map);
	/**
	 * 查询单个对象
	 * @param id
	 * @return
	 */
	Bill findOne(Integer id);
	/**
	 * 添加
	 * @param bill
	 * @return
	 */
	Integer addBill(Bill bill);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Integer deleteBill(Integer id);
	/**
	 * 更新
	 * @param bill
	 * @return
	 */
	Integer updateBill(Bill bill);
}
