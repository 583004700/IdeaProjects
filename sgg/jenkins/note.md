### 一、运行jenkins
java -jar jenkins.war --httpPort=8080
### 浏览 http://localhost:8080 并按照说明完成安装。用户名：admin 密码：adminadmin

### 二、docker 安装gitlab
#### 下载
docker pull twang2218/gitlab-ce-zh
#### 启动docker服务
docker run -d -p 8443:443 -p 8090:80 -p 8022:22 --restart always --name gitlab -v /usr/local/gitlab/etc:/etc/gitlab -v /usr/local/gitlab/log:/var/log/gitlab -v /usr/local/gitlab/data:/var/opt/gitlab --privileged=true twang2218/gitlab-ce-zh
#### 进入容器
docker exec -it gitlab bash
#### 先进入到gitlab目录
cd /etc/gitlab   
#### 编辑gitlab.rb文件  
vim gitlab.rb
#### 修改
external_url 'http://xx.xx.xx.xx'
gitlab_rails['gitlab_ssh_host'] = '192.168.XX.XX' //和上一个IP输入的一样
gitlab_rails['gitlab_shell_ssh_port'] = 8022 // 此端口是run时22端口映射的8022端口
:wq //保存配置文件并退出
#### 重启服务
gitlab-ctl restart
#### 使用浏览器打开Gitlab
第一次访问默认是root账户，会需要修改密码（密码至少8位数）设置好之后确定就行(rootroot)
URL：http://101.43.XX.XX:8090/
#### 常用命令
容器外停止
docker stop gitlab   // 这里的gitlab 就是我们上一步docker run 当中使用--name 配置的名字
容器外重启
docker restart gitlab
进入容器命令行
docker exec -it gitlab bash
容器中应用配置，让修改后的配置生效
gitlab-ctl reconfigure
容器中重启服务
gitlab-ctl restart

#### jenkins Post Steps Exec command
nohup java -jar /Users/zhuwb/xxoo/springboot-web*.jar > mylog.log 2>&1 &

#### jenkins Pre Steps Exec command
