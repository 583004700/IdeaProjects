#!/bin/bash
rm -rf xxoo
appname=$1
pid=`ps -ef | grep $appname | grep 'java -jar' | awk '{printf $2}'`
echo $pid

if [ -z $pid ];
#使用 -z 作空值判断
        then
                echo "$appname not started"
        else
                kill -9 $pid
                echo "$appname stoping..."
fi