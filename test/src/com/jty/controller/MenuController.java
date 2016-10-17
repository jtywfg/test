/**
 * VersonPController
 * author：wangjintao
 * version ：V1.00
 * Create date：2016-4-15 下午03:49:23
*/
package com.jty.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jty.controller.base.BaseController;
import com.jty.service.MenuService;
import com.jty.service.UserService;
import com.jty.utils.ResourceUtil;
import com.jty.utils.exception.BusinessException;


@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController{
	@Resource(name=MenuService.SERVICE_NAME)
	private MenuService menuService;
	
	@RequestMapping(value = "getTopMenu", method = RequestMethod.GET)
	@ResponseBody
	public void getTopMenu(){
		try{
			List<Map> list =menuService.getTopMenu();
 			System.out.println("================================");
			this.printJson(ResourceUtil.getSuccessInfo("获取菜单成功", list));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "获取菜单失败"));
		}
	}
	@RequestMapping(value = "getMenuByParent", method = RequestMethod.POST)
	@ResponseBody
	public void getMenuByParent(@RequestParam("PARENTID") String PARENTID){
		try{
			Map userMap =this.getCurrentUser();
			Integer userType=Integer.valueOf(userMap.get("userType").toString());
//			Long userId=Long.valueOf(userMap.get("id").toString());
			List<Map> list =menuService.getMenuByParent(PARENTID,userType);
			System.out.println("================================");
			this.printJson(ResourceUtil.getSuccessInfo("获取菜单成功", list));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "获取菜单失败"));
		}
	}
}

