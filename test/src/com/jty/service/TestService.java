/**
 * 
 */
package com.jty.service;

import java.util.List;
import java.util.Map;

import com.jty.mybatis.page.PageParameter;
import com.jty.util.Grid;

/**
 * @author sjl
 * ����ʱ��:2015-5-26 ����2:38:26
 */
public interface TestService {
	public List<Map> testFind(String name);
	
	public Grid findPageList(Map pmap,PageParameter page);
}
