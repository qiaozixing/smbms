package controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import pojo.Bill;
import pojo.Provider;
import service.BillService;
import service.ProviderService;
import tools.Page;
@Controller
public class BillController {
	@Autowired
	private BillService billService;

	public void setBillService(BillService billService) {
		this.billService = billService;
	}
	@Autowired
	private ProviderService provService;
	
	public void setProvService(ProviderService provService) {
		this.provService = provService;
	}
	@RequestMapping(value="bill.html",method=RequestMethod.GET)
	public String showBill(Model model){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("index", 1);
		map.put("size", 5);
		Page<Bill> page = billService.findByPage(map);
		page.setIndex(1);
		List<Provider> providerList = provService.findAll();
		model.addAttribute("page",page);
		model.addAttribute("billList",page.getList());
		model.addAttribute("providerList",providerList);
		return "billlist";
	}
	/**
	 * 查询+分页
	 * @param model
	 * @param productName
	 * @param providerId
	 * @param isPayment
	 * @param index
	 * @return
	 */
	@RequestMapping(value="bill.html",method=RequestMethod.POST)
	public String showBill(Model model,
			@RequestParam(value="productName",required=false)String productName,
			@RequestParam(value="providerId",required=false)String providerId,
			@RequestParam(value="isPayment",required=false)String isPayment,
			@RequestParam(value="pageIndex",required=false)String index){
		System.out.println("--------:" + providerId);
		Map<String,Object> map = new HashMap<String, Object>();
		if(index == null || "".equals(index)){
			index = "1";
		}
		if(productName != null && !"".equals(productName)){
			map.put("productName", productName);
		}
		if(providerId != null && !"".equals(providerId) && 
				!"0".equals(providerId)){
			map.put("providerId", providerId);
		}
		if(isPayment != null && !"".equals(isPayment) && !"0".equals(isPayment)){
			map.put("isPayment", isPayment);
		}
		map.put("index", (Integer.parseInt(index) - 1) * Page.SIZE);
		map.put("size", Page.SIZE);
		Page<Bill> page = billService.findByPage(map);
		page.setIndex(Integer.parseInt(index));
		List<Provider> providerList = provService.findAll();
		model.addAttribute("page",page);
		model.addAttribute("billList",page.getList());
		model.addAttribute("providerList",providerList);
		model.addAttribute("productName", map.get("productName"));
		model.addAttribute("providerId", map.get("providerId"));
		model.addAttribute("isPayment", map.get("isPayment"));
		return "billlist";
	}
	/**
	 * 跳转到添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="billAdd.html",method=RequestMethod.GET)
	public String billAdd(Model model){
			return "billadd";
	}
	/**
	 * 添加
	 * @param model
	 * @param bill
	 * @return
	 */
	@RequestMapping(value="billAdd.html",method=RequestMethod.POST)
	public String billAdd(Model model,Bill bill){
		Integer result = billService.addBill(bill);
		if(result > 0){
			return "frame";
		}else{
			model.addAttribute("message", "抱歉因为网络原因添加失败！");
			return "billadd";
		}
	}
	/**
	 * 删除
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="billdel.json",method=RequestMethod.POST)
	@ResponseBody
	public Object delBill(Model model,
			@RequestParam(value="billid",required=false) String id){
		JSONObject json =  new  JSONObject();
		Integer result = billService.deleteBill(Integer.parseInt(id));
		if(result > 0){
			json.put("delResult","true");
		}else{
			json.put("delResult","false");
		}
		return json;
	}
	/**
	 * 查询单个对象
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="billDetail.html")
	public String showOne(Model model,
			@RequestParam(value="billid",required=false) String id){
		Bill bill = billService.findOne(Integer.parseInt(id));
		if(bill != null){
			model.addAttribute("bill", bill);
			return "billview";
		}
		return "billlist";
	}
	/**
	 * 将对象出入更新页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="billModify.html")
	public String showModify(Model model,
			@RequestParam(value="billid",required=false) String id){
		Bill bill = billService.findOne(Integer.parseInt(id));
		if(bill != null){
			model.addAttribute("bill", bill);
			return "billmodify";
		}
		return "billlist";
	}
	
	@RequestMapping(value="billUpdate.html",method=RequestMethod.POST)
	public String updateBill(Model model,Bill bill){
		Integer result = billService.updateBill(bill);
		if(result > 0){
			return "redirect:bill.html";
		}else{
			model.addAttribute("message", "抱歉因为网络原因更新失败！");
			return "billadd";
		}
		
	}
	@RequestMapping(value="provider.json")
	@ResponseBody
	public Object getProviderList(){
		List<Provider> list=provService.findAll();
		return JSON.toJSONString(list);
	}
}
