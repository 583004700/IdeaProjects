package chapter02.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpSocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        // windows使用 netstat -ano 命令,可以查看到正在等待连接的状态     0.0.0.0 代表通信还没开始，地址不确定
        // TCP    0.0.0.0:8888           0.0.0.0:0              LISTENING       18628
        Socket accept = serverSocket.accept();
        int localPort = serverSocket.getLocalPort();
        System.out.println("localPort:"+localPort);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        String s = null;
        while((s = bufferedReader.readLine()) != null){
            System.out.println(s);
        }
    }
}
