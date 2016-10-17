package com.jty.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jty.mybatis.page.PageParameter;
import com.jty.service.AnnounceService;
import com.jty.service.MenuService;
import com.jty.util.Grid;
@Service(value=AnnounceService.SERVICE_NAME)
public class AnnounceServiceImpl extends CommonServiceImpl implements
		AnnounceService {
	private static String className="announce";

	@Override
	public List<Map> getAnnounce() {
		
		return this.baseDao.selectList(className+".getAnnounce");
	}

	@Override
	public Grid findAnnouncePage(Integer page, Integer rows) {
//		Grid collectList = this.findPage("Collect.selectByUser_idPage", parmer,new PageParameter(page, rows));
		
		Grid collectList = this.findPage(className+".findAnnouncePage", null, new PageParameter(page, rows));
		return collectList;
	}
	
	

}
