package com.jty.config;

import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 对属性文件操作的工具类
 * 获取，新增，修改
 * 注意：	以下方法读取属性文件会缓存问题,在修改属性文件时，不起作用，
 * 　InputStream in = PropertiesUtils.class.getResourceAsStream("/config.properties");
 * 　解决办法：
 * 　String savePath = PropertiesUtils.class.getResource("/config.properties").getPath();
 *
 * @author lanyuan
 *         2013-11-19
 * @version 1.0v
 * @Email: mmm333zzz520@163.com
 */
public class PropertiesUtils {

    private static Map<String, String> config = new HashMap<String, String>();

    /**
     * 获取属性文件的数据 根据key获取值
     *
     * @param key      key名称
     * @param fileName 文件名
     * @return
     */
    public static String findPropertiesKey(String key, String fileName) {
        String value = config.get(key + ":" + fileName);
        if (value != null)
            return value;
        else {
            try {
//                Properties prop = getProperties();
                Properties prop = new Properties();
                //            String redisPropertiesUrl=Thread.currentThread().getContextClassLoader().getResource("")+fileName;
//            prop.load(new FileInputStream(redisPropertiesUrl.substring(redisPropertiesUrl.indexOf("/")+1)));
                prop.load(PropertiesUtils.class.getResourceAsStream("/" + fileName));

                String c = prop.getProperty(key);
                config.put(key + ":" + fileName, c);
                return c;
            } catch (Exception e) {
                return "";
            }
        }
    }

    public static void main(String[] args) {
//        Properties prop = new Properties();
//        InputStream in = PropertiesUtils.class
//                .getResourceAsStream("/config.properties");
//        try {
//            prop.load(in);
//            Iterator<Entry<Object, Object>> itr = prop.entrySet().iterator();
//            while (itr.hasNext()) {
//                Entry<Object, Object> e = (Entry<Object, Object>) itr.next();
//                System.err.println((e.getKey().toString() + "" + e.getValue()
//                        .toString()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            String content = PropertiesUtils.findPropertiesKey("Subject", "config.properties");
            content = java.net.URLEncoder.encode(content, "UTF-8");
            content = PropertiesUtils.findPropertiesKey("FromAddress", "config.properties");
            content = java.net.URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回　Properties
     *
     * @param
     * @return
     */
    public static Properties getProperties() {
        Properties prop = new Properties();
        String savePath = PropertiesUtils.class.getResource("/config.properties").getPath();
        //以下方法读取属性文件会缓存问题
//		InputStream in = PropertiesUtils.class
//				.getResourceAsStream("/config.properties");
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(savePath));
            prop.load(in);
        } catch (Exception e) {
            return null;
        }
        return prop;
    }

    /**
     * 写入properties信息
     *
     * @param key   名称
     * @param value 值
     */
    public static void modifyProperties(String key, String value) {
        try {
            // 从输入流中读取属性列表（键和元素对）
            Properties prop = getProperties();
            prop.setProperty(key, value);
            String path = PropertiesUtils.class.getResource("/config.properties").getPath();
            FileOutputStream outputFile = new FileOutputStream(path);
            prop.store(outputFile, "modify");
            outputFile.close();
            outputFile.flush();
        } catch (Exception e) {
        }
    }
}

