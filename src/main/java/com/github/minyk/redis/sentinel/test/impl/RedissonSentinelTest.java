package com.github.minyk.redis.sentinel.test.impl;

import com.github.minyk.redis.sentinel.test.SentinelTest;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;

import java.util.Date;

public class RedissonSentinelTest extends SentinelTest {
    public static final int REDISSON_MASTER_CONN_POOL_SIZE = 10;
    public static final int REDISSON_SLAVE_CONN_POOL_SIZE = 10;
    private Config config;
    private RedissonClient redissonClient;

    public RedissonSentinelTest(String hosts, int sleepMillis, String master, String pwd) {
        super(hosts, sleepMillis, master, pwd);
        config = new Config();
        SentinelServersConfig ssCfg = config.useSentinelServers();
        ssCfg
                .setMasterName(master)
                .addSentinelAddress(hosts.trim().split(","))
                .setDatabase(0)
                .setMasterConnectionPoolSize(REDISSON_MASTER_CONN_POOL_SIZE)
                .setSlaveConnectionPoolSize(REDISSON_SLAVE_CONN_POOL_SIZE)
                .setPassword(pwd)
                .setTimeout(60000)
                .setReadMode(ReadMode.MASTER_SLAVE)
                .setPingTimeout(1000)
                .setRetryAttempts(20)
                .setRetryInterval(1000);

        redissonClient = Redisson.create(config);
    }

    public void runTest() {

        int i=0;
        int fails = 0;
        long downTime = 0;

        LOGGER.info("starting REDISSON client with sentinel hosts: {} master: {} ", hosts, master);
        while (true) {
            try {
                Thread.currentThread().sleep(sleepMillis);
                redissonClient.getBucket("foo").set("bar");
                redissonClient.getBucket("foo").get();
                System.out.print(".");
                if (i++ % 100 == 0) {
                    LOGGER.info(new Date().toString());
                    //System.out.println("\n" + new Date());
                }
                if (fails != 0) {
                    long duration = System.currentTimeMillis() - downTime;
                    LOGGER.info("SERVER UP, was down for {} ms, failed commands: {}", duration, fails);
                    //System.out.println("\n" + new Date() + ", SERVER UP, was down for " + duration + "ms, failed commands: " + fails);
                    fails = 0;
                }
            }
            catch(Exception e) {
                if (fails == 0) {
                    downTime = System.currentTimeMillis();
                    LOGGER.info("SERVER DOWN");
                    //System.out.println("\n" + new Date() + ", SERVER DOWN");
                }
                fails++;
                //System.out.println("\n" + new Date() + ", " + e.getMessage());
                LOGGER.info("SERVER is still down.");
                //System.out.print("x");
            }
//            finally {
//                if (redissonClient != null) {
//                }
//            }
        }
    }

    public String getValue(String k) {
        return (String)redissonClient.getBucket(k).get();
    }

    public String setValue(String k, String v) {
        redissonClient.getBucket(k).set(v);
        return "";
    }
}
