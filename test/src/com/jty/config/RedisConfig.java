package com.jty.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class RedisConfig {
	private RedisConfig(){}
	private static final Logger log = Logger.getLogger(RedisConfig.class);
	private static final String fileName = "/redis.properties";
	private static final Properties properties = new Properties();
	static {
		loadConfig(fileName);
	}
    private static void loadConfig(String fileName){
    	try {
			InputStream inputStream = RedisConfig.class.getResourceAsStream(fileName);
			properties.load(inputStream);
		} catch (IOException e) {
			log.error("加载配置文件失败！", e);
		} catch (Throwable e) {
			log.error("加载配置文件失败！", e);
		}
    }
	public static String get(String key) {
		return properties.getProperty(key).trim();
	}
	public static String getAddr() {
        return get("ip");
    }

    public static String getPort() {
        return get("port");
    }

    public static String getAddress() {
        return getAddr() + ":" + getPort();
    }

	public static void main(String[] args) {
	    String ip = RedisConfig.getAddress();
	    System.out.println(ip);
	}
}
