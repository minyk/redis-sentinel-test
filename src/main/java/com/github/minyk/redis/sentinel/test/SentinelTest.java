package com.github.minyk.redis.sentinel.test;

import com.github.minyk.redis.sentinel.test.impl.JedisSentinelTest;
import com.github.minyk.redis.sentinel.test.impl.RedissonSentinelTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SentinelTest {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SentinelTest.class);

    public final static String LIB_JEDIS = "jedis";
    public final static String LIB_REDISSON = "redisson";

    protected String hosts;
    protected int sleepMillis;
    protected String master;
    protected String pwd;

    protected SentinelTest(String hosts, int sleepMillis, String master, String pwd) {
        this.hosts = hosts;
        this.sleepMillis = sleepMillis;
        this.master = master;
        this.pwd = pwd;
    }

    public static SentinelTest init(String lib, String hosts, int sleepMillis, String master, String pwd) {
        if(lib.equals(LIB_JEDIS)) {
            return new JedisSentinelTest(hosts, sleepMillis, master, pwd);
        } else if (lib.equals(LIB_REDISSON)) {
            return new RedissonSentinelTest(hosts, sleepMillis, master, pwd);
        }
        LOGGER.error("wrong library name.");
        return null;
    }

    public abstract void runTest();

    public abstract String setValue(String key, String value);

    public abstract String getValue(String key);
}
