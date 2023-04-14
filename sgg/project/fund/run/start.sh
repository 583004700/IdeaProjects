cd /Users/common/d/module/fund
nohup java -Xms512m -Xmx512m -Dfile.encoding=UTF-8 -DhistorySleepTime=150 -DsleepTime=40 -DhistoryExecMaxTime=10800000 -jar fund-2.2.2.RELEASE.jar &
nohup java -Xms512m -Xmx512m -Dfile.encoding=UTF-8 -jar xxl-job-admin-2.4.0-SNAPSHOT.jar &