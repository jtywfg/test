package com.jty.service;

import java.util.List;
import java.util.Map;

import com.jty.util.Grid;


public interface OrderService extends CommonService {
	public final String SERVICE_NAME = "com.jty.service.OrderService";

	Grid findOrderPage(Map parmer, Integer page, Integer rows);

	Double checkCalcMoney(String product);

	Map findProductIds(String orderCode);

	List<Map> findproductDetail(String ids);

	Integer checkOrderCount(Integer id);

	

}
