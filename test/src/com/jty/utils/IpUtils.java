package com.jty.utils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {
	/**
	 * 得到客户端ip地址
	 * @param request
	 * @return
	 */
	public static String getIRealIPAddr(HttpServletRequest request) {   
		 String ip = request.getHeader("x-forwarded-for"); 
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip))    {   
		    ip = request.getHeader("Proxy-Client-IP");
		 }
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)   || "null".equalsIgnoreCase(ip)) {  
		  ip = request.getHeader("WL-Proxy-Client-IP");
		 }
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)    || "null".equalsIgnoreCase(ip)) {
		  ip = request.getRemoteAddr(); 
		 }
		 return ip;
	}
}
