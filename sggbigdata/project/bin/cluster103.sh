#!/bin/bash

case $1 in
"start"){
echo ------------ 启动zookeeper
/opt/module/zookeeper-3.4.10/bin/zkServer.sh start
sleep 20
echo ------------ 启动resourcemanager
yarn-daemon.sh start resourcemanager
sleep 10
echo ------------ 启动datanode
hadoop-daemon.sh start datanode
sleep 10
echo ------------ 启动nodemanager
yarn-daemon.sh start nodemanager
sleep 10
echo ------------ 启动kafka
/opt/module/kafka/bin/kafka-server-start.sh -daemon /opt/module/kafka/config/server.properties
sleep 10
echo ------------ 启动flume
nohup /opt/module/flume/bin/flume-ng agent --conf-file /opt/module/flume/conf/file-flume-kafka.conf --name a1 -Dflume.root.logger=INFO,LOGFILE > /dev/null 2>&1 &
};;
"stop"){
echo ------------ 停止kafka
/opt/module/kafka/bin/kafka-server-stop.sh
sleep 5
echo ------------ 停止flume
ps -ef | grep file-flume-kafka | grep -v grep | awk '{print $2}' | xargs kill
sleep 5
echo ------------ 停止datanode
hadoop-daemon.sh stop datanode
sleep 5
echo ------------ 停止nodemanager
yarn-daemon.sh stop nodemanager
sleep 5
echo ------------ 停止resourcemanager
yarn-daemon.sh stop resourcemanager
sleep 30
echo ------------ 停止zookeeper
/opt/module/zookeeper-3.4.10/bin/zkServer.sh stop
};;
esac
