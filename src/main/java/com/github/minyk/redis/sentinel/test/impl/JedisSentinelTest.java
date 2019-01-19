package com.github.minyk.redis.sentinel.test.impl;

import com.github.minyk.redis.sentinel.test.SentinelTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class JedisSentinelTest extends SentinelTest {

    JedisPoolConfig conf;
    Jedis jedis = null;
    JedisSentinelPool pool;


    public JedisSentinelTest(String hosts, int sleepMillis, String master, String pwd) {
        super(hosts, sleepMillis, master, pwd);
        conf = new JedisPoolConfig();
        conf.setMaxWaitMillis(1000);
        conf.setTestOnBorrow(true);
        conf.setMaxTotal(100);
        conf.setMaxIdle(10);

        LOGGER.info("starting JEDIS sentinel pool with sentinel hosts: {} master: {} ", hosts, master);
        //System.out.println("starting pool with sentinel hosts: " + hosts + "master: " + master);
        StringTokenizer st = new StringTokenizer(hosts, ",");
        Set<String> sentinels = new HashSet<>();

        while(st.hasMoreTokens())
            sentinels.add(st.nextToken());

        pool = new JedisSentinelPool(master, sentinels, conf, pwd);
    }

    public void runTest() {

        int i=0;
        int fails = 0;
        long downTime = 0;

        while (true) {
            try {
                Thread.currentThread().sleep(sleepMillis);
                jedis = pool.getResource();
                jedis.set("foo", "bar");
                String value = jedis.get("foo");
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
            finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public String getValue(String k) {
        return jedis.get(k);
    }

    public String setValue(String k, String v) {
        return jedis.set(k, v);
    }
}
