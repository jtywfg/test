package com.jty.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.lang.model.type.NullType;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jty.controller.base.BaseController;
import com.jty.model.Balance;
import com.jty.model.Constant;
import com.jty.model.FunIntegral;
import com.jty.model.User;
import com.jty.model.UserCar;
import com.jty.model.UserOrder;
import com.jty.service.UserService;
import com.jty.util.Grid;
import com.jty.utils.MD5;
import com.jty.utils.ResourceUtil;
import com.jty.utils.exception.BusinessException;


@Controller
@RequestMapping("/userOrder")
public class UserOrderController extends BaseController{
	@Resource(name=UserService.SERVICE_NAME)
    private UserService userService;
	

	
	/**
	 * 录入订单信息,并返积分到推荐订单账户
	 * @param userOrder
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-9-29 下午07:59:01
	 */
	@Transactional
	@RequestMapping(value = "addUserOrder", method = RequestMethod.POST)
	@ResponseBody
	public void addUserOrder(@ModelAttribute UserOrder userOrder){
		userOrder.setCreateTime(new Date());
		userOrder.setOrderCode("YY"+String.valueOf(System.currentTimeMillis()));
		Long id= userService.save(userOrder);
		
		//查询存在积分未消费的记录
		FunIntegral unUsedfunIntegral = new FunIntegral();
		unUsedfunIntegral.setUserId(Integer.valueOf(userOrder.getUserId().toString()));
		unUsedfunIntegral.setIsUsed(0);
		List<FunIntegral> unUsedfunIntegralList=userService.findList(unUsedfunIntegral);
		
		if(unUsedfunIntegralList!=null&&unUsedfunIntegralList.size()>0){
			Float usedFun=0F;
			//减单个记录的积分做记录
			for(FunIntegral f :unUsedfunIntegralList){
				f.setMinusIntegral(-f.getReturnIntegral());
				f.setMinusTime(new Date());
				f.setIsUsed(1);
				userService.update(f);
				usedFun=usedFun+f.getReturnIntegral();
			}
			//扣除总账
			Balance jianbalance =new Balance();
			jianbalance.setUserId(userOrder.getUserId());
			jianbalance=(Balance) userService.findOne(jianbalance);
			jianbalance.setBalanceIntegral(jianbalance.getBalanceIntegral()-usedFun);
			jianbalance.setLastUpdateTime(new Date());
			userService.update(jianbalance);
		}

		//存在推荐订单，则返积分到推荐人账户，并保存流水FunIntegral
		if(!"".equals(userOrder.getTjOrderCode())){
			//查询被推荐的订单信息
			UserOrder tiUserOrder = new UserOrder();
			tiUserOrder.setOrderCode(userOrder.getTjOrderCode());
			tiUserOrder=(UserOrder) userService.findOne(tiUserOrder);
			
			//查询订单被推荐的次数
			UserOrder tiCountUserOrder = new UserOrder();
			tiCountUserOrder.setTjOrderCode(userOrder.getTjOrderCode());
			List<UserOrder> tiCountUserOrderList=userService.findList(tiCountUserOrder);
			
	    	//获取返现比率
	    	Constant constant =new Constant();
	    	constant.setId(1);
	    	constant = (Constant) userService.findOne(constant);
			
			//第一次返积分
	    	Date nowTime = new Date();// 当前时间
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	Date endTime=null;
			try {
				endTime = sdf.parse(tiUserOrder.getEndTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(tiCountUserOrderList.size()==1&&endTime.after(nowTime)){
				//记流水
				FunIntegral funIntegral = new FunIntegral();
				funIntegral.setFromOrderId(Integer.valueOf(id.toString()));
				funIntegral.setOrderId(tiUserOrder.getId());
				funIntegral.setReturnRatio(constant.getOnePerson());
				Float returnIntegral = tiUserOrder.getTotalMoney()*constant.getOnePerson();
				funIntegral.setReturnIntegral(returnIntegral);
				funIntegral.setUserId(Integer.valueOf(tiUserOrder.getUserId().toString()));
				funIntegral.setCreateTime(new Date());
				funIntegral.setIsUsed(0);
				userService.save(funIntegral);
				
				//计入总账
				Balance balance =new Balance();
				balance.setUserId(tiUserOrder.getUserId());
				balance=(Balance) userService.findOne(balance);
				balance.setBalanceIntegral(balance.getBalanceIntegral()+returnIntegral);
				balance.setLastUpdateTime(new Date());
				userService.update(balance);
			}
			//第二次返积分
			if(tiCountUserOrderList.size()==2&&endTime.after(nowTime)){
				//记流水
				FunIntegral funIntegral = new FunIntegral();
				funIntegral.setFromOrderId(Integer.valueOf(id.toString()));
				funIntegral.setOrderId(tiUserOrder.getId());
				funIntegral.setReturnRatio(constant.getTwoPerson());
				Float returnIntegral = tiUserOrder.getTotalMoney()*constant.getTwoPerson();
				funIntegral.setReturnIntegral(returnIntegral);
				funIntegral.setUserId(Integer.valueOf(tiUserOrder.getUserId().toString()));
				funIntegral.setCreateTime(new Date());
				funIntegral.setIsUsed(0);
				userService.save(funIntegral);
				
				//计入总账
				Balance balance =new Balance();
				balance.setUserId(tiUserOrder.getUserId());
				balance=(Balance) userService.findOne(balance);
				balance.setBalanceIntegral(balance.getBalanceIntegral()+returnIntegral);
				balance.setLastUpdateTime(new Date());
				userService.update(balance);
				
			}
			//第三次返积分
			if(tiCountUserOrderList.size()==3&&endTime.after(nowTime)){
				//记流水
				FunIntegral funIntegral = new FunIntegral();
				funIntegral.setFromOrderId(Integer.valueOf(id.toString()));
				funIntegral.setOrderId(tiUserOrder.getId());
				funIntegral.setReturnRatio(constant.getThreePerson());
				Float returnIntegral = tiUserOrder.getTotalMoney()*constant.getThreePerson();
				funIntegral.setReturnIntegral(returnIntegral);
				funIntegral.setUserId(Integer.valueOf(tiUserOrder.getUserId().toString()));
				funIntegral.setCreateTime(new Date());
				funIntegral.setIsUsed(0);
				userService.save(funIntegral);
				
				//计入总账
				Balance balance =new Balance();
				balance.setUserId(tiUserOrder.getUserId());
				balance=(Balance) userService.findOne(balance);
				balance.setBalanceIntegral(balance.getBalanceIntegral()+returnIntegral);
				balance.setLastUpdateTime(new Date());
				userService.update(balance);
			}
			
		}
        this.printJson(ResourceUtil.getSuccessInfo("录入成功", userOrder));
	}
	/**
	 * 查询用户订单
	 * @param userId
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-9-30 上午09:21:45
	 */
	@RequestMapping(value = "findUserOrder", method = RequestMethod.GET)
	@ResponseBody
	public void findUserOrder(Long userId){
		try{
			UserOrder userOrder = new UserOrder();
			userOrder.setUserId(userId);
			List<UserOrder> userOrderList = userService.findList(userOrder);
			List<Map> list= new ArrayList<Map>();
			if(null != userOrderList && !userOrderList.isEmpty()) {
				for(UserOrder ucc : userOrderList) {
					Map m = new HashMap();
					m.put("value", ucc.getOrderCode());
					UserCar uc = new UserCar();
					uc.setId(ucc.getCarId());
					uc = (UserCar)userService.findOne(uc);
					m.put("text", ucc.getOrderCode() + " [ " +uc.getCarNum() + " ]");
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
