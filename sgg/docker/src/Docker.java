public class Docker {

    public static void main(String[] args) {
        //cat /etc/redhat-release 查看CentOS版本
        /**
         * centos7下安装docker
         *1.移除旧版本
         *sudo yum remove docker \
         *                   docker-client \
         *                   docker-client-latest \
         *                   docker-common \
         *                   docker-latest \
         *                   docker-latest-logrotate \
         *                   docker-logrotate \
         *                   docker-selinux \
         *                   docker-engine-selinux \
         *                   docker-engine
         * 2.安装一些必要的系统工具
         * sudo yum install -y yum-utils device-mapper-persistent-data lvm2
         * 3.添加软件源信息
         * sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
         * 4.更新yum缓存
         * sudo yum makecache fast
         * 5.安装docker-ce
         * sudo yum -y install docker-ce
         * 6.启动docker后台服务
         * sudo systemctl start docker
         * 7.测试运行hello-world
         * docker run hello-world
         *
         * 配置阿里云镜像加速
         * https://dev.aliyun.com/search.html
         *
         * sudo mkdir -p /etc/docker
         * sudo tee /etc/docker/daemon.json <<-'EOF'
         * {
         *   "registry-mirrors": ["https://j26uokr2.mirror.aliyuncs.com"]
         * }
         * EOF
         * sudo systemctl daemon-reload
         * sudo systemctl restart docker
         *
         * 镜像命令
         * docker images 列出本机的镜像
         * docker search tomcat  查找镜像
         * docker pull tomcat  拉取镜像
         * docker rmi -f tomcat  删除镜像
         * docker rmi -f tomcat nginx  删除多个镜像
         * docker rmi -f $(docker images -qa)   删除所有镜像
         *
         * 容器命令
         * docker run -it centos 运行容器
         * docker ps 列出正在运行的容器
         * docker ps -l 上一个容器
         * docker ps -a 所有运行过的容器
         *
         * exit 退出并关闭容器
         * ctrl + p + q 退出不关闭容器
         * docker start 容器id  启动已经exit退出的容器
         * docker restart 容器id  重启容器
         * docker stop 容器id     停止容器
         * docker kill 容器id     强制停止容器
         * docker rm  容器id      删除已停止的容器
         * docker rm -f  容器id   强制删除容器
         *
         * docker run -d centos  后台启动容器，必须有一个前台进程，不然会直接关闭
         * 比如：docker run -d centos /bin/sh -c "while true;do echo hello world;sleep 2;done" 这样的话docker容器也不会关闭
         * docker logs -f -t --tail 3 容器id
         * docker top 容器id  查看内容内的进程
         * docker inspect 容器id    查看容器细节
         * docker attach  容器id    重新进入容器
         * docker exec -t 容器id ls -l /tmp   在容器中执行 ls -l /tmp命令，不会进入容器
         * docker cp d4557f38026c:/tmp/yum.log /root    将容器内的文件复制到外面的机器
         *
         * docker run -it -p 8888:8080 tomcat 指定docker对外的端口是8888，访问机器的8888端口时，会访问到docker内部的8080端口，也就是tomcat默认启动端口
         * docker run -it -P tomcat   不指定固定的端口
         * docker run -d -P tomcat   不指定固定的端口       后台启动tomcat
         *
         * docker commit -a="zzyy" -m="tomcat without docs" fda0e32cd5ba atguigu/mytomcat:1.2    提交
         *
         *
         * docker run -it -v /myDataVolume:/dataVolumeContainer centos        myDataVolume是机器上的目录；dataVolumeContainer是容器中的目录
         * docker run -it -v /myDataVolume:/dataVolume:ro centos        只读，在容器内不能修改
         *
         * vim Dockerfile
         * docker build -f /mydocker/Dockerfile -t zzyy/centos .
         * docker run -it --name dc02 --volumes-from dc01 zzyy/centos    容器间数据共享
         *
         * docker history 镜像id
         *
         * docker 同步到阿里云
         */
    }
}
