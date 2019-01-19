
# How to run

* parameters:
```bash
% java -cp ./target/redis-sentinel-test-0.1.jar com.github.minyk.redis.sentinel.test.RedisSentinelTest --help
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
java -Xms1g -Xmx1g \
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

### Test sample

* App log
```
TBD
```

* Sentinel log
```
TBD
```