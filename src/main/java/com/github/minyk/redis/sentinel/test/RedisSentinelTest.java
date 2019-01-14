package com.github.minyk.redis.sentinel.test;

import org.apache.commons.cli.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class RedisSentinelTest {

    public static void main(String argv[]) {

        Options options = new Options();

        options.addRequiredOption("s", "sentinels", true, "Sentinel hosts. Comma seperated string.");
        options.addOption("t", "sleep-time", true, "Sleep time between requests in milliseconds.");
        options.addRequiredOption("m", "master-name", true, "Master name.");
        options.addOption("p", "password", true, "Password for master server");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, argv);
            String hosts = cmd.getOptionValue("sentinels");
            int sleepTime = Integer.parseInt(cmd.getOptionValue("sleep-time", "1000"));
            String master = cmd.getOptionValue("master-name");
            String password = cmd.getOptionValue("password");
            redisTest(hosts, sleepTime, master, password);
        } catch (ParseException pe) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("CLITester", options);
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void redisTest(String hosts, int sleepMillis, String master, String pwd) {

        JedisPoolConfig conf = new JedisPoolConfig();
        conf.setMaxWaitMillis(1000);
        conf.setTestOnBorrow(true);
        conf.setMaxTotal(100);
        conf.setMaxIdle(10);

        System.out.println("starting pool with sentinel hosts: " + hosts + "master: " + master);
        //JedisPool pool = new JedisPool(conf, host, 6379, 1000, null);
        StringTokenizer st = new StringTokenizer(hosts, ",");
        Set<String> sentinels = new HashSet<>();

        while(st.hasMoreTokens())
            sentinels.add(st.nextToken());

        JedisSentinelPool pool = new JedisSentinelPool(master, sentinels, conf, pwd);
        Jedis jedis = null;

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
                    System.out.println("\n" + new Date());
                }
                if (fails != 0) {
                    long duration = System.currentTimeMillis() - downTime;
                    System.out.println("\n" + new Date() + ", SERVER UP, was down for " + duration + "ms, failed commands: " + fails);
                    fails = 0;
                }
            }
            catch(Exception e) {
                if (fails == 0) {
                    downTime = System.currentTimeMillis();
                    System.out.println("\n" + new Date() + ", SERVER DOWN");
                }
                fails++;
                //System.out.println("\n" + new Date() + ", " + e.getMessage());
                System.out.print("x");
            }
            finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

}
