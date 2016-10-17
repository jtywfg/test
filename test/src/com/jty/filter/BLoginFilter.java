
package com.jty.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.jty.utils.ResourceUtil;
import com.jty.utils.ResultUtil;
import com.jty.utils.exception.BusinessException;


public class BLoginFilter implements Filter {
	private static Logger logger = Logger.getLogger(BLoginFilter.class);
	/**不需要过滤的url**/
	private static Set<String> notFilterUrls = null;
	static{
		notFilterUrls = new HashSet<String>();
		notFilterUrls.add("/user/login");  //发送验证码
		notFilterUrls.add("/order/transferUpdate2");
		notFilterUrls.add("/order/transferUpdate");
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String contextPath = req.getContextPath();
		String url = req.getServletPath();
		//logger.info("url:"+url);
		boolean flag = true;
		Map userMap =  (Map)req.getSession().getAttribute("user");
		
		if(notFilterUrls.contains(url)||url.contains(".")){//  
			flag = false;
		}
		
		if(flag && userMap == null){
//			String temp = url.substring(url.lastIndexOf("."), url.length()).toLowerCase();
//			if(temp.contains(".html") || temp.contains(".htm") || temp.contains(".jsp")){
//				res.sendRedirect("login.html");
//				//request.getRequestDispatcher("login.html").forward(req,res);
//			}else{
				ResourceUtil.write(res, ResultUtil.getFailureStr(new BusinessException("登录失效。",-1,false)));
//			}
        	return ;
		}
		
		
//		if(!notFilterUrls.contains(url)|| url.contains(".css")  || url.contains(".js")){
//			logger.info("url:"+url);
//			if(0!=loginStatus){
//				String temp = url.substring(url.lastIndexOf("."), url.length()).toLowerCase();
//				if(temp.contains(".html") || temp.contains(".htm") || temp.contains(".jsp")){
//					res.sendRedirect("login.html");
//					//request.getRequestDispatcher("login.html").forward(req,res);
//				}else{
//					ResourceUtil.write(res, ResultUtil.getFailureStr(new BusinessException("登录失效。",-1,false)));
//				}
//	        	return ;
//			}
//		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
