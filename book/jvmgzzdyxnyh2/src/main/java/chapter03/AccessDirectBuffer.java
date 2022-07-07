package chapter03;

import java.nio.ByteBuffer;

public class AccessDirectBuffer {
    public void directAccess(){
        long startTime = System.currentTimeMillis();
        ByteBuffer b = ByteBuffer.allocateDirect(500);
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++) {
                b.putInt(j);
            }
            b.flip();
            for (int j = 0; j < 99; j++) {
                b.getInt();
            }
            b.clear();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("testDirectWrite:"+(endTime-startTime));
    }

    public void bufferAccess(){
        long startTime = System.currentTimeMillis();
        ByteBuffer b = ByteBuffer.allocate(500);
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++) {
                b.putInt(j);
            }
            b.flip();
            for (int j = 0; j < 99; j++) {
                b.getInt();
            }
            b.clear();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("testBufferWrite:"+(endTime-startTime));
    }

    public static void main(String[] args) {
        //直接内存适合申请次数较少、访问较频繁的场合。如果需要频繁 申请内存空间，则并不适合使用直接内存。
        AccessDirectBuffer accessDirectBuffer = new AccessDirectBuffer();
        accessDirectBuffer.bufferAccess();
        accessDirectBuffer.directAccess();

        accessDirectBuffer.bufferAccess();
        accessDirectBuffer.directAccess();
    }
}
