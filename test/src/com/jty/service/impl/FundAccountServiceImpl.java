package com.jty.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jty.mybatis.page.PageParameter;
import com.jty.service.FundAccountService;
import com.jty.util.Grid;
@Service(value=FundAccountService.SERVICE_NAME)
public class FundAccountServiceImpl extends CommonServiceImpl implements
		FundAccountService {
	private static String className="fundAccount";

	@Override
	public Double findReturnMoney(Long userId) {
		return this.baseDao.selectOne(className+".findReturnMoney", userId);
	}

	@Override
	public Double findDrawMoney(Long userId) {
		return this.baseDao.selectOne(className+".findDrawMoney", userId);
	}

	@Override
	public Grid fundaccountList(Long userId,Integer type,Integer page,Integer rows,String realName) {
		HashMap map = new HashMap();
		map.put("UserId", userId);
		map.put("realName", realName);
		map.put("type", type);
		Grid fundaccountList = this.findPage(className+".fundaccountListPage", map,new PageParameter(page, rows));
		return fundaccountList;
	}

	@Override
	public Grid findDrawList(Long userId, Integer page, Integer rows) {
		HashMap map = new HashMap();
		map.put("UserId", userId);
		Grid drawList = this.findPage(className+".findDrawListPage", map,new PageParameter(page, rows));
		return drawList;
	}
}
