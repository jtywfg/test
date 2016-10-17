package com.jty.service;

import java.util.List;
import java.util.Map;

import com.jty.model.User;
import com.jty.util.Grid;

public interface UserService extends CommonService{
	public final String SERVICE_NAME = "com.jty.service.UserService";

	public Map login(String username,String pwd);

	/**
	 * @param user
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-14 下午03:52:51
	*/
	void register(User user);

	/**
	 * @param UserName
	 * @param page
	 * @param rows
	 * @return
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2016-8-15 上午09:01:19
	*/
	Grid findUserList(Integer page, Integer rows,Map parmer);

	public List<User> findNodeleteList();

}
