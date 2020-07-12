#!/bin/bash

PWD=/usr/local/script/jiankong

URL="http://10.10.10.11/index.html"

HTTP_CODE=`curl -o /dev/null -s -w "%{http_code}" "${URL}"` 


if [ $HTTP_CODE != 200 ]
    then
	service heartbeat stop
fi
