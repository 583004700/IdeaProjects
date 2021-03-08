package three;

public class StringPool58Demo {
    public static void main(String[] args) {


        String str1 = new StringBuilder("58").append("tongcheng").toString();
        System.out.println(str1);
        System.out.println(str1.intern());
        System.out.println(str1 == str1.intern());

        System.out.println();

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());
        // java是false，因为有一个初始的字符串java在常量池中已经存在 sun.misc.Version 中
        System.out.println(str2 == str2.intern());
    }
}
