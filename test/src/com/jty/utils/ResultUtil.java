package com.jty.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jty.contant.Constants;
import com.jty.util.Grid;
import com.jty.utils.exception.BusinessException;

public class ResultUtil {

	public static String getFailureStr(Exception e){
		return JSON.toJSONString(getFailureMap(e));
	}
	public static Map getFailureMap(Exception e){
		Map result = new HashMap();
		if(e instanceof BusinessException){
			BusinessException be = (BusinessException)e;
			if(!StringUtils.isEmpty(be.getMessage())){
				result.put(Constants.MESSAGE, be.getMessage());
			}
			result.put(Constants.SUCCESS, be.getStatus());
		}else{
			result.put(Constants.MESSAGE, Constants.SYS_ERROR_INFO);
			result.put(Constants.SUCCESS, 0);
		}
		return result;
	}
	public static String getSuccessStrByList(String message,List list){
		return JSON.toJSONString(getSuccessMapByList(message,list));
	}
	public static String getSuccessStrByGrid(String message,Grid grid){
		return JSON.toJSONString(getSuccessMapByGrid(message,grid));
	}
	public static String getSuccessStr(String message,Map params){
		return JSON.toJSONString(getSuccessMap(message,params));
	}
	public static Map getSuccessMap(String message,Map params){
		Map result = new HashMap();
		result.put(Constants.SUCCESS, 1);
		if(!StringUtils.isEmpty(message)){
			result.put(Constants.MESSAGE, message);
		}
		if(null!=params){
			for(Object key:params.keySet()){
				result.put(key, params.get(key));
			}
		}
		return result;
	}
	public static Map getSuccessMapByList(String message,List list){
		Map result = new HashMap();
		result.put(Constants.SUCCESS, 1);
		if(!StringUtils.isEmpty(message)){
			result.put(Constants.MESSAGE, message);
		}
		if(null!=list){
			result.put(Constants.DATA, list);
		}
		return result;
	}
	public static Map getSuccessMapByGrid(String message,Grid grid){
		Map result = new HashMap();
		result.put(Constants.SUCCESS, 1);
		if(!StringUtils.isEmpty(message)){
			result.put(Constants.MESSAGE, message);
		}
		if(null!=grid){
			result.put(Constants.TOTAL, grid.getCount());
			result.put(Constants.DATA, grid.getList());
		}
		return result;
	}
	/**
	 * 返回成功json
	 * @param message 消息
	 * @param otherObj 返回对象
	 * @author aijian
	 * @return str 成功json
	 * @Create Date: 2014-10-23
	 */
	public static String getSuccessInfo(String message,Object otherObj){
		Map map = new HashMap();
		map.put(Constants.SUCCESS, 1);
		map.put(Constants.MESSAGE, message);
		map.put("object", otherObj);
		return JSON.toJSONString(map);
	}
	/**
	  * 返回成功json
	  *
	  * @author xiongx
	  * @date 2015-6-8 下午4:21:50
	  * @version V1.0
	  *
	  * @Title: getSuccessInfo
	  * @Description: 返回成功json
	  * @param successFlag
	  * @param messageFlag
	  * @param dataFlag
	  * @param message
	  * @param object
	  * @return String    返回类型
	  * @throws
	  */
	public static String getSuccessInfo(String successFlag,String messageFlag,String dataFlag,String message,Object object){
		Map map = new HashMap();
		map.put(successFlag, 1);
		map.put(messageFlag, message);
		map.put(dataFlag, object);
		return JSON.toJSONString(map);
	}
	
	
	/**
	 * 返回失败json
	 * @param message 消息
	 * @param flag 非1
	 * @author aijian
	 * @return str 成功json
	 * @Create Date: 2014-10-23
	 */
	public static String getFailureInfo(int flag,String message){
		Map map = new HashMap();
		map.put(Constants.SUCCESS, flag);
		map.put(Constants.MESSAGE, message);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 返回失败json
	 * @param message 消息
	 * @param flag 非1
	 * @author aijian
	 * @return str 成功json
	 * @Create Date: 2014-10-23
	 */
	public static String getFailureInfo(String message){
		Map map = new HashMap();
		map.put(Constants.SUCCESS, 0);
		map.put(Constants.MESSAGE, message);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 返回失败json
	 * @param message 消息
	 * @param flag 非1
	 * @author xf
	 * @return str 成功json
	 * @Create Date: 2014-10-23
	 */
	public static String getFailureInfo(Exception e){
		Map result = new HashMap();
		if(e instanceof BusinessException){
			result.put(Constants.SUCCESS, -1);
			result.put(Constants.MESSAGE, e.getMessage());
		}else{
			result.put(Constants.SUCCESS, 0);
			result.put(Constants.MESSAGE, Constants.SYS_ERROR_INFO);
		}
		return JSON.toJSONString(result);
	}
}
