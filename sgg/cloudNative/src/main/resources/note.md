##一、基础环境
### 1、安装yum-utils
```shell
yum install -y yum-utils
```
### 2、配置yum 源
```shell
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```
### 3、安装docker
```shell
yum install -y docker-ce-20.10.7 docker-ce-cli-20.10.7 containerd.io-1.4.6
```
### 4、启动
```shell
systemctl enable docker --now
```
### 5、设置主机名
```shell
hostnamectl set-hostname k8s-master
hostnamectl set-hostname k8s-node1
hostnamectl set-hostname k8s-node2
```
### 6、将SELinux 设置为 permissive 模式（相当于将其禁用）
```shell
setenforce 0
sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config
```
### 7、关闭swap分区
```shell
swapoff -a
sed -ri 's/.*swap.*/#&/' /etc/fstab
```
### 8、允许 iptables 检查桥接流量
```shell
cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
br_netfilter
EOF

cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF
sudo sysctl --system
```
### 9、安装kubelet、kubeadm、kubectl
```shell
cat <<EOF | sudo tee /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=http://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=http://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg 
http://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
exclude=kubelet kubeadm kubectl
EOF

yum install -y kubelet-1.20.9 kubeadm-1.20.9 kubectl-1.20.9 --disableexcludes=kubernetes

systemctl enable --now kubelet
```
### 10、下载各个机器需要的镜像(master节点都需要)
```shell
tee ./images.sh <<-'EOF'
#!/bin/bash
images=(
kube-apiserver:v1.20.9
kube-proxy:v1.20.9
kube-controller-manager:v1.20.9
kube-scheduler:v1.20.9
coredns:1.7.0
etcd:3.4.13-0
pause:3.2
)
for imageName in ${images[@]} ; do
docker pull registry.cn-hangzhou.aliyuncs.com/lfy_k8s_images/$imageName
done
EOF

chmod +x ./images.sh && ./images.sh
```
### 11、初始化主节点
```shell
# 所有机器添加master域名映射，以下需要修改为自己的
echo "172.31.0.2 cluster-endpoint" >> /etc/hosts

# 主节点初始化（以下命令只在master节点运行）# 所有网络范围不重叠
kubeadm init \
--apiserver-advertise-address=172.31.0.2 \
--control-plane-endpoint=cluster-endpoint \
--image-repository registry.cn-hangzhou.aliyuncs.com/lfy_k8s_images \
--kubernetes-version v1.20.9 \
--service-cidr=10.96.0.0/16 \
--pod-network-cidr=192.168.0.0/16

# 创建目录和配置
mkdir -p $HOME/.kube
cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
chown $(id -u):$(id -g) $HOME/.kube/config

# 安装网络组件
curl https://docs.projectcalico.org/manifests/calico.yaml -O
# 应用配置
kubectl apply -f calico.yaml

# 创建加入集群的命令
kubeadm token create --print-join-command
```
### 12、加入其它节点（在非主节点执行）
```shell
# 利用上一步创建好的加入集群的命令执行
kubeadm join cluster-endpoint:6443 --token httx5v.jjujup0c2vbevns9     --discovery-token-ca-cert-hash sha256:cb212cd751a3c90e09514755c466bd605cd55d8e8bf073fa89db4d24b6f2e78c
```
### 13、安装可视化的web界面
```shell
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.3.1/aio/deploy/recommended.yaml
```