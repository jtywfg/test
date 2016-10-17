package com.jty.sendMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import com.jty.config.PropertiesUtils;
import com.jty.model.Order;

public class PostMessageUTF {

    public static void SendMessage(Order order) throws Exception {
    	String url=PropertiesUtils.findPropertiesKey("url", "sendMessage.properties");
    	String UserName=PropertiesUtils.findPropertiesKey("UserName", "sendMessage.properties");						//用户名
		String UserPass=PropertiesUtils.findPropertiesKey("UserPass", "sendMessage.properties");
		String mobile=PropertiesUtils.findPropertiesKey("mobile", "sendMessage.properties");//密码
	    String content="用户"+order.getUserId()+"车险已经购买成功。支付总金额为："+order.getTotalMoney()+"元。";									//发送内容					
		String parameter="CorpID="+UserName+"&Pwd="+UserPass+"&Content="+content+"&Mobile="+mobile;
		System.out.println(url+"?"+parameter);
		System.out.println(PostMessageUTF.httpPostSend(url,parameter,"UTF-8"));
    }
	public static void main(String[] a) throws Exception
	{
//		SendMessage("18070138067");
//		try{
//			String url="http://101.200.29.88:8082/SendMT/SendMessage";
//		
//			String UserName="bxwwh";						//用户名
//			String UserPass="123456";								//密码
//		    String content="尊敬的用户wt你好，你车险已经购买成功。";									//发送内容
//			String mobile="18070138067";										//手机号
//			String parameter="CorpID="+UserName+"&Pwd="+UserPass+"&Content="+content+"&Mobile="+mobile;
//			System.out.println(url+"?"+parameter);
//			System.out.println(PostMessageUTF.httpPostSend(url,parameter,"UTF-8"));
//		}
//		catch (Exception e) 
//		{
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
	}
	
	public static String httpPostSend(String sendUrl,String parameter,String encoded) throws
	SocketTimeoutException, Exception
	{
		String urlPath = sendUrl;
		StringBuffer sbf = new StringBuffer();
		BufferedWriter writer = null;
		BufferedReader reader = null;
		HttpURLConnection uc = null;
		try {
			URL url = new URL(urlPath);
			uc = (HttpURLConnection) url.openConnection();
			uc.setConnectTimeout(30000);
			uc.setReadTimeout(30000);
			uc.setRequestMethod("POST");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			
			writer = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(),encoded)); // 向服务器传送数据
			writer.write(parameter);
			writer.flush();
			writer.close();
			reader = new BufferedReader(new InputStreamReader(uc.getInputStream(),encoded)); // 读取服务器响应信息
			String line;
	
			while ((line = reader.readLine()) != null) {
				sbf.append(line);
			}
			reader.close();
			uc.disconnect();	
		} 
	   
	    catch (SocketTimeoutException e){throw new SocketTimeoutException(); }
		catch (IOException e) {e.printStackTrace();throw new IOException();} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("无法连接服务器");
		} finally {
			closeIO(writer, reader);
		}
		return sbf.toString().trim();
	}
	
	private static void closeIO(BufferedWriter writer, BufferedReader reader) {
		if (writer != null) {
			try {
				writer.close();
				writer = null;
			} catch (Exception e) {

			}
		}
		if (reader != null) {
			try {
				reader.close();
				reader = null;
			} catch (Exception e) {

			}
		}

	}
}
