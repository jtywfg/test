package com.jty.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jty.controller.base.BaseController;
import com.jty.model.UserCar;
import com.jty.model.UserOrder;
import com.jty.service.UserService;
import com.jty.utils.ResourceUtil;


@Controller
@RequestMapping("/userCar")
public class UserCarController extends BaseController{
	@Resource(name=UserService.SERVICE_NAME)
    private UserService userService;
	

	
	/**
	 * 录入用户拥有的车辆信息
	 * @param userCar
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-14 下午03:55:14
	 */
	@RequestMapping(value = "addUserCar", method = RequestMethod.POST)
	@ResponseBody
	public void addUserCar(@ModelAttribute UserCar userCar){
		try{
            UserCar	oldUserCar = new UserCar();
            oldUserCar.setVehicleLicence(userCar.getVehicleLicence());
            oldUserCar =(UserCar) userService.findOne(oldUserCar);
            if(oldUserCar==null){
    			userService.save(userCar);
    			this.printJson(ResourceUtil.getSuccessInfo("录入成功", userCar));
            }else{
            	this.printJson(ResourceUtil.getFailureInfo(0, "录入失败,行驶证重复。"));
            }
		}catch(DataIntegrityViolationException e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0,"录入信息太长或不符合要求"));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureStr(e));
		}
	}
	/**
	 * 根据用户查拥有的车信息
	 * @param userId
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-9-29 下午07:42:26
	 */
	@RequestMapping(value = "findUserCar", method = RequestMethod.GET)
	@ResponseBody
	public void findUserCar(Integer userId){
		try{
			UserCar uc = new UserCar();
			uc.setUserId(userId);
			List<UserCar> ucList = userService.findList(uc);
			List<Map> list= new ArrayList<Map>();
			if(null != ucList && !ucList.isEmpty()) {
				for(UserCar ucc :ucList){
					Map m = new HashMap();
					m.put("value", ucc.getId());
					m.put("text", ucc.getCarNum());
					list.add(m);
				}
			}
			this.printJson(JSON.toJSONString(list));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	/**
	 * 更新车辆信息
	 * @param userCar
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-9-30 下午08:41:30
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "updateUserCar", method = RequestMethod.POST)
	@ResponseBody
	public void updateUserCar(@ModelAttribute UserCar userCar){
		try{
			UserCar	oldUserCar = new UserCar();
            oldUserCar.setId(userCar.getId());
            oldUserCar =(UserCar) userService.findOne(oldUserCar);
            
            oldUserCar.setCarNum(userCar.getCarNum());
            //输入的行驶证和老行驶证不同
        	if(!userCar.getVehicleLicence().equals(oldUserCar.getVehicleLicence())){
        		//核查新的行驶证是否重复
        		UserCar	oldUserCar2 = new UserCar();
                oldUserCar2.setVehicleLicence(userCar.getVehicleLicence());
                oldUserCar2 =(UserCar) userService.findOne(oldUserCar2);
                //不存在则更新
        		if(oldUserCar2==null){
        			oldUserCar.setVehicleLicence(userCar.getVehicleLicence());
        			userService.update(oldUserCar);
        			this.printJson(ResourceUtil.getSuccessInfo("修改成功", userCar));
        		}else{
        			this.printJson(ResourceUtil.getFailureInfo(0, "修改失败,行驶证已经存在"));
        		}
        	}else{
    			userService.update(oldUserCar);
    			this.printJson(ResourceUtil.getSuccessInfo("修改成功", userCar));
    		}

		}catch(DataIntegrityViolationException e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0,"录入信息太长或不符合要求"));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureStr(e));
		}
	}
	/**
	 * 删除车辆信息
	 * @param id
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-9-30 下午08:41:56
	 */
	@RequestMapping(value = "deleteUserCar", method = RequestMethod.GET)
	@ResponseBody
	public void deleteUserCar(Integer id){
		try{
			UserOrder countUserOrder = new UserOrder();
			countUserOrder.setCarId(id);
			List<UserOrder> countUserOrderList=userService.findList(countUserOrder);
			if(countUserOrderList == null || countUserOrderList.isEmpty()){
				UserCar	oldUserCar = new UserCar();
	            oldUserCar.setId(id);
	            userService.delete(oldUserCar);
	            this.printJson(ResourceUtil.getSuccessInfo("删除成功", ""));
			}else{
				this.printJson(ResourceUtil.getFailureInfo(0, "存在订单不能删除"));
			}
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureStr(e));
		}
	}
	@RequestMapping(value = "findUserCar2", method = RequestMethod.GET)
	@ResponseBody
	public void findUserCar2(Integer userId){
		try{
			UserCar uc = new UserCar();
			uc.setUserId(userId);
			List<UserCar> ucList = userService.findList(uc);
			this.printJson(ResourceUtil.getSuccessRows("", ucList));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, ""));
		}
	}
	
}
