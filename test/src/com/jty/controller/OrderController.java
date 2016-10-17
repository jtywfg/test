/**
 * VersonPController
 * author：wangjintao
 * version ：V1.00
 * Create date：2016-4-15 下午03:49:23
*/
package com.jty.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jty.alipay.util.AlipayConfig;
import com.jty.alipay.util.AlipayNotify;
import com.jty.alipay.util.AlipaySubmit;
import com.jty.config.PropertiesUtils;
import com.jty.controller.base.BaseController;
import com.jty.model.Announce;
import com.jty.model.Balance;
import com.jty.model.Constant;
import com.jty.model.FundAccount;
import com.jty.model.Order;
import com.jty.model.OrderDetail;
import com.jty.model.ProductType;
import com.jty.model.User;
import com.jty.model.UserAgent;
import com.jty.sendMessage.PostMessageUTF;
import com.jty.service.AnnounceService;
import com.jty.service.MenuService;
import com.jty.service.OrderService;
import com.jty.service.UserService;
import com.jty.util.Grid;
import com.jty.utils.ResourceUtil;
import com.jty.utils.exception.BusinessException;


@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{
	@Resource(name=OrderService.SERVICE_NAME)
	private OrderService orderService;
	
	/**
	 * @author wufugui
	 * @version 2016-8-16
	 * @Des 查找购买的订单（分权限：管理可见所有、客户可见本人）
	 */
	@RequestMapping(value = "findOrder", method = RequestMethod.GET)
	@ResponseBody
	public void findOrder(@RequestParam(value="RealName",required=false) String RealName,
			@RequestParam(value="OrderState",required=false) Integer OrderState,
			Integer page,Integer rows){
		try{
			Map parmer = new HashMap();
			Map userMap =this.getCurrentUser();
			Long userId=Long.valueOf(userMap.get("id").toString());
			Integer userType=Integer.valueOf(userMap.get("userType").toString());
			if(userType==1){//系统管理员
				if(RealName!=null&&!("").equals(RealName)){
					parmer.put("RealName", RealName);
				}else{//UserId为空时
					
				}
			}else{
				parmer.put("UserId", userId);
			}
			
			if(OrderState!=null&&OrderState!=0){//所有--传参0
				parmer.put("OrderState", OrderState);
			}
			Grid grid =orderService.findOrderPage(parmer,page,rows);
 			System.out.println("================================");
			this.printJson(ResourceUtil.getSuccessInfo("获取成功", grid));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "获取失败"));
		}
	}
	@RequestMapping(value = "getProductByOrder", method = RequestMethod.GET)
	@ResponseBody
	public void getProductByOrder(@RequestParam("orderCode") String orderCode){
		try{
			Map productIds=orderService.findProductIds(orderCode);
			List<Map> list =new ArrayList<Map>();
			if(productIds!=null){
				String ids=productIds.get("product").toString();
				list=orderService.findproductDetail(ids);
			}
 			System.out.println("================================");
			this.printJson(ResourceUtil.getSuccessRows("获取成功", list));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "获取失败"));
		}
	}
	/**
	 * @author wufugui
	 * @version 2016-8-16
	 * @Des 购买提交订单
	 */
	@RequestMapping(value = "buyOrder", method = RequestMethod.POST)
	@ResponseBody
	public void buyOrder(@ModelAttribute Order order){
		try{
			Double 	calcMoney=	orderService.checkCalcMoney(order.getProduct());
			/*-----------提交前需要进行金额的计算检验---------------------------------------*/
//			if(order.getTotalMoney().equals(calcMoney)){		
				Map userMap =this.getCurrentUser();
				Long userId=Long.valueOf(userMap.get("id").toString());
				String orderCode="CZ"+System.currentTimeMillis();
				Order newOrder = new Order();
				newOrder.setCarMoney(order.getCarMoney());
				newOrder.setOrderCode(orderCode);
				newOrder.setOrderState(1);//待支付
				newOrder.setProduct(order.getProduct());
				newOrder.setTotalMoney(order.getTotalMoney());
				newOrder.setUserId(userId);//提交人
//				newOrder.setRealName(userMap.get("RealName").toString());
//				newOrder.setUserName(userMap.get("UserName").toString());
				
				newOrder.setCreateTime(new Date());
				Long orderId=orderService.save(newOrder);
				newOrder.setId(orderId.intValue());
				/*==================================================*/
				String[] productid=order.getProduct().split(",");
				for(int i=0;i<productid.length;i++){
					OrderDetail od=new OrderDetail();
					od.setCreateTime(new Date());
					od.setOrderCode(orderCode);
					od.setOrderState(1);
					od.setProductid(productid[i]);
	//				od.setProductMoney(0);
					od.setUserId(userId);
					orderService.save(od);
				}
				this.printJson(ResourceUtil.getSuccessInfo("提交成功", newOrder));
//			}else{
//				this.printJson(ResourceUtil.getFailureInfo(0, "因险种项目金额发现变动，请刷新重试"));
//			}
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "提交失败"));
		}
	}
	
    //提交订单
    @RequestMapping(value = "alipayapi", method = RequestMethod.POST)
    public void alipayapi(HttpServletRequest request, HttpServletResponse response, int orderId) throws UnsupportedEncodingException {
        try {
        	Order order = new Order();
        	order.setId(orderId);
        	order = (Order) orderService.findOne(order);
        	order.setTransferType(1);
        	orderService.update(order);
            String url = AlipayConfig.WebsiteDomain();

            //支付类型
            String payment_type = "1";
            //必填，不能修改
            //服务器异步通知页面路径url+"/payTransfer/transferUpdate2"
            String notify_url = url + "/order/transferUpdate2";
            //需http://格式的完整路径，不能加?id=123这类自定义参数

            //页面跳转同步通知页面路径url+"/index.html#/returnUrl/"+(payTransfer.getType()==0?"topUp":"success")
            String return_url = url + "/order/transferUpdate";
            //需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

            //商户订单号
            String out_trade_no = order.getOrderCode();
            //商户网站订单系统中唯一订单号，必填

            //订单名称payTransfer.getTransferName()
            String subject = "aa";
            subject = new String(subject.getBytes("ISO-8859-1"), "GBK");
            //必填

            //付款金额
            DecimalFormat df = new DecimalFormat("######0.00");
            String total_fee = df.format(order.getTotalMoney()).toString();
            //必填

            //订单描述

            String body = "aa";
            body = new String(body.getBytes("ISO-8859-1"), "GBK");
            //商品展示地址
            String show_url = PropertiesUtils.findPropertiesKey("WebsiteDomain", "config.properties");
            ;//new String((payTransfer.getTransferUrl() == null ? "" : payTransfer.getTransferUrl()).getBytes("ISO-8859-1"), "UTF-8");
            //需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html

            //防钓鱼时间戳
            String anti_phishing_key = new AlipaySubmit().query_timestamp();
            //若要使用请调用类文件submit中的query_timestamp函数

            //客户端的IP地址
            String exter_invoke_ip = "";
            //非局域网的外网IP地址，如：221.0.0.1
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", "create_direct_pay_by_user");
            sParaTemp.put("partner", AlipayConfig.partner());
            sParaTemp.put("_input_charset", AlipayConfig.input_charset);
            sParaTemp.put("payment_type", payment_type);
            sParaTemp.put("notify_url", notify_url);
            sParaTemp.put("return_url", return_url);
            sParaTemp.put("seller_email", AlipayConfig.seller_email());
            sParaTemp.put("out_trade_no", out_trade_no);
            sParaTemp.put("subject", subject);
            sParaTemp.put("total_fee", total_fee);
            sParaTemp.put("body", body);
            sParaTemp.put("show_url", show_url);
            sParaTemp.put("anti_phishing_key", anti_phishing_key);
//            sParaTemp.put("it_b_pay", "10m");
            sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

            if (order.getOrderState() == 1) {
                //建立请求
                String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
                response.setHeader("Content-Type", "text/html;charset=UTF-8");
                response.getWriter().write(sHtmlText);
                System.out.println("用户"+order.getUserId()+"支付宝提交订单成功"+order.getOrderCode());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    //交易更新
    @RequestMapping(value = "transferUpdate", method = RequestMethod.GET)
    @ResponseBody
    public void transferUpdate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //获取支付宝POST过来反馈信息
        try {
        	System.out.println("支付宝页面同步回调开始");
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            String getKey = "";
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            System.out.println("支付宝页面同步回调参数"+params);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号

            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号

            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");


            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            //计算得出通知验证结果
            boolean verify_result = AlipayNotify.verify(params);
            if (verify_result) {//验证成功
            	System.out.println("支付宝页面同步回调验证链接成功"+verify_result);
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                	System.out.println("支付宝页面同步回调【付款成功】"+trade_status);
                    //System.out.println("-----------------return验证通过----------------");
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                	Order order = new Order();
                	order.setOrderCode(out_trade_no);
                	order = (Order) orderService.findOne(order);
                    //如果未支付
                    if (order.getOrderState() !=2) {
                        //支付宝支付：1
                    	order.setTransferType(1);
                    	order.setOrderState(2);
                    	order.setTransferCode(trade_no);
                    	order.setLastUpdateTime(new Date());
                        orderService.update(order);
                        
                        //支付成功后返现
                        returnAmount(order);
                    }
                    String urlParm = "../index.html#/returnUrl/success?out_trade_no=" + out_trade_no + "&qian=" + order.getTotalMoney()+"&transferType=1";
                    response.setHeader("Content-Type", "text/html;charset=UTF-8");
                    response.getWriter().write("<script>\n" +
                            "    window.location.href = \"" + urlParm + "\";\n" +
                            "</script>");
                } else {
                	System.out.println("支付宝页面同步回调【付款失败】"+trade_status);
                	Order order = new Order();
                	order.setOrderCode(out_trade_no);
                	order = (Order) orderService.findOne(order);
                    if (order.getOrderState() !=2) {
                        //支付宝支付：1
                    	order.setTransferType(1);
                    	order.setTransferCode(trade_no);
                    	order.setLastUpdateTime(new Date());
                    	order.setOrderState(3);
                        //personOrder.setDeleteFlag(1);
                        orderService.update(order);
                    }

                }
            } else {
            	System.out.println("支付宝页面同步回调验证链接失败【可能不是支付宝端发起的回调或其他原因】"+verify_result);
            }


            //该页面可做页面美工编辑


        } catch (IOException e) {
            e.printStackTrace();
        }

        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——


    }


    //交易更新
    @RequestMapping(value = "transferUpdate2", method = RequestMethod.POST)
    public void transferUpdate2(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//    public void transferUpdate(HttpServletRequest request,Map<String,String> params,String out_trade_no,String trade_no,  String trade_status) {
        //获取支付宝POST过来反馈信息
        try {
        	System.out.println("支付宝页面异步回调开始");
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }
            System.out.println("支付宝页面异步回调参数"+requestParams);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");

            //支付宝交易号

            String trade_no = request.getParameter("trade_no");

            //交易状态
            String trade_status = request.getParameter("trade_status");

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码


            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            System.out.println("支付宝页面异步回调处理后的参数:" + params);
            //计算得出通知验证结果
            boolean verify_result = AlipayNotify.verify(params);
            if (verify_result) {
            	System.out.println("支付宝页面异步回调验证链接成功"+verify_result);
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                	System.out.println("支付宝页面异步回调【付款成功】"+trade_status);
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                	Order order = new Order();
                	order.setOrderCode(out_trade_no);
                	order = (Order) orderService.findOne(order);
                    //如果未支付
                    System.out.println("getOrderState:" + order.getOrderState());

                    if (order.getOrderState() !=2) {
                        //支付宝支付：1
                    	order.setTransferType(1);
                    	order.setOrderState(2);
                    	order.setTransferCode(trade_no);
                    	order.setLastUpdateTime(new Date());
                        orderService.update(order);
                        
                        //支付成功后返现
                        returnAmount(order);
                    }
                    System.out.println("-----------------success---------------------");
                    response.setHeader("Content-Type", "text/html;charset=UTF-8");
                    response.getWriter().write("success");
                } else {
                	System.out.println("支付宝页面异步回调【付款失败】"+trade_status);
                	Order order = new Order();
                	order.setOrderCode(out_trade_no);
                	order = (Order) orderService.findOne(order);
                    if (order.getOrderState() !=2) {
                        //支付宝支付：1
                    	order.setTransferType(1);
                    	order.setTransferCode(trade_no);
                    	order.setLastUpdateTime(new Date());
                    	order.setOrderState(3);
                        orderService.update(order);
                        
                    }
                    response.setHeader("Content-Type", "text/html;charset=UTF-8");
                    response.getWriter().write("fail");
                }
            } else {
            	System.out.println("支付宝页面异步回调验证链接失败【可能不是支付宝端发起的回调或其他原因】"+verify_result);
                response.setHeader("Content-Type", "text/html;charset=UTF-8");
                response.getWriter().write("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----------------e---------------------");
        }

        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

    }
    
    /**
     * 支付成功后返现
     * @return
     * author：wangjintao
     * version ：V1.00
     * Create date：2016-8-17 上午11:11:25
     */
    public void returnAmount(Order order) {
    	//获取返现比率
    	Constant constant =new Constant();
    	constant.setId(1);
    	constant = (Constant) orderService.findOne(constant);
    	
    	//获取一级返现代理
		UserAgent yijiuserAgent = new UserAgent();
		yijiuserAgent.setAgentLevel(1);
		yijiuserAgent.setUserId(order.getUserId());
		yijiuserAgent=(UserAgent) orderService.findOne(yijiuserAgent);
		if(yijiuserAgent!=null){
			//一级返现
			FundAccount yijifundAccount = new FundAccount();
			yijifundAccount.setAgentLevel(1);
			yijifundAccount.setCreateTime(new Date());
			yijifundAccount.setFromUserId(order.getUserId());
			yijifundAccount.setIsCheck(1);
			yijifundAccount.setOrderId(Long.valueOf(order.getId()));
			yijifundAccount.setReturnAmount(order.getTotalMoney()*constant.getOneParam());
			yijifundAccount.setReturnRatio(constant.getOneParam());
			yijifundAccount.setType(1);
			yijifundAccount.setUserId(yijiuserAgent.getTjUserId());
			orderService.save(yijifundAccount);
			//计入总账
			Balance yijibalance =new Balance();
			yijibalance.setUserId(yijiuserAgent.getTjUserId());
			yijibalance=(Balance) orderService.findOne(yijibalance);
			yijibalance.setBalanceAmount(yijibalance.getBalanceAmount()+yijifundAccount.getReturnAmount());
			yijibalance.setLastUpdateTime(new Date());
			orderService.update(yijibalance);
		}


		
    	//获取二级返现代理
		UserAgent erjiuserAgent = new UserAgent();
		erjiuserAgent.setAgentLevel(2);
		erjiuserAgent.setUserId(order.getUserId());
		erjiuserAgent=(UserAgent) orderService.findOne(erjiuserAgent);
		if(erjiuserAgent!=null){
			//二级返现
			FundAccount erjifundAccount = new FundAccount();
			erjifundAccount.setAgentLevel(2);
			erjifundAccount.setCreateTime(new Date());
			erjifundAccount.setFromUserId(order.getUserId());
			erjifundAccount.setIsCheck(1);
			erjifundAccount.setOrderId(Long.valueOf(order.getId()));
			erjifundAccount.setReturnAmount(order.getTotalMoney()*constant.getTwoParam());
			erjifundAccount.setReturnRatio(constant.getTwoParam());
			erjifundAccount.setType(1);
			erjifundAccount.setUserId(erjiuserAgent.getTjUserId());
			orderService.save(erjifundAccount);
			
			//计入总账
			Balance erjibalance =new Balance();
			erjibalance.setUserId(erjiuserAgent.getTjUserId());
			erjibalance=(Balance) orderService.findOne(erjibalance);
			erjibalance.setBalanceAmount(erjibalance.getBalanceAmount()+erjifundAccount.getReturnAmount());
			erjibalance.setLastUpdateTime(new Date());
			orderService.update(erjibalance);
		}

		//短信通知
		try {
			PostMessageUTF.SendMessage(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

