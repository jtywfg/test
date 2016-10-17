package com.jty.service;

import java.util.List;
import java.util.Map;

import com.jty.util.Grid;

public interface AnnounceService extends CommonService {
	public final String SERVICE_NAME = "com.jty.service.AnnounceService";

	List<Map> getAnnounce();

	Grid findAnnouncePage(Integer page, Integer rows);
	

}
