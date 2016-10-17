package com.jty.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jty.controller.base.BaseController;
import com.jty.model.Constant;
import com.jty.service.MenuService;
import com.jty.utils.ResourceUtil;



@Controller
@RequestMapping("/constant")
public class ConstantController extends BaseController{
	@Resource(name=MenuService.SERVICE_NAME)
	private MenuService menuService;
	
	/**
	 * 查询代理比率
	 * @param oneParam
	 * @param twoParam
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-17 上午09:42:59
	 */
	@RequestMapping(value = "findConstant", method = RequestMethod.GET)
	@ResponseBody
	public void findConstant(){
		try{
			Constant constant = new Constant();
			constant.setId(1);
			constant =(Constant) menuService.findOne(constant);
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", constant));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	
	/**
	 * 更新代理比率
	 * @param oneParam
	 * @param twoParam
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-17 上午09:37:59
	 */
	@RequestMapping(value = "updateConstant", method = RequestMethod.GET)
	@ResponseBody
	public void updateConstant(Float oneParam,Float twoParam,
			Float onePerson,Float twoPerson,Float threePerson){
		try{
			Constant constant = new Constant();
			constant.setId(1);
			constant.setOneParam(oneParam);
			constant.setTwoParam(twoParam);
			constant.setOnePerson(onePerson);
			constant.setTwoPerson(twoPerson);
			constant.setThreePerson(threePerson);
			menuService.update(constant);
			this.printJson(ResourceUtil.getSuccessInfo("更新成功", constant));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "更新失败"));
		}
	}
}

