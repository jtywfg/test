package com.jty.dispatcher;

import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author sjl
 */
public class EncodingDispatcherServlet extends DispatcherServlet {
	private String encoding ;
	@Override
	public void init(ServletConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");  
	    super.init(config); 
	}
	
	@Override
	protected void doService(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		req.setCharacterEncoding(encoding);
		super.doService(req, resp);
	}
	
}
