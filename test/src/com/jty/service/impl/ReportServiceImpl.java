package com.jty.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jty.mybatis.page.PageParameter;
import com.jty.service.MenuService;
import com.jty.service.ReportService;
import com.jty.util.Grid;
@Service(value=ReportService.SERVICE_NAME)
public class ReportServiceImpl extends CommonServiceImpl implements ReportService{

	private static String className="report";

	@Override
	public List<Map> findAccountUser() {
		List<Map> list=this.baseDao.selectList(className+".findAccountUser");
		return list;
	}
	
	@Override
	public List<Map> returnReport() {
		List<Map> list=this.baseDao.selectList(className+".returnReport");
		return list;
	}

	@Override
	public Grid getIncome(Map parmer,int page, int rows) {
		Grid incomeList = this.findPage(className+".getIncomePage", parmer, new PageParameter(page, rows));
		return incomeList;
	}
	

	@Override
	public List<Map> getIncomeList(Map parmer) {
		return this.baseDao.selectList(className+".getIncomeList",parmer);
	}

	@Override
	public Grid getGoout(Map parmer,int page, int rows) {
		Grid gooutList = this.findPage(className+".getGooutPage", parmer, new PageParameter(page, rows));
		return gooutList;
	}

	
	@Override
	public List<Map> getGooutList(Map parmer) {
		return this.baseDao.selectList(className+".getGooutList",parmer);
	}

	@Override
	public List<Map> findUserList(Map userMap) {
		Integer userType=Integer.valueOf(userMap.get("userType").toString());
		Integer id=Integer.valueOf(userMap.get("id").toString());
		Map parmer = new HashMap();
		if(userType==0){ //会员查出自己的与自己推荐的用户
			parmer.put("id", id);
		}
		List<Map>  userList = this.baseDao.selectList(className+".findUserList",parmer);
//		List<Map>  userList = this.baseDao.selectList(className+".findUserList2",parmer);
		return userList;
	}

	@Override
	public List<Map> findZjorderList(Integer userId) {
		Map parmer = new HashMap();
		parmer.put("userId", userId);
		List<Map> list=this.baseDao.selectList(className+".findZjorderList",parmer);
		return list;
	}

	@Override
	public List<Map> findTjorderList(String tjOrderCode) {
		Map parmer = new HashMap();
		parmer.put("tjOrderCode", tjOrderCode);
		List<Map> list=this.baseDao.selectList(className+".findTjorderList",parmer);
		return list;
	}

	@Override
	public List<Map> findOrderList(Map parmer) {
//		return this.baseDao.selectList(className+".findOrderList");
		return this.baseDao.selectList(className+".findOrderList2",parmer);
	}

	@Override
	public List<Map> findAllTree() {
		List<Map> allOrder=this.baseDao.selectList(className+".findAllOrder");
		return createTree(allOrder);
	}
	
	@SuppressWarnings("unchecked")
	private List<Map> createTree(List<Map> allOrder) {
		List<Map> lmp = new ArrayList<Map>();
		
		if(allOrder!=null){
			for(int i=0;i<allOrder.size();i++){
				Map map = allOrder.get(i);
//				if(map.get("parentId") != null && !map.get("parentId").toString().equals("0")) {
				if(map.get("tjOrderCode").equals("")){//顶级时从上往下遍历
					Map map1 = createBranch(allOrder, map);
					lmp.add(map1);
				}
			}
		}
		return lmp;
	}
	@SuppressWarnings("unchecked")
	private Map createBranch(List<Map> allOrder, Map currentMap) {
		List<Map> childList = new ArrayList<Map>();
		for(Map map : allOrder) {
			if(map.get("tjOrderCode").equals(currentMap.get("orderCode"))) {
				createBranch(allOrder, map);
				childList.add(map);
			}
		}
		if(!childList.isEmpty()) {
			currentMap.put("children", childList);
			currentMap.put("state", "closed");
		} else {
			currentMap.put("state", "open");
		}
		return currentMap;
	}
	
	
}
