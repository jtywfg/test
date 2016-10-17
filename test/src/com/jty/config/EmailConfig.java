package com.jty.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * 邮件发送配置文件
 * EmailConfig
 * author：wangjintao
 * version ：V1.00
 * Create date：2015-8-4 下午04:44:25
 */
public class EmailConfig {
	private EmailConfig(){}
	private static final Logger log = Logger.getLogger(EmailConfig.class);
	private static final String fileName = "/email.properties";
	private static final Properties properties = new Properties();
	static {
		loadConfig(fileName);
	}
    private static void loadConfig(String fileName){
    	try {
			InputStream inputStream = EmailConfig.class.getResourceAsStream(fileName);
			properties.load(inputStream);
		} catch (IOException e) {
			log.error("加载配置文件失败！", e);
		} catch (Throwable e) {
			log.error("加载配置文件失败！", e);
		}
    }
	public static String get(String key) {
		return properties.getProperty(key);
	}
}
