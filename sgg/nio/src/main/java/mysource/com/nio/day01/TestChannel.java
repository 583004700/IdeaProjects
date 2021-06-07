package mysource.com.nio.day01;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

public class TestChannel {
    @Test
    public void test6() throws Exception{
        Charset cs1 = Charset.forName("GBK");
        CharsetEncoder ce = cs1.newEncoder();
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("你好世界");
        cBuf.flip();

        ByteBuffer bf = ce.encode(cBuf);
        for (int i = 0; i < 8; i++) {
            System.out.println(bf.get());
        }

        bf.flip();
        CharBuffer cf = cd.decode(bf);
        System.out.println(cf.toString());
    }

    @Test
    public void test5() throws Exception{
        Map<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String,Charset>> entrys = map.entrySet();
        for(Map.Entry<String,Charset> e:entrys){
            System.out.println(e.getKey()+"="+e.getValue());
        }
    }

    @Test
    public void test4() throws Exception{
        RandomAccessFile raf1 = new RandomAccessFile("f:/1.txt","rw");
        FileChannel channel1 = raf1.getChannel();
        ByteBuffer buf1 = ByteBuffer.allocate(99);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        ByteBuffer[] bufs = {buf1,buf2};
        channel1.read(bufs);

        for(ByteBuffer b:bufs){
            b.flip();
        }

        System.out.println(new String(bufs[0].array()));
        System.out.println(new String(bufs[1].array()));

        RandomAccessFile raf2 = new RandomAccessFile("f:/2.txt","rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);
    }

    @Test
    public void test3() throws IOException{
        FileChannel inChannel = FileChannel.open(Paths.get("f:/nio.zip"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("f:/nio1.zip"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

//		inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    //使用直接缓冲区完成文件的复制(内存映射文件)
    @Test
    public void test2() throws IOException {//2127-1902-1777
        long start = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get("f:/nio.zip"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("f:/nio1.zip"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        int count = 0;
        final int buffSize = Integer.MAX_VALUE;
        long s = 0;
        long pos = 0;
        long totalCount = inChannel.size() % buffSize == 0 ? inChannel.size() / buffSize : inChannel.size() / buffSize + 1;
        System.out.println("totalCount:"+totalCount);
        do{
            //内存映射文件
            pos = s * count;
            s = buffSize > inChannel.size() - pos ? inChannel.size() - pos : buffSize;
            MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, pos, s);
            MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, pos, s);
            outMappedBuf.put(inMappedBuf);
            count++;
            System.out.println("count："+count);
        }while(count < totalCount);


        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }
}
