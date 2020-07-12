FROM hub.c.163.com/public/centos:6.7
MAINTAINER wangyang@itxdl.cn

ADD ./jdk-8u181-linux-x64.tar.gz /root
ADD ./apache-tomcat-7.0.103.tar.gz /root

ENV JAVA_HOME /root/jdk1.8.0_181
ENV PATH $JAVA_HOME/bin:$PATH

EXPOSE 8080

ENTRYPOINT /root/apache-tomcat-7.0.103/bin/startup.sh && tailf /root/apache-tomcat-7.0.103/logs/catalina.out