package com.jty.service;

import java.util.List;
import java.util.Map;

import com.jty.util.Grid;

public interface FundAccountService extends CommonService {
	public final String SERVICE_NAME = "com.jty.service.FundAccountService";

	/**
	 * @author wufugui
	 * @version 2016-8-20
	 * @Des 个人总返金
	 */
	Double findReturnMoney(Long userId);

	/**
	 * @author wufugui
	 * @version 2016-8-20
	 * @Des 申请的提现（待审核的+审核通过的）
	 */
	Double findDrawMoney(Long userId);

	/**
	 * @param userId
	 * @param type
	 * @return
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-22 上午11:34:06
	*/
	Grid fundaccountList(Long userId, Integer type,Integer page,Integer rows,String realName);

	Grid findDrawList(Long userId, Integer page, Integer rows);

}
