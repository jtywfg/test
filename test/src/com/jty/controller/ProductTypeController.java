/**
 * VersonPController
 * author：wangjintao
 * version ：V1.00
 * Create date：2016-4-15 下午03:49:23
*/
package com.jty.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jty.controller.base.BaseController;
import com.jty.model.ProductType;
import com.jty.service.OrderService;
import com.jty.service.ProductTypeService;
import com.jty.utils.ResourceUtil;


@Controller
@RequestMapping("/product")
public class ProductTypeController extends BaseController{
	@Resource(name=ProductTypeService.SERVICE_NAME)
	private ProductTypeService productTypeService;
	@Resource(name=OrderService.SERVICE_NAME)
	private OrderService orderService;
	
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	@ResponseBody
	public void addProduct(@ModelAttribute ProductType productType){
		try{
			ProductType pt = new ProductType();
			pt.setMoney(productType.getMoney());
			pt.setProductName(productType.getProductName());
			pt.setProductNo(productType.getProductNo());
			pt.setRate(productType.getRate());
			pt.setIsRate(productType.getIsRate());
			if(productType.getIsRate()==0){
				pt.setPrice(productType.getMoney().floatValue());
			}
			productTypeService.save(pt);
			this.printJson(ResourceUtil.getSuccessInfo("保存成功", pt));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "保存失败"));
		}
	}
	/**
	 * @author wufugui
	 * @version 2016-8-16
	 * @Des 
	 */
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	@ResponseBody
	public void updateProduct(ProductType productType){
		try{
			productTypeService.update(productType);
			this.printJson(ResourceUtil.getSuccessInfo("更新成功", productType));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "更新失败"));
		}
	}
	@RequestMapping(value = "findOne", method = RequestMethod.GET)
	@ResponseBody
	public void findOne(Integer id){
		try{
			ProductType pt = new ProductType();
			pt.setId(id);
			pt=(ProductType) productTypeService.findOne(pt);
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", pt));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	/**
	 * @author wufugui
	 * @version 2016-8-19
	 * @Des 删除险种之前，先检验订单中是否存在该险种
	 */
	@RequestMapping(value = "deleteProduct", method = RequestMethod.POST)
	@ResponseBody
	public void deleteProduct(Integer id){
		try{
			Integer orderCount=orderService.checkOrderCount(id);
//			Integer orderCount=1;
			if(orderCount==0){
				ProductType pt = new ProductType();
				pt.setId(id);
				pt=(ProductType) productTypeService.findOne(pt);
				productTypeService.delete(pt);
				this.printJson(ResourceUtil.getSuccessInfo("删除成功", pt));
			}else{
				this.printJson(ResourceUtil.getFailureInfo(0, "不能删除，因已有订单购买此险"));
				
			}
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "删除失败"));
		}
	}
	/**
	 * @author wufugui
	 * @version 2016-8-14
	 * @Des 险种类别
	 */
	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	@ResponseBody
	public void getProduct(){
		try{
			ProductType pt=new ProductType();
			List<Map> list =productTypeService.findList(pt);
 			System.out.println("================================");
			this.printJson(ResourceUtil.getSuccessRows("获取成功", list));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "获取失败"));
		}
	}
}

