package com.jty.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.util.FileUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jty.contant.Constants;
import com.jty.util.Grid;
import com.jty.utils.exception.BusinessException;

@SuppressWarnings( { "unchecked" })
public class ResourceUtil {
	public static String getErrorMsg(Object... params) {
		return null;
	}

	public static String saveFile(InputStream is, String fileName, String path) {
		if (null == is) {
			return null;
		}
		File tempFile = null;
		try {
			String uuId = UUID.randomUUID().toString();
			File pathFile = new File(path);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			tempFile = new File(path
					+ File.separator
					+ uuId
					+ fileName.substring(fileName.lastIndexOf("."), fileName
							.length()));
			OutputStream os = new FileOutputStream(tempFile);
			try {
				FileUtil.copyStream(is, os);
				return tempFile.getName();
				// return
				// com.jty.fastdfs.utils.FastdfsClientFactory.getFastdfsClient().upload(tempFile);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != os) {
					os.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != tempFile) {
				tempFile.deleteOnExit();
			}
		}
		return null;
	}

	public static String getErrorMsg(Map<String, Object> params) {
		String errorMsg = "";
		for (String key : params.keySet()) {
			Object val = params.get(key);
			if (null == val) {
				errorMsg += key + ",";
			} else if (val instanceof String) {
				String strVal = (String) val;
				if ("".equals(strVal)) {
					errorMsg += key + ",";
				}
			}
		}
		if (!"".equals(errorMsg)) {
			errorMsg = "参数[" + errorMsg.substring(0, errorMsg.length() - 1)
					+ "]为必传参数。";
		}
		return errorMsg;
	}

	public static String getSuccessStr(String msg, Map map) {
		Map result = new HashMap();
		result.put("status", 1);
		if (!StringUtils.isEmpty(msg)) {
			result.put("msg", msg);
		}
		if (null != map) {
			for (Object key : map.keySet()) {
				result.put(key, map.get(key));
			}
		}
		return JSON.toJSONString(result);
	}

	public static Map getSuccessMap(String msg, Map map) {
		Map result = new HashMap();
		result.put("status", 1);
		if (!StringUtils.isEmpty(msg)) {
			result.put("msg", msg);
		}
		if (null != map) {
			for (Object key : map.keySet()) {
				result.put(key, map.get(key));
			}
		}
		return result;
	}

	public static Map getFailureMap(String msg, Map map) {
		Map result = new HashMap();
		result.put("status", 0);
		if (!StringUtils.isEmpty(msg)) {
			result.put("msg", msg);
		}
		if (null != map) {
			for (Object key : map.keySet()) {
				result.put(key, map.get(key));
			}
		}
		return result;
	}

	public static Map getSuccessGrid(String msg, Grid map) {
		Map result = new HashMap();
		result.put("status", 1);
		if (!StringUtils.isEmpty(msg)) {
			result.put("msg", msg);
		}
		if (null != map) {
			result.put("object", map);
		}
		return result;
	}

	public static Map getSuccessObject(String msg, Object o) {
		Map result = new HashMap();
		result.put("status", 1);
		if (!StringUtils.isEmpty(msg)) {
			result.put("msg", msg);
		}
		if (null != o) {
			result.put("object", o);
		}
		return result;
	}

	public static Map getSuccessListMap(String msg, List<Map> map) {
		Map result = new HashMap();
		result.put("status", 1);
		if (!StringUtils.isEmpty(msg)) {
			result.put("msg", msg);
		}
		result.put("list", map);
		return result;
	}

	public static Map getSuccessList(String msg, List list) {
		Map result = new HashMap();
		result.put("status", 1);
		if (!StringUtils.isEmpty(msg)) {
			result.put("msg", msg);
		}
		result.put("list", list);
		return result;
	}

	public static Map getMessageMap(String msg) {
		Map result = new HashMap();
		result.put("status", 1);
		if (!StringUtils.isEmpty(msg)) {
			result.put("msg", msg);
		}
		return result;
	}

	public static String getFailureStr(Exception e) {
		Map result = new HashMap();
		if (e instanceof BusinessException) {
			BusinessException be = (BusinessException) e;
			result.put("message", be.getMessage());
			result.put("success", be.getStatus());
		} else {
			result.put("message", Constants.SYS_ERROR_INFO);
			result.put("success", 0);
		}
		return JSON.toJSONString(result);
	}

	public static String getLoginFailureStr(String msg) {
		Map result = new HashMap();
		result.put("msg", msg);
		result.put("status", "-1");
		return JSON.toJSONString(result);
	}

	public static Map getFailureMap(Exception e) {
		Map result = new HashMap();
		if (e instanceof BusinessException) {
			BusinessException be = (BusinessException) e;
			if (!StringUtils.isEmpty(be.getMessage())) {
				result.put("msg", be.getMessage());
			}
			result.put("status", be.getStatus());
		} else {
			result.put("msg", Constants.SYS_ERROR_INFO);
			result.put("status", 0);
		}
		return result;
	}

	public static void write(HttpServletResponse response, String msg) {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.write(msg);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getSuccessInfo(String message, Object otherObj) {
		Map map = new HashMap();
		map.put("success", Integer.valueOf(1));
		map.put("message", message);
		map.put("object", otherObj);
		return JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss", SerializerFeature.BrowserCompatible);
	}
	/**
	 * @author wufugui
	 * @version 2016-9-30
	 * @Des 推荐层级树打印
	 */
	public static String getTreeInfo(Object otherObj) {
		Map map = new HashMap();
		map.put("success", Integer.valueOf(1));
		map.put("data", otherObj);
		return JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss", SerializerFeature.BrowserCompatible);
	}
	public static String getSuccessRows(String message, List rows) {
		Map map = new HashMap();
		map.put("success", Integer.valueOf(1));
		map.put("message", message);
		map.put("rows", rows);
		return JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss", SerializerFeature.BrowserCompatible);
	}

	/**
	 * easyui grid 使用格式
	 * @author huangjw
	 * 创建时间:2016-8-15 下午09:30:20
	 * @param message
	 * @param grid
	 * @return
	 */
	public static String getSuccessInfo(String message, Grid grid) {
		Map map = new HashMap();
		map.put("success", Integer.valueOf(1));
		map.put("message", message);
		map.put("rows", grid.getList());
		map.put("total", grid.getCount());
		return JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss", SerializerFeature.BrowserCompatible);
	}

	public static String getFailureInfo(int flag, String message) {
		Map map = new HashMap();
		map.put("success", Integer.valueOf(flag));
		map.put("message", message);
		return JSON.toJSONString(map);
	}
}
