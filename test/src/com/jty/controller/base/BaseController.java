package com.jty.controller.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jty.utils.ResultUtil;

public class BaseController {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	

	protected void setSession(String key,Object value){
		request.getSession().setAttribute(key, value);
	}
	

	protected Object getSession(String key){
		return request.getSession().getAttribute(key);
	}
	
	@ExceptionHandler
	public String exception(Exception e){

        request.setAttribute("exceptionMessage", e.getMessage());  
        e.printStackTrace();

        if(e instanceof NumberFormatException){
        	request.setAttribute("msg", "!");
        	logger.info(this.getClass().getName());
        }
        this.printJson(ResultUtil.getFailureInfo("操作失败"));

        return "error";
	}
	/**
	 * 返回json
	 * @param jsonObj
	 * author：wangjintao
	 * version ：V1.00
	 * Create date：2015-6-1 下午02:03:17
	 */
	public void printJson(String jsonObj) {
		PrintWriter writer;
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			writer = response.getWriter();// 将内容输出到response
			writer.write(jsonObj);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	public Map getCurrentUser(){
		return (Map)request.getSession().getAttribute("user");
	}
	
	/**
	 * 设置当前登录用户信息
	 * @return
	 */
	public void setCurrentUser(Map userMap){
		request.getSession().setAttribute("user",userMap);
	}
	
	/**
	 * 移除当前登录用户信息
	 * @return
	 */
	public void removeCurrentUser(){
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
	}

	public String getBasePath(){
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath;
	}

}
