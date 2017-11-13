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

import com.alibaba.fastjson.JSONObject;

import pojo.Provider;
import service.ProviderService;
import tools.Page;

@Controller
public class ProviderController {
	@Autowired
	private ProviderService provService;

	public void setProvService(ProviderService provService) {
		this.provService = provService;
	}
	/**
	 * 查询所有供应商信息
	 * @param model
	 * @param proCode
	 * @param proName
	 * @return
	 */
	@RequestMapping("provider.html")
	public String showProv(Model model,
			@RequestParam(value="proCode",required=false) String proCode,
			@RequestParam(value="proName",required=false) String proName,
			@RequestParam(value="pageIndex",required=false) String index){
		Map<String,Object> map = new HashMap<String,Object>();
		if(index == null || "".equals(index)){
			index = "1";
		}
		if(proCode != null && !"".equals(proCode)){
			map.put("proCode", proCode);
		}
		if(proName != null && !"".equals(proName)){
			map.put("proName", proName);
		}
		map.put("index", (Integer.parseInt(index) - 1) * Page.SIZE);
		map.put("size", Page.SIZE);
		Page page = new Page();
		List<Provider> list = provService.findByAll(map);
		Integer totalCount = provService.findByCount(map);
		page.setTotalCount(totalCount);
		page.setList(list);
		page.setIndex(Integer.parseInt(index));
		model.addAttribute("page", page);
		model.addAttribute("proCode", map.get("proCode"));
		model.addAttribute("proName", map.get("proName"));
		return "providerlist";
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(value="providerAdd.html",method=RequestMethod.GET)
	public String provAdd(){
		return "provideradd";
	}
	/**
	 * 添加操作
	 * @param model
	 * @param provider
	 * @return
	 */
	@RequestMapping(value="providerAdd.html",method=RequestMethod.POST)
	public String providerAdd(Model model,Provider provider){
		Integer result = provService.addProvider(provider);
		if(result == 1){
			return "redirect:provider.html";
		}else{
			model.addAttribute("message", "抱歉因为网络原因添加失败！");
			return "provideradd";
		}
	}
	/**
	 * 删除供应商
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="providerdel.json",method=RequestMethod.POST)
	@ResponseBody
	public Object delProvider(Model model,@RequestParam("proid") String id){
		Integer result = provService.deleteProvider(Integer.parseInt(id));
		JSONObject json = new JSONObject();
		if(result > 0){
			json.put("delResult", "true");
		}else{			
			json.put("delResult","false");
		}
		return json;
	}
	/**
	 * 查看供应商详情
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="providerDetail.html")
	public String showDetail(Model model,@RequestParam("id") String id){
		Provider provider = provService.findProvOne(Integer.parseInt(id));
		if(provider != null){
			model.addAttribute("provider", provider);
			return "providerview";		
		}else{
			return null;
		}
	}
	/**
	 * 将供应商信息传入更新页面
	 * @param model
	 * @param provider
	 * @return
	 */
	@RequestMapping(value="providerModify.html")
	public String providerModify(Model model,@RequestParam("id") String id){
		Provider provider = provService.findProvOne(Integer.parseInt(id));
		if(provider != null){
			model.addAttribute("provider", provider);
			return "providermodify";		
		}else{
			return null;
		}
	}
	/**
	 * 更新操作
	 * @param model
	 * @param provider
	 * @return
	 */
	@RequestMapping(value="updateProvider.html",method=RequestMethod.POST)
	public String updateProvider(Model model,Provider provider){
		Integer result = provService.updateProvider(provider);
		if(result > 0){
			return "redirect:provider.html";
		}else{
			model.addAttribute("message", "抱歉因为网络原因更新失败！");
			return "providermodify";
		}
	}
}
