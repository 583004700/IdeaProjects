package chapter02.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpSocketClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8888));
        int localPort = socket.getLocalPort();
        System.out.println(localPort);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        for (int i = 0; i < 10000; i++) {
            bufferedWriter.write("aaaaaaaaa\n");
            //第一个判断要素是每个网络包能容纳的数据长度，协议栈会根据一个
            //叫作 MTUA 的参数来进行判断。MTU 表示一个网络包的最大长度，在以太
            //网中一般是 1500 字节（图 2.5）B。MTU 是包含头部的总长度，因此需要从
            //MTU 减去头部的长度，然后得到的长度就是一个网络包中所能容纳的最大
            //数据长度，这一长度叫作 MSSC。当从应用程序收到的数据长度超过或者接
            //近 MSS 时再发送出去，就可以避免发送大量小包的问题了。
            //--------------------------------------------------------------
            // 即使没有调用 flush方法，也会当数据到达一定量的时候发送出去。
            //MTU：Maximum Transmission Unit，最大传输单元。——编者注
            //B　在使用 PPPoE 的 ADSL 等网络中，需要额外增加一些头部数据，因此
            //MTU 会小于 1500 字节。关于 PPPoE，我们将在 4.3.2 节进行讲解。
            //C MSS：Maximum Segment Size，最大分段大小。 TCP 和 IP 的头部加起来一
            //般是 40 字节，因此 MTU 减去这个长度就是 MSS。例如，在以太网中，
            //MTU 为 1500，因此 MSS 就 是 1460。TCP/IP 可以使用一些可选参数
            //（protocol option），如加密等，这时头部的长度会增加，那么 MSS 就会随
            //着头部长度增加而相应缩短。
            //bufferedWriter.flush();
            Thread.sleep(10);
        }
        bufferedWriter.close();
    }
}
