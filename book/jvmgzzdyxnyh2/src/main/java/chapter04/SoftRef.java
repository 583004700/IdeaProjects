package chapter04;

import java.lang.ref.SoftReference;

public class SoftRef {
    public static class User{
        public User(int id,String name){
            this.id = id;
            this.name = name;
        }
        public int id;
        public String name;

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        User u = new User(1,"geym");
        SoftReference<User> userSoftRef = new SoftReference<User>(u);
        u = null;
        System.out.println(userSoftRef.get());
        System.gc();
        System.out.println("After GC:");
        System.out.println(userSoftRef.get());
        byte[] b = new byte[1024*1024*7];
        System.gc();
        // 软引用在内存不足时会被清除
        System.out.println(userSoftRef.get());
    }
}
