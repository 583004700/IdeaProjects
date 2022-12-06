### 打开consul目录
cd /usr/local/Cellar/consul/1.14.1/

### 启动consul服务
consul agent -dev

### 命令

执行consul agent -dev，启动开发模式，这个模式会快速启动一个单节点的Consul。注意，这个模式不能数据持久化，因此，不能用于生产环境
-server：定义agent运行在server模式，每个数据中心的Server建议在3～5个避免失败情况下数据的丢失
-client：定义agent运行在client模式
-bootstrap-expect：在一个datacenter中期望提供的server节点数目，当该值提供的时候，consul一直等到达到指定sever数目的时候才会引导整个集群
-bind：节点的ip地址一般是0.0.0.0或云服务内网地址，用于被集群中的其他节点所访问
-node：指定节点在集群中的唯一名称，默认为机器的hostname
-config-dir：配置文件目录，里面所有以.json结尾的文件都会被加载
-datacenter: 配置数据中心