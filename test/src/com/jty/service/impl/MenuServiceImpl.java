package com.jty.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jty.service.MenuService;
@Service(value=MenuService.SERVICE_NAME)
public class MenuServiceImpl extends CommonServiceImpl implements MenuService{

	private static String className="menu";

	@Override
	public List<Map> getTopMenu() {
		List<Map> list=this.baseDao.selectList(className+".getTopMenu");
		return list;
	}

	@Override
	public List<Map> getMenuByParent(String PARENTID, Integer userType) {
		Map mp=new HashMap();
		mp.put("PARENTID", PARENTID);
		mp.put("userType", userType);
		List<Map> list=this.baseDao.selectList(className+".getMenuByParent",mp);
		return list;
	}
	
	
}
