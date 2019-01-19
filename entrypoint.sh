#!/usr/bin/env bash

java -Xms${JVM_XMS} -Xmx${JVM_XMX} \
     -cp /redis-sentinel-test-0.1.jar \
     com.github.minyk.redis.sentinel.test.RedisSentinelTest \
     -s ${SENTINELS} \
     -t 1000 \
     -m mymaster \
     -p pass \
     -l ${LIB}
