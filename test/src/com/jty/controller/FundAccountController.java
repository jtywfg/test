package com.jty.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jty.controller.base.BaseController;
import com.jty.model.Balance;
import com.jty.model.FundAccount;
import com.jty.service.FundAccountService;
import com.jty.util.Grid;
import com.jty.utils.ResourceUtil;



@Controller
@RequestMapping("/fundaccount")
public class FundAccountController extends BaseController{
	@Resource(name=FundAccountService.SERVICE_NAME)
	private FundAccountService fundAccountService;
	
	/**
	 * 
	 * 查询返金记录，admin查询所有，个人查询自己
	 * author：wangjintao
	 * type :-1 提现 1返金
	 * version ：V1.00
	 * Create date：2016-8-17 上午11:38:50
	 */
	@RequestMapping(value = "fundAccountList", method = RequestMethod.GET)
	@ResponseBody
	public void fundAccountList(Integer type,Integer page,Integer rows,String realName){
		try{
			Map userMap =this.getCurrentUser();
			Long userId=Long.valueOf(userMap.get("id").toString());
			Integer userType=Integer.valueOf(userMap.get("userType").toString());
			if(userType!=1){
				userId=null;
			}
			Grid fundAccountList=fundAccountService.fundaccountList(userId,type,page,rows,realName);
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", fundAccountList));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	
	/**
	 * @author wufugui
	 * @version 2016-8-20
	 * @Des 查询本人可提现的金额=总返金-已提/提ing
	 */
	@RequestMapping(value = "findUsableMoney", method = RequestMethod.GET)
	@ResponseBody
	public void findUsableMoney(){
		try{
			Map userMap =this.getCurrentUser();
			Long userId=Long.valueOf(userMap.get("id").toString());
			Integer userType=Integer.valueOf(userMap.get("userType").toString());
			Double usableMoney=0d;
			if(userType!=1||userType==1){//个人的
				Double 	returnMoney=fundAccountService.findReturnMoney(userId);
				Double 	drawMoney=fundAccountService.findDrawMoney(userId);
				if(returnMoney!=null){
					if(drawMoney==null){
						usableMoney=returnMoney;
					}else{
						usableMoney=returnMoney-drawMoney;
						
					}
				}
			}else{//管理员
				
			}
			
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", usableMoney));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	/**
	 * @author wufugui
	 * @version 2016-8-20
	 * @Des 客户申请提现功能
	 */
	@RequestMapping(value = "applyUsableMoney", method = RequestMethod.POST)
	@ResponseBody
	public void applyUsableMoney(@RequestParam(value="drawMoney",required=true) Double drawMoney){
		try{
			
			Map userMap =this.getCurrentUser();
			Long userId=Long.valueOf(userMap.get("id").toString());
//			Integer userType=Integer.valueOf(userMap.get("userType").toString());
			/*=======核验可提金额==================================================*/
			Double usableMoney=0d;//可提的
			Double 	returnMoney=fundAccountService.findReturnMoney(userId);
			Double 	drawMoneyPass=fundAccountService.findDrawMoney(userId);//已提或提ing
			if(returnMoney!=null){
				if(drawMoneyPass==null){
					usableMoney=returnMoney;
				}else{
					usableMoney=returnMoney-drawMoneyPass;
					
				}
			}
			
			/*=========================================================*/
			if(drawMoney.compareTo(usableMoney)<=0){
				FundAccount fa=new FundAccount();
				fa.setCreateTime(new Date());
				fa.setReturnAmount(drawMoney);
				fa.setIsCheck(0);//待审核
				fa.setType(-1);//提现
				fa.setAgentLevel(0);
				fa.setUserId(userId);
				fundAccountService.save(fa);
				this.printJson(ResourceUtil.getSuccessInfo("申请成功", fa));
			}else{
				
				this.printJson(ResourceUtil.getFailureInfo(0, "申请失败,超出可申请金额,请点击刷新按钮"));
			}
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "申请失败"));
		}
	}
	/**
	 * @author wufugui
	 * @version 2016-8-22
	 * @Des 针对提现记录--审核分页列表
	 */
	@RequestMapping(value = "findDrawList", method = RequestMethod.GET)
	@ResponseBody
	public void findDrawList(Integer page,Integer rows){
		try{
			Map userMap =this.getCurrentUser();
			Long userId=Long.valueOf(userMap.get("id").toString());
			Integer userType=Integer.valueOf(userMap.get("userType").toString());
			if(userType!=1){
				userId=null;
			}
			Grid drawList=fundAccountService.findDrawList(userId,page,rows);
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", drawList));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	
	//审核功能
	@RequestMapping(value = "reviewDraw", method = RequestMethod.POST)
	@ResponseBody
	public void reviewDraw(Long id,Integer isCheck){
		try{
			FundAccount fa = new FundAccount();
			fa.setId(id);
			fa = (FundAccount) fundAccountService.findOne(fa);
			if(fa.getIsCheck()==0){
				Long balanceUserId=fa.getUserId();
				Double drawAmount=fa.getReturnAmount();
				fa.setIsCheck(isCheck);
				fa.setLastUpdateTime(new Date());
				fundAccountService.update(fa);
				/*--------添加一段，记得要减掉用户的余额表---------------------*/
				if(isCheck==1){
					Balance balance =new Balance();
					balance.setUserId(balanceUserId);
					balance=(Balance) fundAccountService.findOne(balance);
					if(balance!=null){
						balance.setBalanceAmount(balance.getBalanceAmount()-drawAmount);
						balance.setLastUpdateTime(new Date());
						fundAccountService.update(balance);
					}
				}
				this.printJson(ResourceUtil.getSuccessInfo("审核成功", fa));
			}else{
				this.printJson(ResourceUtil.getSuccessInfo("已审核过", fa));
			}
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "审核失败"));
		}
	}
	
}

