
### Test sample

* App log
```
# java -Xms1g -Xmx1g -cp /redis-sentinel-test-0.1.jar com.github.minyk.redis.sentinel.test.RedisSentinelTest \
>      -s 172.17.0.4:26379,172.17.0.5:26379,172.17.0.6:26379 \
>      -t 1000 \
>      -m mymaster
starting pool with sentinel hosts: 172.17.0.4:26379,172.17.0.5:26379,172.17.0.6:26379master: mymaster
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
.
Mon Jan 14 08:25:36 UTC 2019
...................................................
Mon Jan 14 08:26:30 UTC 2019, SERVER DOWN
xx.
Mon Jan 14 08:26:34 UTC 2019, SERVER UP, was down for 4008ms, failed commands: 2
...........................................
Mon Jan 14 08:27:20 UTC 2019, SERVER DOWN
xx.
Mon Jan 14 08:27:24 UTC 2019, SERVER UP, was down for 4011ms, failed commands: 2
....
Mon Jan 14 08:27:28 UTC 2019
........................^C
```

* Sentinel log
```
sentinel_3  | 1:X 14 Jan 08:06:52.864 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
sentinel_3  | 1:X 14 Jan 08:06:52.867 # Sentinel ID is a7c0113c1edf50a7996ebf14193f6e9c875e3d09
sentinel_3  | 1:X 14 Jan 08:06:52.867 # +monitor master mymaster 172.17.0.2 6379 quorum 2
sentinel_3  | 1:X 14 Jan 08:06:52.868 * +slave slave 172.17.0.3:6379 172.17.0.3 6379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:06:52.898 * +sentinel sentinel 9153d3325fd8d9f31decd7a20c96fe9163465974 172.17.0.4 26379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:06:55.015 * +sentinel sentinel 0fa9797af439cde8687c8de1428a60593c04e2da 172.17.0.6 26379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:07:02.885 * +slave slave 172.17.0.7:6379 172.17.0.7 6379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:07:13.013 * +fix-slave-config slave 172.17.0.7:6379 172.17.0.7 6379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:26:33.268 # +sdown master mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:26:33.346 # +new-epoch 1
sentinel_3  | 1:X 14 Jan 08:26:33.348 # +vote-for-leader 9153d3325fd8d9f31decd7a20c96fe9163465974 1
sentinel_3  | 1:X 14 Jan 08:26:33.361 # +odown master mymaster 172.17.0.2 6379 #quorum 3/2
sentinel_3  | 1:X 14 Jan 08:26:33.361 # Next failover delay: I will not start a failover before Mon Jan 14 08:26:43 2019
sentinel_3  | 1:X 14 Jan 08:26:34.461 # +config-update-from sentinel 9153d3325fd8d9f31decd7a20c96fe9163465974 172.17.0.4 26379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:26:34.462 # +switch-master mymaster 172.17.0.2 6379 172.17.0.3 6379
sentinel_3  | 1:X 14 Jan 08:26:34.463 * +slave slave 172.17.0.7:6379 172.17.0.7 6379 @ mymaster 172.17.0.3 6379
sentinel_3  | 1:X 14 Jan 08:26:34.463 * +slave slave 172.17.0.2:6379 172.17.0.2 6379 @ mymaster 172.17.0.3 6379
sentinel_3  | 1:X 14 Jan 08:26:39.479 # +sdown slave 172.17.0.2:6379 172.17.0.2 6379 @ mymaster 172.17.0.3 6379
sentinel_3  | 1:X 14 Jan 08:26:49.670 # -sdown slave 172.17.0.2:6379 172.17.0.2 6379 @ mymaster 172.17.0.3 6379
sentinel_3  | 1:X 14 Jan 08:26:59.678 * +convert-to-slave slave 172.17.0.2:6379 172.17.0.2 6379 @ mymaster 172.17.0.3 6379
sentinel_3  | 1:X 14 Jan 08:27:23.307 # +sdown master mymaster 172.17.0.3 6379
sentinel_3  | 1:X 14 Jan 08:27:23.308 # +sdown slave 172.17.0.7:6379 172.17.0.7 6379 @ mymaster 172.17.0.3 6379
sentinel_3  | 1:X 14 Jan 08:27:23.467 # +new-epoch 2
sentinel_3  | 1:X 14 Jan 08:27:23.468 # +vote-for-leader 0fa9797af439cde8687c8de1428a60593c04e2da 2
sentinel_3  | 1:X 14 Jan 08:27:23.994 # +config-update-from sentinel 0fa9797af439cde8687c8de1428a60593c04e2da 172.17.0.6 26379 @ mymaster 172.17.0.3 6379
sentinel_3  | 1:X 14 Jan 08:27:23.995 # +switch-master mymaster 172.17.0.3 6379 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:27:23.995 * +slave slave 172.17.0.7:6379 172.17.0.7 6379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:27:23.995 * +slave slave 172.17.0.3:6379 172.17.0.3 6379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:27:29.023 # +sdown slave 172.17.0.3:6379 172.17.0.3 6379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:27:29.023 # +sdown slave 172.17.0.7:6379 172.17.0.7 6379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:27:39.168 # -sdown slave 172.17.0.3:6379 172.17.0.3 6379 @ mymaster 172.17.0.2 6379
sentinel_3  | 1:X 14 Jan 08:27:39.169 # -sdown slave 172.17.0.7:6379 172.17.0.7 6379 @ mymaster 172.17.0.2 6379
```