package impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.BillMapper;
import pojo.Bill;
import service.BillService;
import tools.Page;
@Service("billService")
public class BillServiceImpl implements BillService {
	@Autowired
	private BillMapper billMapper;
	
	public void setBillMapper(BillMapper billMapper) {
		this.billMapper = billMapper;
	}

	public Page<Bill> findByPage(Map<String, Object> map) {
		List<Bill> list = billMapper.findByPage(map);
		Integer totalCount = billMapper.findByCount(map);
		Page<Bill> page = new Page<Bill>();
		page.setList(list);
		page.setTotalCount(totalCount);
		return page;
	}
	/**
	 * 添加
	 */
	public Integer addBill(Bill bill) {
		return billMapper.addBill(bill);
	}
	/**
	 * 删除
	 */
	public Integer deleteBill(Integer id) {
		return billMapper.deleteBill(id);
	}
	/**
	 * 更新
	 */
	public Integer updateBill(Bill bill) {
		return billMapper.updateBill(bill);
	}
	/**
	 * 查询单个对象
	 */
	public Bill findOne(Integer id) {
		return billMapper.findOne(id);
	}
	
}
