package chapter04;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftRefQueue {
    public static class User {
        public User(int id, String name) {
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

    static ReferenceQueue<User> referenceQueue = null;

    public static class CheckRefQueue extends Thread {
        @Override
        public void run() {
            while (true) {
                if (referenceQueue != null) {
                    UserSoftReference obj = null;
                    try {
                        obj = (UserSoftReference) referenceQueue.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (obj != null) {
                        System.out.println("user id:" + obj.uid + "is delete");
                    }
                }
            }
        }
    }

    public static class UserSoftReference extends SoftReference<User> {
        int uid;

        public UserSoftReference(User referent, ReferenceQueue<? super User> q) {
            super(referent, q);
            uid = referent.id;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new CheckRefQueue();
        t.setDaemon(true);
        t.start();
        User user = new User(1, "geym");
        referenceQueue = new ReferenceQueue<User>();
        UserSoftReference userSoftReference = new UserSoftReference(user, referenceQueue);
        user = null;
        System.out.println(userSoftReference.get());
        System.gc();
        // 内存足够，不会被回收
        System.out.println("After GC:");
        System.out.println(userSoftReference.get());
        System.out.println("try to create byte array and GC");
        byte[] b = new byte[1024 * 925 * 7];
        System.gc();
        System.out.println(userSoftReference.get());
        Thread.sleep(1000);
    }
}
