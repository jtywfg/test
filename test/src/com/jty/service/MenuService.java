package com.jty.service;

import java.util.List;
import java.util.Map;

import com.jty.model.User;

public interface MenuService extends CommonService{
	public final String SERVICE_NAME = "com.jty.service.MenuService";

	List<Map> getTopMenu();

	List<Map> getMenuByParent(String PARENTID, Integer userType);

}
