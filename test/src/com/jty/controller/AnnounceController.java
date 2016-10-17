/**
 * VersonPController
 * author：wangjintao
 * version ：V1.00
 * Create date：2016-4-15 下午03:49:23
*/
package com.jty.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jty.controller.base.BaseController;
import com.jty.model.Announce;
import com.jty.model.User;
import com.jty.service.AnnounceService;
import com.jty.service.MenuService;
import com.jty.service.UserService;
import com.jty.util.Grid;
import com.jty.utils.ResourceUtil;
import com.jty.utils.exception.BusinessException;


@Controller
@RequestMapping("/announce")
public class AnnounceController extends BaseController{
	@Resource(name=AnnounceService.SERVICE_NAME)
	private AnnounceService announceService;
	
	/**
	 * @author wufugui
	 * @version 2016-8-14
	 * @Des 管理公告--分页展示
	 */
	@RequestMapping(value = "findAnnounce", method = RequestMethod.GET)
	@ResponseBody
	public void findAnnounce(Integer page,Integer rows){
		try{
//			List<Map> list =announceService.findAnnounce(page,rows);
//			Long count =announceService.findAnnounce(page,rows);
			Grid grid =announceService.findAnnouncePage(page,rows);
 			System.out.println("================================");
			this.printJson(ResourceUtil.getSuccessInfo("获取公告成功", grid));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "获取公告失败"));
		}
	}
	@RequestMapping(value = "addAnnounce", method = RequestMethod.POST)
	@ResponseBody
	public void addAnnounce(@ModelAttribute Announce announce){
		try{
			Announce a = new Announce();
			a.setContent(announce.getContent());
			a.setTitle(announce.getTitle());
			a.setDeleteFlag(0);
			a.setCreateTime(new Date());
			announceService.save(a);
			this.printJson(ResourceUtil.getSuccessInfo("录入成功", a));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "录入失败"));
		}
	}
	@RequestMapping(value = "updateAnnounce", method = RequestMethod.POST)
	@ResponseBody
	public void updateAnnounce(@ModelAttribute Announce announce){
		try{
			Announce a = new Announce();
			a.setId(announce.getId());
			a = (Announce) announceService.findOne(announce);
			a.setTitle(announce.getTitle());
			a.setContent(announce.getContent());
			announceService.update(a);
			this.printJson(ResourceUtil.getSuccessInfo("更新成功", a));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "更新失败"));
		}
	}
	@RequestMapping(value = "editAnnounce", method = RequestMethod.POST)
	@ResponseBody
	public void editAnnounce(@ModelAttribute Announce announce){
		try{
			Announce a = new Announce();
			if(announce.getId()!=null){
				a.setId(announce.getId());
				a = (Announce) announceService.findOne(a);
				a.setTitle(announce.getTitle());
				a.setContent(announce.getContent());
				announceService.update(a);
			}else{
				a.setTitle(announce.getTitle());
				a.setContent(announce.getContent());
				a.setDeleteFlag(0);
				a.setCreateTime(new Date());
				announceService.save(a);
			}
			this.printJson(ResourceUtil.getSuccessInfo("提交成功", a));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "提交失败"));
		}
	}
	
	@RequestMapping(value = "deleteAnnounce", method = RequestMethod.POST)
	@ResponseBody
	public void deleteAnnounce(Integer id){
		try{
			Announce a = new Announce();
			a.setId(id);
			a = (Announce) announceService.findOne(a);
			a.setDeleteFlag(1);
			announceService.update(a);
			this.printJson(ResourceUtil.getSuccessInfo("删除成功", a));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "删除失败"));
		}
	}
	/**
	 * @author wufugui
	 * @version 2016-8-14
	 * @Des 首页公告
	 */
	@RequestMapping(value = "getAnnounce", method = RequestMethod.GET)
	@ResponseBody
	public void getAnnounce(){
		try{
			List<Map> list =announceService.getAnnounce();
 			System.out.println("================================");
			this.printJson(ResourceUtil.getSuccessInfo("获取公告成功", list));
		}catch(Exception e){
			e.printStackTrace();
			this.printJson(ResourceUtil.getFailureInfo(0, "获取公告失败"));
		}
	}
}

