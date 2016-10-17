package com.jty.commons.redis.redisson;

import java.util.HashMap;
import java.util.Map;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RBlockingQueue;

import com.jty.config.RedisConfig;

public class DistributedQueue<T> {
	static Map<String,Redisson> map = new HashMap<String, Redisson>();
	public static Redisson getRedisson(String address){
		if(!map.containsKey(address)){
			Config config = new Config();
//			config.useSingleServer().setAddress(address).setDatabase(Integer.parseInt(RedisConfig.get("redissionDb")));			
			config.useSingleServer().setAddress(address);
			map.put(address, Redisson.create(config));
		}
		return map.get(address);
	}

	protected Redisson mRedisson;

	protected RBlockingQueue<T> mQueue;

    public DistributedQueue(String address, String name) {
		Config config = new Config();
		config.useSingleServer().setAddress(address);
		mRedisson = Redisson.create(config);

		mQueue = mRedisson.getBlockingQueue(name);
	}

    public DistributedQueue(Redisson redisson, String name) {
        mRedisson = redisson;

        mQueue = mRedisson.getBlockingQueue(name);
    }

    public boolean isEmpty() {
    	return mQueue.isEmpty();
    }

    public T peek() {
    	return mQueue.peek();
    }

    public boolean offer(T message) {
    	return mQueue.offer(message);
    }

    public T poll() {
    	return mQueue.poll();
    }
    
    public boolean remove(T message){
    	return mQueue.remove(message);
    }

    public void put(T message) throws InterruptedException {
    	mQueue.put(message);
    }

    public T take() throws InterruptedException {
    	return mQueue.take();
    }

    public void clear() {
        mQueue.clear();
    }

    public void shutdown() {
    	mRedisson.shutdown();
    }

}