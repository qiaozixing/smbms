
package controller;

import java.util.Date;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import pojo.Role;
import pojo.User;
import service.RoleService;
import service.UserService;
import tools.Page;

@Controller
public class UserController {
		@Autowired
		private UserService userService;
		public void setUserService(UserService userService) {
			this.userService = userService;
		}
		@Autowired
		private RoleService roleService;
		
		public void setRoleService(RoleService roleService) {
			this.roleService = roleService;
		}
		/**
		 * login get TODO(get方法提交只是用来跳转login界面的)
		 * @return
		 */
		@RequestMapping(value="login.html"
					,method=RequestMethod.GET)
		public String login(){
			return "../login";
		}
		/**
		 * islogin TODO(post提交用来做用户登录方法实现的)
		 * @param model
		 * @param userCode
		 * @param userPassword
		 * @param session
		 * @return
		 */
		@RequestMapping(value="login.html"
				,method=RequestMethod.POST)
		public String islogin(Model model,
				@RequestParam("userCode")String userCode,
				@RequestParam("userPassword")String userPassword,
				HttpSession session){
			User user=userService.findByName(userCode);
			if(user!=null){
				if(user.getUserPassword().equals(userPassword)){
					if(session.getAttribute("USER_CODE")!=null){
						session.removeAttribute("USER_CODE");
					}
					session.setAttribute("USER_CODE", user);
					session.setMaxInactiveInterval(60000);
					return "frame";
				}else {
					model.addAttribute("error", "抱歉密码不正确");
					return "../login";	
				}
			}else{
				model.addAttribute("error", "抱歉账户名不正确");
				return "../login";	
			}
			
		}
		/**
		 * userAdd TODO(进去用户新增界面)
		 * @return
		 */
		@RequestMapping(value="userAdd.html"
				,method=RequestMethod.GET)
		public String userAdd(){
			//userAdd.html
			return "useradd";
		}
		/**
		 * roleList TODO(跳转添加页面的时候实现角色的展示)
		 * @return
		 */
		@RequestMapping("role.json")
		@ResponseBody
		public Object roleList(){
			List<Role> list=roleService.findByRoleAll();
			return JSON.toJSONString(list);
		}	
		/**
		 * 	getUserCode TODO(判断该用户编号是否存在)
		 * @param userCode
		 * @return
		 */
		@RequestMapping("getUserCode.json")
		@ResponseBody
		public Object getUserCode(@RequestParam("userCode")
			String userCode){
			User user=userService.findByName(userCode);
			JSONObject json=new JSONObject();
			if(user!=null){
				 json.put("userCode", "exist");
			}else{
				json.put("userCode", "success");
			}
			return json;
		}
		/**
		 * 添加用户
		 * @param model
		 * @param user
		 * @param session
		 * @return
		 */
		@RequestMapping(value="userAdd.html",method=RequestMethod.POST)
		public String userAdd(Model model,User user,HttpSession session){
			if(session.getAttribute("USER_CODE")!=null){
				User u=(User)session.getAttribute("USER_CODE");
				user.setCreatedBy(u.getId());	
			}else {
				user.setCreatedBy(1);
			}
			user.setCreationDate(new Date());
			Integer id=userService.saveUser(user);
			if (id>0) {
				return "redirect:user.html";
			}else {
				model.addAttribute("message", "抱歉因为网络原因添加失败！");
				return "useradd";
			}
			
		}
		/**
		 * userPage TODO(点击用户模块显示首页面信息)
		 * @param model
		 * @return
		 */
		@RequestMapping(value="user.html",method=RequestMethod.GET)
		public String userShow(
				Model model,
				@RequestParam(value = "pageIndex", required = false) Integer index,
				@RequestParam(value = "queryname", required = false) String queryname,
				@RequestParam(value = "queryUserRole", required = false) String queryUserRole) {
			List<Role> list = roleService.findByRoleAll();
			model.addAttribute("roleList", list);
			System.out.println("---------:" + queryname);
			// 这里应该有一个首页面的展示 Page分页对象 显示首页信息
			// 用来实现分页展示
			if ("".equals(index) || null == index) {
				index = 1;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if (queryname != null && !"".equals(queryname)) {
				map.put("userName", queryname);
			}
			if (queryUserRole != null && !"".equals(queryUserRole) && !"0".equals(queryUserRole)) {
				map.put("roleId", queryUserRole);
			}
			map.put("index", (index - 1) * Page.SIZE);
			map.put("size", Page.SIZE);
			Page<User> page = userService.findByPage(map);
			page.setIndex(index);
			model.addAttribute("queryname", map.get("userName"));
			model.addAttribute("queryUserRole", map.get("roleId"));
			model.addAttribute("page", page);
			return "userlist";
		}
		/**
		 * 跳转到用户详情
		 * @param model
		 * @param userCode
		 * @return
		 */
		@RequestMapping(value="userview.html")
		public String userDetail(Model model,
				@RequestParam("uid") String id){
			User user = userService.findById(Integer.parseInt(id));
			if(user != null){
				model.addAttribute("user", user);
				return "userview";			
			}else{
				return "userlist";
			}
		}
		/**
		 * 将用户信息传入更新页面
		 * @param model
		 * @param userCode
		 * @return
		 */
		@RequestMapping(value="usermodify.html")
		public String intoUpdate(Model model,
				@RequestParam("uid") String id){
			User user = userService.findById(Integer.parseInt(id));
			if(user != null){
				model.addAttribute("user", user);
				return "usermodify";			
			}else{
				return "userlist";
			}
		}
		/**
		 * 更新用户信息
		 * @param model
		 * @param user
		 * @param session
		 * @return
		 */
		@RequestMapping(value="userUpdate.html",method=RequestMethod.POST)
		public String updateUser(Model model,User user,HttpSession session){
			if(session.getAttribute("USER_CODE") != null){
				User user2 = (User)session.getAttribute("USER_CODE");
				user.setModifyBy(user2.getId());
			}else{
				user.setModifyBy(1);
			}
			Integer result =userService.updateUser(user);
			if(result > 0){
				return "redirect:user.html";
			}else{
				model.addAttribute("message", "抱歉因为网络原因更新失败！");
				return "usermodify";
			}
		}
		@RequestMapping(value="userdel.json",method=RequestMethod.POST)
		@ResponseBody
		public Object deleteUser(Model model,@RequestParam("uid") String id){
			Integer result =  userService.deleteUser(Integer.parseInt(id));
			JSONObject json = new JSONObject();
			if(result > 0){
				json.put("delResult", "true");
			}else{
				json.put("delResult", "false");
			}
			return json;
		}
		/**
		 * 跳转到密码修改
		 * @return
		 */
		@RequestMapping(value="pwdmodify.html",method=RequestMethod.GET)
		public String pwdModify(){
			return "pwdmodify";
		}
		/**
		 * 获取原密码
		 * @param model
		 * @param pwd
		 * @return
		 */
		@RequestMapping(value="updatePwd.json",method=RequestMethod.POST)
		@ResponseBody
		public Object pwdModify2(Model model,
				@RequestParam(value="oldPwd",required=false) String pwd,
				HttpSession session){
			JSONObject json = new JSONObject();
			if(session.getAttribute("USER_CODE") != null){
				User user = (User)session.getAttribute("USER_CODE");
				String oldPwd = user.getUserPassword();
				if(oldPwd == pwd){
					json.put("result", "true");
				}else{
					json.put("result", "false");
				}
			}
			return json;
		}
		
		@RequestMapping(value="updatePwd.html",method=RequestMethod.POST)
		public String updatePwd(Model model,
				@RequestParam(value="newpassword",required=false) String newPwd,
				@RequestParam(value="id",required=false) Integer id){
			Integer result = userService.updatePwd(newPwd,id);
			if(result > 0){
				return "redirect:user.html";
			}else{
				model.addAttribute("message", "抱歉因为网络原因更新失败！");
				return "pwdmodify";
			}
		}
}

