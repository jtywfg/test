package com.jty.service;

import java.util.List;
import java.util.Map;

import com.jty.model.User;
import com.jty.util.Grid;

public interface ReportService extends CommonService{
	public final String SERVICE_NAME = "com.jty.service.ReportService";

	List<Map> returnReport();

	/**
	 * @return
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-18 下午08:20:01
	*/
	List<Map> findAccountUser();

	/**
	 * @author wufugui
	 * @version 2016-10-12
	 * @Des 进帐分页列表
	 */
	Grid getIncome(Map parmer, int page, int rows);

	/**
	 * @author wufugui
	 * @version 2016-10-12
	 * @Des 出帐分页列表
	 */
	Grid getGoout(Map parmer, int page, int rows);

	List<Map> findUserList(Map userMap);

	List<Map> findZjorderList(Integer userId);

	List<Map> findTjorderList(String tjOrderCode);

	/**
	 * @author wufugui
	 * @version 2016-10-9
	 * @param parmer 
	 * @Des 导出-进帐
	 */
	List<Map> getIncomeList(Map parmer);

	/**
	 * @author wufugui
	 * @version 2016-10-9
	 * @param parmer 
	 * @Des 导出-出帐
	 */
	List<Map> getGooutList(Map parmer);

	List<Map> findOrderList(Map parmer);

	List<Map> findAllTree();

}
