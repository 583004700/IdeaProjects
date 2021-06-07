package mysource.com.nio.day01;

import org.junit.Test;

import java.nio.ByteBuffer;

public class TestBuffer {

    @Test
    public void test3(){
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect());
    }

    @Test
    public void test2(){
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst,0,2);
        System.out.println(new String(dst,0,2));

        System.out.println(buf.position());
        buf.mark();

        buf.get(dst,2,2);
        System.out.println(new String(dst,2,2));

        buf.reset();
        System.out.println(buf.position());

        if(buf.hasRemaining()){
            System.out.println(buf.remaining());
        }
    }

    @Test
    public void test1(){
        String str = "abcde";

        ByteBuffer buf = ByteBuffer.allocate(1024);

        System.out.println("----------------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        buf.put(str.getBytes());
        System.out.println("---------put-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        buf.flip();

        System.out.println("---------flip-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        byte[] dst = new byte[buf.limit()];
        buf.get(dst);

        System.out.println("---------get-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        System.out.println(new String(dst,0,dst.length));

        buf.rewind();
        System.out.println("---------rewind-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        buf.clear();
        System.out.println("---------clear-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
    }
}
