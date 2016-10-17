package com.jty.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jty.model.Balance;
import com.jty.model.User;
import com.jty.model.UserAgent;
import com.jty.mybatis.page.PageParameter;
import com.jty.service.UserService;
import com.jty.util.Grid;
import com.jty.utils.MD5;
import com.jty.utils.exception.BusinessException;
@Service(value=UserService.SERVICE_NAME)
public class UserServiceImpl extends CommonServiceImpl implements UserService {
	private static String className="user";

	/**
	 * 登录
	 * @param mobile 手机号
	 * @param pwd 密码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map login(String username, String pwd) {
		User user = new User();
		Map parmer= new HashMap();
		parmer.put("pwd", MD5.MD5Encode(pwd));
		parmer.put("LoginName", username);
		user =(User) this.baseDao.selectOne(className+".findUser",parmer);
		if(user==null){
			throw new BusinessException("用户名或密码错误。", 0, false);
		}
		String id = String.valueOf(user.getId());
		//返回的信息
		Map userInfo = new HashMap();
		userInfo.put("userName", user.getUserName());
		userInfo.put("realName", user.getRealName());
		userInfo.put("id", id);
		userInfo.put("userType", user.getUserType());
		return userInfo;
	}

	/**
	 * 注册
	 * @param mobile 手机号
	 * @param pwd 密码
	 * @param verifyCode 验证码
	 */
	@Override
	@Transactional
	public void register(User user) {
		Map parmer= new HashMap();
		parmer.put("LoginName", user.getUserName());
		User oldUser =(User) this.baseDao.selectOne(className+".findUser",parmer);
		if(oldUser==null){
			user.setPassword(MD5.MD5Encode(user.getPassword()));
			user.setUserType(0);//0：会员客户  1：管理员
			user.setCreateTime(new Date());
			user.setFreezed(0);
			user.setDeleteFlag(0);
			Long userId = this.save(user);
			
			
			//初始化用户余额
			Balance balance = new Balance();
			balance.setUserId(userId);
			balance.setBalanceAmount(0.0);
			balance.setBalanceIntegral(0F);
			balance.setLastUpdateTime(new Date());
			this.save(balance);
			
			//增加一级推荐人，直接推荐人
			UserAgent yijiuserAgent = new UserAgent();
			yijiuserAgent.setTjUserId(user.getTjUserId());
			yijiuserAgent.setAgentLevel(1);
			yijiuserAgent.setUserId(userId);
			yijiuserAgent.setCreateTime(new Date());
			this.save(yijiuserAgent);
			
			/**增加二级推荐人，直接推荐人的上级*/
			//先通过【一级推荐人】找到【二级推荐人】
			UserAgent setuserAgent = new UserAgent();
			setuserAgent.setUserId(user.getTjUserId());
			setuserAgent.setAgentLevel(1);
			UserAgent erjiUserId = new UserAgent();
			//获取二级推荐人id
			erjiUserId =(UserAgent) this.findOne(setuserAgent);
			//存在二级推荐人
			if(erjiUserId!=null&&erjiUserId.getTjUserId()!=null&&!erjiUserId.getTjUserId().equals("")){
				//添加二级推荐人记录
				UserAgent erjiuserAgent = new UserAgent();
				erjiuserAgent.setUserId(userId);
				erjiuserAgent.setAgentLevel(2);
				erjiuserAgent.setTjUserId(erjiUserId.getTjUserId());
				erjiuserAgent.setCreateTime(new Date());
				this.save(erjiuserAgent);
			}
			/**增加二级推荐人结束*/
		}else{
			throw new BusinessException("该账户已经存在。", 0, false);
		}

	}
	
	@Override
	public Grid findUserList(Integer page,Integer rows,Map parmer) {
		Grid userList = this.findPage(className+".findUserListPage", parmer,new PageParameter(page, rows));
		return userList;
	}

	@Override
	public List<User> findNodeleteList() {
		return this.baseDao.selectList(className+".findNodeleteList");
	}
	
}
