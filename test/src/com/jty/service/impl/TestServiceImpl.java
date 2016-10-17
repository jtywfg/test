/**
 * 
 */
package com.jty.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jty.mybatis.page.PageParameter;
import com.jty.service.TestService;
import com.jty.util.Grid;
@Service("testService")
public class TestServiceImpl extends CommonServiceImpl implements TestService{
	private static String className="test";

	@Override
	public List<Map> testFind(String name) {
		return this.baseDao.selectList(className+".testFind",name);
	}
	
	@Override
	public Grid findPageList(Map pmap,PageParameter page){
		return this.findPage(className+".testFindPage", pmap, page);
	}
}
