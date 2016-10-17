package com.jty.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jty.controller.base.BaseController;
import com.jty.model.User;
import com.jty.service.ReportService;
import com.jty.service.UserService;
import com.jty.util.Grid;
import com.jty.utils.ResourceUtil;



@Controller
@RequestMapping("/report")
public class ReportController extends BaseController{
	@Resource(name=ReportService.SERVICE_NAME)
	private ReportService reportService;
	@Resource(name=UserService.SERVICE_NAME)
    private UserService userService;
	
	/**
	 * @author wufugui
	 * @version 2016-9-29
	 * @Des 推荐详情--先展示所有的用户
	 */
	@RequestMapping(value = "findUser", method = RequestMethod.GET)
	@ResponseBody
	public void findUser(){
		try{
			Map userMap =this.getCurrentUser();
			List<Map> userList = reportService.findUserList(userMap);
			
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", userList));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	
	/**
	 * @author wufugui
	 * @version 2016-9-29
	 * @Des 单击用户--查出用户自己的购买订单，以及订单推荐的情况
	 */
	@RequestMapping(value = "findUserOrder", method = RequestMethod.GET)
	@ResponseBody
	public void findUserOrder(Integer id){
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
	 * @author wufugui
	 * @version 2016-9-29
	 * @Des 将上面两个方法合拼成一棵3层级树
	 */
	@RequestMapping(value = "findUserList", method = RequestMethod.GET)
	@ResponseBody
	public void findUserList(){
		try{
			int z=1;
			List<Map> treeList=new ArrayList<Map>();
			Map treeMap=null;
			
			Map userMap =this.getCurrentUser();
			List<Map> userList = reportService.findUserList(userMap);
			for(int i=0;i<userList.size();i++){
				treeMap=new HashMap();
				treeMap.put("id", ++z);
//				treeMap.put("id", userList.get(i).get("id"));
				treeMap.put("text", userList.get(i).get("realName")+"@"+userList.get(i).get("userName"));
//				treeMap.put("carNum", userList.get(i).get("userName"));//需要显示用
//				treeList.add(treeMap);
				
				Integer userId=Integer.valueOf(userList.get(i).get("id").toString());
				List<Map> zjorderList = reportService.findZjorderList(userId);
				List<Map> zjList=new ArrayList<Map>();
				Map zjMap=null;
				for(int j=0;j<zjorderList.size();j++){
					zjMap=new HashMap();
					zjMap.put("id", ++z);
//					zjMap.put("id", zjorderList.get(j).get("id"));
					zjMap.put("text", zjorderList.get(j).get("orderCode"));//订单编号
					zjMap.put("carNum", zjorderList.get(j).get("carNum"));//车牌号
					zjMap.put("buyMoney", zjorderList.get(j).get("totalMoney"));//订单金额
					zjMap.put("createTime", zjorderList.get(j).get("createTime"));//购买日期
					zjMap.put("startTime", zjorderList.get(j).get("startTime"));
					zjMap.put("endTime", zjorderList.get(j).get("endTime"));
//					zjList.add(zjMap);
					
					
					String tjOrderCode=zjorderList.get(j).get("orderCode").toString();
					List<Map> tjList=new ArrayList<Map>();
						
						List<Map> tjorderList = reportService.findTjorderList(tjOrderCode);
						Map tjMap=null;
						for(int k=0;k<tjorderList.size();k++){
							tjMap=new HashMap();
							tjMap.put("id", ++z);
//							tjMap.put("id", tjorderList.get(k).get("id"));
							tjMap.put("text", tjorderList.get(k).get("orderCode"));//订单编号
							tjMap.put("carNum", tjorderList.get(k).get("carNum")+"@"+tjorderList.get(k).get("realName"));//车牌号
							tjMap.put("buyMoney", tjorderList.get(k).get("totalMoney"));//订单金额
							tjMap.put("createTime", tjorderList.get(k).get("createTime"));//购买日期
							tjMap.put("startTime", tjorderList.get(k).get("startTime"));
							tjMap.put("endTime", tjorderList.get(k).get("endTime"));
							tjList.add(tjMap);
						}
					zjMap.put("children", tjList);
					zjList.add(zjMap);
				}
				treeMap.put("children", zjList);
				treeList.add(treeMap);
			}
			
//			JSONArray.toJSON(treeList);
			/*JSONObject.toJSONString(treeList);
			JSONObject.toJSONStringWithDateFormat(treeList, "yyyy-MM-dd HH:mm:ss",  SerializerFeature.BrowserCompatible);*/
//			this.printJson(ResourceUtil.getTreeInfo(treeList));
			this.printJson(JSONObject.toJSONStringWithDateFormat(treeList, "yyyy-MM-dd HH:mm:ss",  SerializerFeature.BrowserCompatible));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	@RequestMapping(value = "findUserList2", method = RequestMethod.GET)
	@ResponseBody
	public void findUserList2(@RequestParam(value="userName",required=false) String userName,
			@RequestParam(value="realName",required=false) String realName){
		try{
			int z=1;
			List<Map> treeList=new ArrayList<Map>();
			Map parmer = new HashMap();
			if(userName!=null&&!"".equals(userName)){
				parmer.put("userName", userName);
			}
			if(realName!=null&&!"".equals(realName)){
				realName=new String(realName.getBytes("iso-8859-1"), "utf-8");
				parmer.put("realName", realName);
			}
				List<Map> orderList = reportService.findOrderList(parmer);
				Map zjMap=null;
				for(int j=0;j<orderList.size();j++){
					zjMap=new HashMap();
					zjMap.put("id", ++z);
//					zjMap.put("id", orderList.get(j).get("id"));
					zjMap.put("text", orderList.get(j).get("realName")+"@"+orderList.get(j).get("userName"));//订单编号
					zjMap.put("carNum", orderList.get(j).get("carNum"));//车牌号
					zjMap.put("buyMoney", orderList.get(j).get("totalMoney"));//订单金额
					zjMap.put("createTime", orderList.get(j).get("createTime"));//购买日期
//					zjMap.put("startTime", orderList.get(j).get("startTime"));
//					zjMap.put("endTime", orderList.get(j).get("endTime"));
//					zjList.add(zjMap);
					
					
					String tjOrderCode=orderList.get(j).get("orderCode").toString();
					List<Map> tjList=new ArrayList<Map>();
					
					List<Map> tjorderList = reportService.findTjorderList(tjOrderCode);
					Map tjMap=null;
					for(int k=0;k<tjorderList.size();k++){
						tjMap=new HashMap();
						tjMap.put("id", ++z);
//						tjMap.put("id", tjorderList.get(k).get("id"));
						tjMap.put("text", tjorderList.get(k).get("realName")+"@"+tjorderList.get(k).get("userName"));//订单编号
						tjMap.put("carNum", tjorderList.get(k).get("carNum"));//车牌号
						tjMap.put("buyMoney", tjorderList.get(k).get("totalMoney"));//订单金额
						tjMap.put("createTime", tjorderList.get(k).get("createTime"));//购买日期
//						tjMap.put("startTime", tjorderList.get(k).get("startTime"));
//						tjMap.put("endTime", tjorderList.get(k).get("endTime"));
						tjList.add(tjMap);
					}
					zjMap.put("children", tjList);
					treeList.add(zjMap);
				}
			
//			JSONArray.toJSON(treeList);
			/*JSONObject.toJSONString(treeList);
			JSONObject.toJSONStringWithDateFormat(treeList, "yyyy-MM-dd HH:mm:ss",  SerializerFeature.BrowserCompatible);*/
//			this.printJson(ResourceUtil.getTreeInfo(treeList));
			this.printJson(JSONObject.toJSONStringWithDateFormat(treeList, "yyyy-MM-dd HH:mm:ss",  SerializerFeature.BrowserCompatible));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	@RequestMapping(value = "findUserList3", method = RequestMethod.GET)
	@ResponseBody
	public void findUserList3(){
			List<Map> treeList=new ArrayList<Map>();
			treeList=reportService.findAllTree();
			this.printJson(JSONObject.toJSONStringWithDateFormat(treeList, "yyyy-MM-dd HH:mm:ss",  SerializerFeature.BrowserCompatible));
	}
	
	 	@RequestMapping(value = "/incomeExport", method = RequestMethod.GET)
	    @ResponseBody
	    public void incomeExport(HttpServletRequest request,HttpServletResponse response,
	    		@RequestParam(value="startTime",required=false) String startTime,
				@RequestParam(value="endTime",required=false) String endTime,
				@RequestParam(value="userName",required=false) String userName,
				@RequestParam(value="realName",required=false) String realName) throws IOException {
	 		
	 		Map parmer = new HashMap();
			if(startTime!=null&&!"".equals(startTime)){
				parmer.put("startTime", startTime);
			}
			if(endTime!=null&&!"".equals(endTime)){
				parmer.put("endTime", endTime);
			}
			if(userName!=null&&!"".equals(userName)){
				parmer.put("userName", userName);
			}
			if(realName!=null&&!"".equals(realName)){
				realName=new String(realName.getBytes("iso-8859-1"), "utf-8");
				parmer.put("realName", realName);
			}
	    	List<Map> incomeExportList =income(parmer);
			HSSFWorkbook workbook = new HSSFWorkbook();
			//写入excle
			this.writeIncome(workbook, incomeExportList);	
			/**定义下载报表后的后缀名*/
			String excelfileName="进帐表.xls";
			/**生成文件并下载*/
			response.reset();// 清空输出流
			response.setHeader("Content-Disposition","attachment;filename="+ new String(excelfileName.getBytes("gb2312"), "ISO8859-1"));
			response.setContentType("application/x-download");
			OutputStream ouputStream = response.getOutputStream();    
			// 把相应的Excel 工作簿存盘
			workbook.write(ouputStream);
			ouputStream.flush();
			// 操作结束，关闭文件
			ouputStream.close();
		}
	 	private void writeIncome(HSSFWorkbook workbook,List<Map> incomeExportList) {
			HSSFSheet sheet = workbook.createSheet("进帐");
			HSSFRow row0 = sheet.createRow(0);
			//设置列宽度
			sheet.setColumnWidth(0, 15 * 256);
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 15 * 256);
			sheet.setColumnWidth(3, 15 * 256);
			sheet.setColumnWidth(4, 15 * 256);
			sheet.setColumnWidth(5, 15 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 15 * 256);
			
			/* 基本的边框设置 */
			HSSFCellStyle style = questionAnalycellstyle(workbook);
			writeCellValue(row0,style,0,"购买用户名");
			writeCellValue(row0,style,1,"购买姓名");
			writeCellValue(row0,style,2,"购买单号");
			writeCellValue(row0,style,3,"推荐订单号");
			writeCellValue(row0,style,4,"购买金额");
			writeCellValue(row0,style,5,"创建日期");
			writeCellValue(row0,style,6,"生效日期");
			writeCellValue(row0,style,7,"截止日期");
			int a=1;
			for(Map m: incomeExportList){
				HSSFRow rowa = sheet.createRow(a);
				writeCellValue(rowa,style,0,m.get("userName")==null?"":m.get("userName").toString());
				writeCellValue(rowa,style,1,m.get("realName")==null?"":m.get("realName").toString());
				writeCellValue(rowa,style,2,m.get("orderCode")==null?"":m.get("orderCode").toString());
				writeCellValue(rowa,style,3,m.get("tjOrderCode")==null?"":m.get("tjOrderCode").toString());
				writeCellValue(rowa,style,4,m.get("totalMoney")==null?"":m.get("totalMoney").toString());
				writeCellValue(rowa,style,5,m.get("createTime")==null?"":m.get("createTime").toString());
				writeCellValue(rowa,style,6,m.get("startTime")==null?"":m.get("startTime").toString());
				writeCellValue(rowa,style,7,m.get("endTime")==null?"":m.get("endTime").toString());
				a++;
			}
		}
	 private List<Map> income(Map parmer){
			try{
				//返回的结果
				List<Map> incomeList = reportService.getIncomeList(parmer);
				return incomeList;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
	@RequestMapping(value = "income", method = RequestMethod.GET)
	@ResponseBody
	public void income(@RequestParam(value="startTime",required=false) String startTime,
			@RequestParam(value="endTime",required=false) String endTime,
			@RequestParam(value="userName",required=false) String userName,
			@RequestParam(value="realName",required=false) String realName,
			int page,int rows){
		try{
			//返回的结果
			Map parmer = new HashMap();
			if(startTime!=null&&!"".equals(startTime)){
				parmer.put("startTime", startTime);
			}
			if(endTime!=null&&!"".equals(endTime)){
				parmer.put("endTime", endTime);
			}
			if(userName!=null&&!"".equals(userName)){
				parmer.put("userName", userName);
			}
			if(realName!=null&&!"".equals(realName)){
				realName=new String(realName.getBytes("iso-8859-1"), "utf-8");
				parmer.put("realName", realName);
			}
			Grid grid = reportService.getIncome(parmer,page,rows);
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", grid));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	@RequestMapping(value = "/gooutExport", method = RequestMethod.GET)
    @ResponseBody
    public void gooutExport(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value="startTime",required=false) String startTime,
			@RequestParam(value="endTime",required=false) String endTime,
			@RequestParam(value="userName",required=false) String userName,
			@RequestParam(value="realName",required=false) String realName) throws IOException {
		Map parmer = new HashMap();
		if(startTime!=null&&!"".equals(startTime)){
			parmer.put("startTime", startTime);
		}
		if(endTime!=null&&!"".equals(endTime)){
			parmer.put("endTime", endTime);
		}
		if(userName!=null&&!"".equals(userName)){
			parmer.put("userName", userName);
		}
		if(realName!=null&&!"".equals(realName)){
			realName=new String(realName.getBytes("iso-8859-1"), "utf-8");
			parmer.put("realName", realName);
		}
    	List<Map> gooutExportList =goout(parmer);
		HSSFWorkbook workbook = new HSSFWorkbook();
		//写入excle
		this.writeGoout(workbook, gooutExportList);	
		/**定义下载报表后的后缀名*/
		String excelfileName="出帐表.xls";
		/**生成文件并下载*/
		response.reset();// 清空输出流
		response.setHeader("Content-Disposition","attachment;filename="+ new String(excelfileName.getBytes("gb2312"), "ISO8859-1"));
		response.setContentType("application/x-download");
		OutputStream ouputStream = response.getOutputStream();    
		// 把相应的Excel 工作簿存盘
		workbook.write(ouputStream);
		ouputStream.flush();
		// 操作结束，关闭文件
		ouputStream.close();
	}
 	private void writeGoout(HSSFWorkbook workbook,List<Map> gooutExportList) {
		HSSFSheet sheet = workbook.createSheet("出帐");
		HSSFRow row0 = sheet.createRow(0);
		//设置列宽度
		sheet.setColumnWidth(0, 15 * 256);
		sheet.setColumnWidth(1, 15 * 256);
		sheet.setColumnWidth(2, 15 * 256);
		
		/* 基本的边框设置 */
		HSSFCellStyle style = questionAnalycellstyle(workbook);
		writeCellValue(row0,style,0,"用户名");
		writeCellValue(row0,style,1,"姓名");
		writeCellValue(row0,style,2,"赠送积分");
		writeCellValue(row0,style,3,"赠送时间");
		writeCellValue(row0,style,4,"抵销积分");
		writeCellValue(row0,style,5,"抵销时间");
		int a=1;
		for(Map m: gooutExportList){
			HSSFRow rowa = sheet.createRow(a);
			writeCellValue(rowa,style,0,m.get("userName")==null?"":m.get("userName").toString());
			writeCellValue(rowa,style,1,m.get("realName")==null?"":m.get("realName").toString());
			writeCellValue(rowa,style,2,m.get("returnIntegralSum")==null?"":m.get("returnIntegralSum").toString());
			writeCellValue(rowa,style,3,m.get("createTime")==null?"":m.get("createTime").toString());
			writeCellValue(rowa,style,4,m.get("minusIntegral")==null?"":m.get("minusIntegral").toString());
			writeCellValue(rowa,style,5,m.get("minusTime")==null?"":m.get("minusTime").toString());
			a++;
		}
	}
	 private List<Map> goout(Map parmer){
			try{
				//返回的结果
				List<Map> gooutList = reportService.getGooutList(parmer);
				return gooutList;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
	@RequestMapping(value = "goout", method = RequestMethod.GET)
	@ResponseBody
	public void goout(@RequestParam(value="startTime",required=false) String startTime,
			@RequestParam(value="endTime",required=false) String endTime,
			@RequestParam(value="userName",required=false) String userName,
			@RequestParam(value="realName",required=false) String realName,
			int page,int rows){
		try{
			//返回的结果
			Map parmer = new HashMap();
			if(startTime!=null&&!"".equals(startTime)){
				parmer.put("startTime", startTime);
			}
			if(endTime!=null&&!"".equals(endTime)){
				parmer.put("endTime", endTime);
			}
			if(userName!=null&&!"".equals(userName)){
				parmer.put("userName", userName);
			}
			if(realName!=null&&!"".equals(realName)){
				realName=new String(realName.getBytes("iso-8859-1"), "utf-8");
				parmer.put("realName", realName);
			}
			Grid grid = reportService.getGoout(parmer,page,rows);
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", grid));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}
	
	/**
	 * 返现报表
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-18 下午07:29:50
	 */
	@RequestMapping(value = "returnReport", method = RequestMethod.GET)
	@ResponseBody
	public void returnReport(int page,int rows){
		try{
			List<Map> accountUserList =getAccountUserList();
			//分页 
			List<Map> list= new ArrayList<Map>();
			int first=(page-1)*rows; //计算从哪条记录开始
	        int last=page*rows;//计算到哪条结束
			for(int j = first ; j < last ; j++){
				if(j<accountUserList.size()&&j<accountUserList.size()){
					list.add(accountUserList.get(j));
				}
			}
			//返回的结果
			Grid grid = new Grid();
			grid.setCount(accountUserList.size());
			grid.setList(list);
			this.printJson(ResourceUtil.getSuccessInfo("查询成功", grid));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "查询失败"));
		}
	}

	public List<Map>  getAccountUserList() {
		//获取一级二级返金总额及数量
		List<Map> list =reportService.returnReport();
		Map monMap = new HashMap();
		Map countMap = new HashMap();
		for(Map m :list){
			String userId=m.get("userId").toString();
			String AgentLevel=m.get("agentLevel").toString();
			String toAmount=m.get("toAmount").toString();
			String count=m.get("count").toString();
			monMap.put(userId+"_"+AgentLevel, toAmount);
			countMap.put(userId+"_"+AgentLevel, count);
		}
		//获取所有返金用户信息
		List<Map> accountUserList =reportService.findAccountUser();
		for(Map mm :accountUserList){
			//一级
			Float yijiAmount =0f;
			if(monMap.get(mm.get("userId")+"_1")!=null){
				yijiAmount =Float.valueOf(monMap.get(mm.get("userId")+"_1").toString()); 
			}
			mm.put("yijiAmount", yijiAmount);
			
			String yijiCount ="0";
			if(countMap.get(mm.get("userId")+"_1")!=null){
				yijiCount =countMap.get(mm.get("userId")+"_1").toString(); 
			}
			mm.put("yijiCount", yijiCount);
			
			//二级
			Float erjiAmount =0f;
			if(monMap.get(mm.get("userId")+"_2")!=null){
				erjiAmount =Float.valueOf(monMap.get(mm.get("userId")+"_2").toString()); 
			}
			mm.put("erjiAmount", erjiAmount);
			
			String erjiCount ="0";
			if(countMap.get(mm.get("userId")+"_2")!=null){
				erjiCount =countMap.get(mm.get("userId")+"_2").toString(); 
			}
			mm.put("erjiCount", erjiCount);
			
			mm.put("totalAmount", yijiAmount+erjiAmount);
			
		}
		return accountUserList;
	}
	
	/**
	 * 导出报表
	 * @param request
	 * @param response
	 * @throws IOException
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-18 下午08:56:39
	 */
    @RequestMapping(value = "/exportAccount", method = RequestMethod.GET)
    @ResponseBody
    public void exportAccount(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	List<Map> accountUserList =getAccountUserList();
		HSSFWorkbook workbook = new HSSFWorkbook();
		//写入excle
		this.writeSysUser(workbook, accountUserList);	
		/**定义下载报表后的后缀名*/
		String excelfileName="返金详情.xls";
		/**生成文件并下载*/
		response.reset();// 清空输出流
		response.setHeader("Content-Disposition","attachment;filename="+ new String(excelfileName.getBytes("gb2312"), "ISO8859-1"));
		response.setContentType("application/x-download");
		OutputStream ouputStream = response.getOutputStream();    
		// 把相应的Excel 工作簿存盘
		workbook.write(ouputStream);
		ouputStream.flush();
		// 操作结束，关闭文件
		ouputStream.close();
	}
	public void writeSysUser(HSSFWorkbook workbook,List<Map> accountUserList) {
		HSSFSheet sheet = workbook.createSheet("返金详情");
		HSSFRow row0 = sheet.createRow(0);
		//设置列宽度
		sheet.setColumnWidth(0, 15 * 256);
		sheet.setColumnWidth(1, 15 * 256);
		sheet.setColumnWidth(2, 15 * 256);
		sheet.setColumnWidth(3, 15 * 256);
		sheet.setColumnWidth(4, 15 * 256);
		sheet.setColumnWidth(5, 15 * 256);
		
		/* 基本的边框设置 */
		HSSFCellStyle style = questionAnalycellstyle(workbook);
		writeCellValue(row0,style,0,"用户名");
		writeCellValue(row0,style,1,"一级返金人数");
		writeCellValue(row0,style,2,"一级返金金额");
		writeCellValue(row0,style,3,"二级返金人数");
		writeCellValue(row0,style,4,"二级返金金额");
		writeCellValue(row0,style,5,"总返金金额");
		int a=1;
		for(Map m: accountUserList){
			HSSFRow rowa = sheet.createRow(a);
			writeCellValue(rowa,style,0,m.get("realName").toString());
			writeCellValue(rowa,style,1,m.get("yijiCount").toString());
			writeCellValue(rowa,style,2,m.get("yijiAmount").toString());
			writeCellValue(rowa,style,3,m.get("erjiCount").toString());
			writeCellValue(rowa,style,4,m.get("erjiAmount").toString());
			writeCellValue(rowa,style,5,m.get("totalAmount").toString());
			a++;
		}
	}

	public HSSFCell writeCellValue(HSSFRow row,HSSFCellStyle style,int cellNum,String cellValue) {
		HSSFCell rowcell=row.createCell(cellNum);
		rowcell.setCellValue(cellValue);
		rowcell.setCellStyle(style);
		return rowcell;
	}
	public  HSSFCellStyle questionAnalycellstyle(HSSFWorkbook workbook){
		/* 基本的边框设置 */
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		/* 设置数据格式 */
//		HSSFDataFormat celldf = workbook.createDataFormat();
//		style.setDataFormat(celldf.getFormat("#,#0.00"));
		return style;
	}
	
}

