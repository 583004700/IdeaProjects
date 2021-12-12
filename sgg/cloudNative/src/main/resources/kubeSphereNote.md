#1、kubernetes上安装kubeSphere
#一、安装docker
```shell
yum install -y yum-utils
# 配置docker的yum地址
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
# 安装指定版本
yum install -y docker-ce-20.10.7 docker-ce-cli-20.10.7 containerd.io-1.4.6
# 启动&开机启动docker
systemctl enable docker --now
```
二、安装kubernetes
```shell
# 分别设置host
hostnamectl set-hostname k8s-master
hostnamectl set-hostname node1
hostnamectl set-hostname node2


# 
setenforce 0
sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config
swapoff -a
sed -ri 's/.*swap.*/#&/' /etc/fstab
cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
br_netfilter
EOF
cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF
sudo sysctl --system


#安装kubelet、kubeadm、kubectl
cat <<EOF | sudo tee /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=http://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=http://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg 
http://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF

yum install -y kubelet-1.20.9 kubeadm-1.20.9 kubectl-1.20.9

systemctl enable --now kubelet
echo "172.20.10.8 k8s-master" >> /etc/hosts

#初始化master节点
kubeadm init \
--apiserver-advertise-address=172.20.10.8 \
--control-plane-endpoint=k8s-master \
--image-repository registry.cn-hangzhou.aliyuncs.com/lfy_k8s_images \
--kubernetes-version v1.20.9 \
--service-cidr=10.96.0.0/16 \
--pod-network-cidr=192.168.0.0/16

# master执行后的日志信息
            Your Kubernetes control-plane has initialized successfully!
            
            To start using your cluster, you need to run the following as a regular user:
            
              mkdir -p $HOME/.kube
              sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
              sudo chown $(id -u):$(id -g) $HOME/.kube/config
            
            Alternatively, if you are the root user, you can run:
            
              export KUBECONFIG=/etc/kubernetes/admin.conf
            
            You should now deploy a pod network to the cluster.
            Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
              https://kubernetes.io/docs/concepts/cluster-administration/addons/
            
            You can now join any number of control-plane nodes by copying certificate authorities
            and service account keys on each node and then running the following as root:
            
              kubeadm join k8s-master:6443 --token q695lf.a7cv1vm6bcw48i89 \
                --discovery-token-ca-cert-hash sha256:3604f5efb2b4396e05b4c2e71171590b729f67395033c82959b3e52927dba935 \
                --control-plane 
            
            Then you can join any number of worker nodes by running the following on each as root:
            
            kubeadm join k8s-master:6443 --token q695lf.a7cv1vm6bcw48i89 \
                --discovery-token-ca-cert-hash sha256:3604f5efb2b4396e05b4c2e71171590b729f67395033c82959b3e52927dba935


# 安装网络组件
curl https://docs.projectcalico.org/manifests/calico.yaml -O
kubectl apply -f calico.yaml
```
三、安装KubeSphere前置环境

四、安装kubeSphere