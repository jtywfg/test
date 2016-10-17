package com.jty.commons.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.jty.config.RedisConfig;


public class JedisManager {

	private static final Logger log = Logger.getLogger(JedisManager.class);
	private static final String ip = RedisConfig.get("ip");
	private static final Integer port = Integer.parseInt(RedisConfig.get("port"));
	private static final Integer maxidle = Integer.parseInt(RedisConfig.get("maxidle"));
	private static final Integer maxActive = Integer.parseInt(RedisConfig.get("maxactive"));
	private static final Long maxwait = Long.parseLong(RedisConfig.get("maxwait"));
    
	/**
	 * 私有构造器.
	 */
	private JedisManager() {
	    
	}
	
	private static Map<String,JedisPool> maps  = new HashMap<String,JedisPool>();
	
    private static JedisPool getPool(){
    	return JedisManager.getPool(ip,port);
    }
    /**
     * 获取连接池.
     * @return 连接池实例
     */
    private static JedisPool getPool(String ip,int port) {
    	String key = ip+":" +port;
    	JedisPool pool = null;
        if(!maps.containsKey(key)) {
            JedisPoolConfig config = new JedisPoolConfig();
//        	genericobj
            config.setMaxIdle(maxidle);
            config.setMaxWait(maxwait);
            config.setMaxActive(maxActive);
           // config.setMaxWaitMillis(Integer.parseInt(RedisConfig.get("maxwait")));
            config.setTestOnBorrow(false);
            config.setTestOnReturn(true);
            try{  
                /**
                 *如果你遇到 java.net.SocketTimeoutException: Read timed out exception的异常信息
                 *请尝试在构造JedisPool的时候设置自己的超时值. JedisPool默认的超时时间是2秒(单位毫秒)
                 */
                pool =  new  JedisPool(config, ip, port, Integer.parseInt(RedisConfig.get("timeout")));
                maps.put(key, pool)	;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }else{
        	pool = maps.get(key);
        }
        return pool;
    }

    /**
     *类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     *没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class RedisUtilHolder{
        private static JedisManager instance = new JedisManager();
    }

	public static JedisManager getInstance() {
		return RedisUtilHolder.instance;
	}
	
	/**
	 * explain 获取Redis实例.获取指定地址的redis实例
	 * @param ip
	 * @param port
	 * @return 
	 * @author wenluliao
	 * <br/>Date 2014-10-29  Time 下午3:53:58
	 */
	public static Jedis getJedis(String ip,int port) {
		Jedis jedis  = null;
		int count =0;
		do{
    		try{ 
    			jedis = getPool(ip,port).getResource();
    			//log.info("get redis master1!");
    		} catch (Exception e) {
    			log.error("get redis master1 failed!", e);
    			e.printStackTrace();
    			 // 销毁对象  
    			getPool(ip,port).returnBrokenResource(jedis);  
    		}
    		count++;
		}while(jedis==null&&count<Integer.parseInt(RedisConfig.get("retryNum")));
		return jedis;
	}
	
	public static Jedis getJedis(String ip,int port,int database) {
		Jedis jedis  = null;
		int count =0;
		do{
    		try{ 
    			jedis = getPool(ip,port).getResource();
    			jedis.select(database);
    			//log.info("get redis master1!");
    		} catch (Exception e) {
    			log.error("get redis master1 failed!", e);
    			e.printStackTrace();
    			 // 销毁对象  
    			getPool(ip,port).returnBrokenResource(jedis);  
    		}
    		count++;
		}while(jedis==null&&count<Integer.parseInt(RedisConfig.get("retryNum")));
		return jedis;
	}
	
	/**
	 * explain 获取Redis实例.
	 * @return 
	 * @author wenluliao
	 * <br/>Date 2014-10-29  Time 下午3:53:36
	 */
	public static Jedis getJedis() {
		Jedis jedis  = null;
		int count =0;
		do{
    		try{ 
    			jedis = getPool(ip,port).getResource();
    			//log.info("get redis master1!");
    		} catch (Exception e) {
    			log.error("get redis master1 failed!", e);
    			e.printStackTrace();
    			 // 销毁对象  
    			getPool(ip,port).returnBrokenResource(jedis);  
    		}
    		count++;
		}while(jedis==null&&count<Integer.parseInt(RedisConfig.get("retryNum")));
		return jedis;
	}
	
	/**
	 * explain 获取费用的redis库
	 * @return 
	 * @author wenluliao
	 * <br/>Date 2014-10-30  Time 上午8:49:30
	 */
	public static Jedis getPFeeJedis() {
		Jedis jedis  = null;
		int count =0;
		do{
    		try{ 
    			jedis = getPool(ip,port).getResource();
    			jedis.select(Integer.parseInt(RedisConfig.get("schoolConfigdb")));
    			//log.info("get redis master1!");
    		} catch (Exception e) {
    			log.error("get redis master1 failed!", e);
    			e.printStackTrace();
    			 // 销毁对象  
    			getPool(ip,port).returnBrokenResource(jedis);  
    		}
    		count++;
		}while(jedis==null&&count<Integer.parseInt(RedisConfig.get("retryNum")));
		return jedis;
	}
	
	/**
	 * explain 获取监控信息
	 * @return 
	 * @author wangwenhui
	 * <br/>Date 2014-11-08  Time 上午11:49:30
	 */
	public static Jedis getItleJedis() {
		Jedis jedis  = null;
		int count =0;
		do{
    		try{ 
    			jedis = getPool(ip,port).getResource();
    			jedis.select(Integer.parseInt(RedisConfig.get("itledb")));
    			//log.info("get redis master1!");
    		} catch (Exception e) {
    			log.error("get redis master1 failed!", e);
    			e.printStackTrace();
    			 // 销毁对象  
    			getPool(ip,port).returnBrokenResource(jedis);  
    		}
    		count++;
		}while(jedis==null&&count<Integer.parseInt(RedisConfig.get("retryNum")));
		return jedis;
	}
	
	/**
	 * 释放redis实例到连接池.
     * @param jedis redis实例
     */
	public static void closeJedis(Jedis jedis) {
		if(jedis != null) {
		    getPool(ip,port).returnResource(jedis);
		}
	}
	
}
