package chapter10;

public class PrintClassLoaderTree {
    public static void main(String[] args) {
        ClassLoader c1 = PrintClassLoaderTree.class.getClassLoader();
        while(c1 != null){
            System.out.println(c1);
            c1 = c1.getParent();
        }
        // String类由启动加载器加载，启动加载器由c语言实现 所以为 null
        System.out.println(String.class.getClassLoader());
    }
}
