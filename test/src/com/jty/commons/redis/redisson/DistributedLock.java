package com.jty.commons.redis.redisson;

import java.util.concurrent.TimeUnit;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RLock;

public class DistributedLock {

	private Redisson mRedisson;

	private RLock mLock;

    public DistributedLock(String address, String name) {
		Config config = new Config();
		config.useSingleServer().setAddress(address);
		mRedisson = Redisson.create(config);

		mLock = mRedisson.getLock(name);
	}

    public DistributedLock(Redisson redisson, String name) {
        mRedisson = redisson;

        mLock = mRedisson.getLock(name);
    }

    public void lock() {
        mLock.lock();
    }

    public void lock(long leaseTime, TimeUnit unit) {
        mLock.lock(leaseTime, unit);
    }

    public void unlock() {
        mLock.unlock();
    }

    public void forceUnlock() {
        mLock.forceUnlock();
    }

    public void delete() {
        mLock.delete();
    }

    public void shutdown() {
    	mRedisson.shutdown();
    }

}