package com.jty.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Random;

import redis.clients.jedis.Jedis;

import com.jty.commons.redis.JedisManager;

public class SendMessage {
    public static void sendValidCode(String mobile) throws Exception{
    	Jedis jedis = JedisManager.getJedis();
    	Random rand = new Random();
    	String validCode = StringUtils.leftPad(String.valueOf(rand.nextInt(999999)), 6, "0");
    	jedis.setex("random", 60, String.valueOf(validCode));
		JedisManager.closeJedis(jedis);
    	String content = "【好家长】验证码：%s，感谢您注册知了自主学习平台，请尽快完成验证";
        try {
			//content = new String(content.getBytes("ISO-8859-1"), "utf-8");
			content = String.format(content, validCode);
			SendMessage(content, mobile);
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
    
    public static void SendMessage(String content, String phone) throws Exception {
        content = java.net.URLEncoder.encode(content, "UTF-8");
        String url = "http://101.200.29.88:8082/SendMT/SendMessage";
        String params = "CorpID=jintaiyang&Pwd=123456&Mobile=%s&Content=%s";
        params = String.format(params, phone, content);
        try {
            sendPost(url, params);
        } catch (Exception ex) {
            throw ex;
        }

    }
    public static void sendPost(String url, String param) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Send_Message_Finish:" + result);

    }
}
