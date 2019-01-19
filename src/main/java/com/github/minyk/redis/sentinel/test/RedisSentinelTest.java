package com.github.minyk.redis.sentinel.test;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RedisSentinelTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSentinelTest.class);
    private static final long serialVersionUID = 1L;


    public static void main(String argv[]) {

        Options options = new Options();

        options.addRequiredOption("s", "sentinels", true, "Sentinel hosts. Comma seperated string.");
        options.addOption("t", "sleep-time", true, "Sleep time between requests in milliseconds.");
        options.addRequiredOption("m", "master-name", true, "Master name.");
        options.addOption("p", "password", true, "Password for master server");
        options.addRequiredOption("l", "library", true, "Redis client library. 'jedis' or 'redisson' only.");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, argv);
            String hosts = cmd.getOptionValue("sentinels");
            int sleepTime = Integer.parseInt(cmd.getOptionValue("sleep-time", "1000"));
            String master = cmd.getOptionValue("master-name");
            String password = cmd.getOptionValue("password");
            String lib = cmd.getOptionValue("library");

            SentinelTest tester = SentinelTest.init(lib, hosts, sleepTime, master, password);
            tester.runTest();

        } catch (ParseException pe) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("CLITester", options);
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
