
# How to build

```bash
$ mvn clean compile jib:build
```

# How to run

* parameters:

```bash
$ java -cp ./target/redis-sentinel-test-0.1.jar com.github.minyk.redis.sentinel.test.RedisSentinelTest --help
usage: CLITester
 -l,--library <arg>       Redis client library. 'jedis' or 'redisson'
                          only.
 -m,--master-name <arg>   Master name.
 -p,--password <arg>      Password for master server
 -s,--sentinels <arg>     Sentinel hosts. Comma seperated string.
 -t,--sleep-time <arg>    Sleep time between requests in milliseconds.
```

### Test with Redisson library

```bash
$ java -Xms1g -Xmx1g \
     -cp ./target/redis-sentinel-test-0.1.jar \
     com.github.minyk.redis.sentinel.test.RedisSentinelTest \
     -s redis://127.0.0.1:26379 \
     -t 1000 \
     -m mymaster \
     -p pass \
     -l redisson
```

### Test with Jedis library

```bash
% java -Xms1g -Xmx1g \
     -cp ./target/redis-sentinel-test-0.1.jar \
     com.github.minyk.redis.sentinel.test.RedisSentinelTest \
     -s 127.0.0.1:26379 \
     -t 1000 \
     -m mymaster \
     -p pass \
     -l jedis
```

# Test sample

### redisson

```
0    [main] INFO  org.redisson.Version  - Redisson 3.5.5
576  [main] INFO  org.redisson.connection.SentinelConnectionManager  - master: redis://172.17.0.2:6379 added
590  [main] INFO  org.redisson.connection.SentinelConnectionManager  - slave: redis://172.17.0.3:6379 added
590  [main] INFO  org.redisson.connection.SentinelConnectionManager  - slave: redis://172.17.0.8:6379 added
645  [redisson-netty-1-2] INFO  org.redisson.connection.pool.PubSubConnectionPool  - 1 connections initialized for /172.17.0.8:6379
645  [redisson-netty-1-3] INFO  org.redisson.connection.pool.PubSubConnectionPool  - 1 connections initialized for /172.17.0.3:6379
648  [redisson-netty-1-1] INFO  org.redisson.connection.pool.SlaveConnectionPool  - 10 connections initialized for /172.17.0.8:6379
649  [redisson-netty-1-2] INFO  org.redisson.connection.pool.SlaveConnectionPool  - 10 connections initialized for /172.17.0.3:6379
659  [redisson-netty-1-5] INFO  org.redisson.connection.pool.MasterConnectionPool  - 10 connections initialized for /172.17.0.2:6379
677  [redisson-netty-1-3] INFO  org.redisson.connection.SentinelConnectionManager  - sentinel: sentinel:26379 added
679  [main] INFO  com.github.minyk.redis.sentinel.test.SentinelTest  - starting REDISSON client with sentinel hosts: redis://sentinel:26379 master: mymaster
.1720 [main] INFO  com.github.minyk.redis.sentinel.test.SentinelTest  - Sat Jan 19 07:53:44 UTC 2019
..................22963 [pool-1-thread-1] ERROR org.redisson.connection.pool.MasterConnectionPool  - host /172.17.0.2:6379 disconnected due to failedAttempts=3 limit reached
27493 [redisson-3-1] INFO  org.redisson.connection.MasterSlaveEntry  - master /172.17.0.8:6379 excluded from slaves
27493 [redisson-3-1] INFO  org.redisson.connection.SentinelConnectionManager  - slave: 172.17.0.2:6379 has up
27507 [redisson-netty-1-6] INFO  org.redisson.connection.pool.MasterConnectionPool  - 10 connections initialized for /172.17.0.8:6379
27512 [redisson-netty-1-6] INFO  org.redisson.connection.MasterSlaveEntry  - master /172.17.0.2:6379 has changed to redis://172.17.0.8:6379
...............42709 [redisson-3-1] INFO  org.redisson.connection.MasterSlaveEntry  - master /172.17.0.8:6379 excluded from slaves
42709 [redisson-3-1] INFO  org.redisson.connection.SentinelConnectionManager  - slave: 172.17.0.2:6379 has up
..................
```

### jedis

```
0    [main] INFO  com.github.minyk.redis.sentinel.test.SentinelTest  - starting JEDIS sentinel pool with sentinel hosts: sentinel:26379 master: mymaster
8    [main] INFO  redis.clients.jedis.JedisSentinelPool  - Trying to find master from available Sentinels...
90   [main] INFO  redis.clients.jedis.JedisSentinelPool  - Redis master running at 172.17.0.2:6379, starting Sentinel listeners...
315  [main] INFO  redis.clients.jedis.JedisSentinelPool  - Created JedisPool to master at 172.17.0.2:6379
.1326 [main] INFO  com.github.minyk.redis.sentinel.test.SentinelTest  - Sat Jan 19 07:57:01 UTC 2019
.........13361 [main] INFO  com.github.minyk.redis.sentinel.test.SentinelTest  - SERVER DOWN
13361 [main] INFO  com.github.minyk.redis.sentinel.test.SentinelTest  - SERVER is still down.
16366 [main] INFO  com.github.minyk.redis.sentinel.test.SentinelTest  - SERVER is still down.
17131 [MasterListener-mymaster-[sentinel:26379]] INFO  redis.clients.jedis.JedisSentinelPool  - Created JedisPool to master at 172.17.0.3:6379
.17370 [main] INFO  com.github.minyk.redis.sentinel.test.SentinelTest  - SERVER UP, was down for 4009 ms, failed commands: 2
.................
```
