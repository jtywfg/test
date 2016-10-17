package com.jty.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.lang.model.type.NullType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jty.controller.base.BaseController;
import com.jty.model.User;
import com.jty.service.UserService;
import com.jty.util.Grid;
import com.jty.utils.MD5;
import com.jty.utils.ResourceUtil;
import com.jty.utils.exception.BusinessException;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Resource(name=UserService.SERVICE_NAME)
    private UserService userService;
	
	/**
	 * 登录
	 * @param username
	 * @param pwd
	 * 
	 */
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public void login(@RequestParam("username") String username,@RequestParam("passWord") String passWord){
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("username", username);
			params.put("pwd", passWord);
			String errorMsg = ResourceUtil.getErrorMsg(params);
			if(!"".equals(errorMsg)){
				throw new BusinessException(errorMsg, 0, false);
			}
			Map userInfo = userService.login(username, passWord);
			this.setCurrentUser(userInfo);
			this.printJson(ResourceUtil.getSuccessInfo("登录成功", userInfo));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureStr(e));
		}
	}
	
	
	/**
	 * 录入信息
	 * @param user
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-14 下午03:55:14
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public void register(@ModelAttribute User user){
		try{
			Map userMap =this.getCurrentUser();
			Long userId=Long.valueOf(userMap.get("id").toString());
			user.setTjUserId(userId);
			userService.register(user);
			this.printJson(ResourceUtil.getSuccessInfo("录入成功", user));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureStr(e));
		}
	}
	
	/**
	 * 退出登录
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午08:04:06
	 */
	@RequestMapping(value = "loginOut", method = RequestMethod.GET)
	@ResponseBody
	public void loginOut(){
		try{
			this.removeCurrentUser();
			this.printJson(ResourceUtil.getSuccessInfo("退出成功", null));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "退出失败"));
		}
	}
	
	/**
	 * 修改密码
	 * @param userId
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午08:20:31
	 */
	@RequestMapping(value = "changePassword", method = RequestMethod.GET)
	@ResponseBody
	public void changePassword(Integer id, String password, String type) {
		try {
			User u = new User();
			u.setId(id);
			u = (User) userService.findOne(u);
			if(u != null) {
				if("1".equals(type)) {
					password = "123456";
				}
				u.setPassword(MD5.MD5Encode(password));
				userService.update(u);
				this.printJson(ResourceUtil.getSuccessInfo("修改成功", ""));
			} else {
				this.printJson(ResourceUtil.getFailureInfo(0, "用户已不存在"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "修改失败"));
		}
	}
	/**
	 * 查询用户信息
	 * @param userId
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午08:27:07
	 */
	@RequestMapping(value = "findUser", method = RequestMethod.GET)
	@ResponseBody
	public void findUser(Integer id){
		try{
			User u = new User();
			u.setId(id);
			u = (User) userService.findOne(u);
			if(u.getTjUserId()!=null){
				User tuijianUser = new User();
				tuijianUser.setId(Integer.valueOf(u.getTjUserId().toString()));
				tuijianUser = (User) userService.findOne(tuijianUser);
				u.setTjUserName(tuijianUser.getUserName());
			}
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", u));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	
	/**
	 * 查询登录用户信息
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午08:27:07
	 */
	@RequestMapping(value = "findLoginUser", method = RequestMethod.GET)
	@ResponseBody
	public void findLoginUser(){
		try{
			Map userMap =this.getCurrentUser();
			Integer userId=Integer.valueOf(userMap.get("id").toString());
			User u = new User();
			u.setId(userId);
			u = (User) userService.findOne(u);
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", u));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午08:33:11
	 */
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	@ResponseBody
	public void updateUser(@ModelAttribute User user){
		try{
			User u = new User();
			u.setId(user.getId());
			u = (User) userService.findOne(u);
			u.setTelphone(user.getTelphone());
			u.setRealName(user.getRealName());
			u.setDrivingLicence(user.getDrivingLicence());
			u.setCardNum(user.getCardNum());
			u.setBank(user.getBank());
			u.setBankCard(user.getBankCard());
			u.setBankAccount(user.getBankAccount());
			u.setBirthday(user.getBirthday());
			userService.update(u);
			this.printJson(ResourceUtil.getSuccessInfo("更新成功", u));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "更新失败"));
		}
	}
	
	/**
	 * 分页查询用户信息
	 * @param userId
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午08:27:07
	 */
	@RequestMapping(value = "findUserList", method = RequestMethod.GET)
	@ResponseBody
	public void findUserList(String userName,String realName,String telphone,
			String cardNum,String drivingLicence,
			String minDate, String maxDate,Integer page,Integer rows){
		try{
			
			Map userMap =this.getCurrentUser();
			Integer userType=Integer.valueOf(userMap.get("userType").toString());
			Integer id=Integer.valueOf(userMap.get("id").toString());
			Map parmer = new HashMap();
			if(userType==0){ //会员查出自己的与自己推荐的用户
				parmer.put("id", id);
			}
			if(realName!=null&&!realName.equals("")){
				realName=new String(realName.getBytes("iso-8859-1"), "utf-8");
				parmer.put("realName", realName);
			}
			parmer.put("userName", userName);
			parmer.put("telphone", telphone);
			parmer.put("cardNum", cardNum);
			parmer.put("drivingLicence", drivingLicence);
			parmer.put("minDate", minDate);
			parmer.put("maxDate", maxDate);
			Grid userList = userService.findUserList(page,rows,parmer);
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", userList));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	/**
	 * 禁用
	 * @param id
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午11:13:04
	 */
	@RequestMapping(value = "freezedUser", method = RequestMethod.GET)
	@ResponseBody
	public void freezedUser(Integer id,String type){
		try{
			User u = new User();
			u.setId(id);
			u = (User) userService.findOne(u);
			//禁用
			if(type.equals("1")){
				u.setFreezed(1);
			}if(type.equals("0")){
				u.setFreezed(0);
			}
			userService.update(u);
			this.printJson(ResourceUtil.getSuccessInfo("禁用成功", u));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "禁用失败"));
		}
	}
	
	/**
	 * 删除用户
	 * @param id
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午11:13:04
	 */
	@RequestMapping(value = "deleteUser", method = RequestMethod.GET)
	@ResponseBody
	public void deleteUser(Integer id){
		try{
			User u = new User();
			u.setId(id);
			u = (User) userService.findOne(u);
			u.setDeleteFlag(1);
			userService.update(u);
			this.printJson(ResourceUtil.getSuccessInfo("删除成功", u));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "删除失败"));
		}
	}
	
	/**
	 * 查询所有用户信息
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午08:27:07
	 */
	@RequestMapping(value = "findAllUser", method = RequestMethod.GET)
	@ResponseBody
	public void findAllUser(){
		try{
			User u = new User();
//			List<User> uList = userService.findList(u);
			List<User> uList = userService.findNodeleteList();

			List<Map> list= new ArrayList<Map>();
			for(User uu :uList){
				if(!uu.getUserName().equals("admin")){
					Map m = new HashMap();
					m.put("value", uu.getId());
					m.put("text", uu.getRealName());
					list.add(m);
				}
			}
			this.printJson(JSON.toJSONString(list));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
}
