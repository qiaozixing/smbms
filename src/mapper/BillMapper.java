package mapper;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pojo.Bill;

public interface BillMapper {
	/**
	 * 通过条件查询信息
	 * @param productName
	 * @param providerName
	 * @param isPayment
	 * @return
	 */
	List<Bill> findByPage(Map<String,Object> map);
	/**
	 * 查询单个对象
	 * @param id
	 * @return
	 */
	Bill findOne(@Param("id") Integer id);
	/**
	 * 分页+数据的总记录数
	 * @param map
	 * @return
	 */
	Integer findByCount(Map<String,Object> map);
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
	Integer deleteBill(@Param("id") Integer id);
	/**
	 * 更新
	 * @param bill
	 * @return
	 */
	Integer updateBill(Bill bill);
}
