package com.jty.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jty.mybatis.page.PageParameter;
import com.jty.service.AnnounceService;
import com.jty.service.MenuService;
import com.jty.service.OrderService;
import com.jty.util.Grid;
@Service(value=OrderService.SERVICE_NAME)
public class OrderServiceImpl extends CommonServiceImpl implements
		OrderService {
	private static String className="order";

	@Override
	public Grid findOrderPage(Map parmer, Integer page, Integer rows) {
		Grid orderList = this.findPage(className+".findOrderListPage", parmer,new PageParameter(page, rows));
		return orderList;
	}

	@Override
	public Double checkCalcMoney(String product) {
		HashMap map = new HashMap();
		map.put("ids", product);
		return this.baseDao.selectOne(className+".checkCalcMoney", map);
	}

	@Override
	public Map findProductIds(String orderCode) {
		return this.baseDao.selectOne(className+".findProductIds", orderCode);
	}

	@Override
	public List<Map> findproductDetail(String productIds) {
		HashMap map = new HashMap();
		map.put("productIds", productIds);
		return this.baseDao.selectList(className+".findproductDetail", map);
	}

	@Override
	public Integer checkOrderCount(Integer id) {
		return this.baseDao.selectOne(className+".checkOrderCount", id);
	}

	

}
