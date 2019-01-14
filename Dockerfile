FROM harbor.ajway.kr/middleware/tomcat8:jre8-DEV-1.0

COPY /target/*.jar /
COPY /entrypoint.sh /

ENV TINI_VERSION v0.18.0
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini
RUN chmod +x /tini
ENTRYPOINT ["/tini", "--"]

# Run your program under Tini

ENV JVM_XMS "1g"
ENV JVM_XMX "1g"

ENV SENTINELS "127.0.0.1:6379"

CMD ["/entrypoint.sh"]
