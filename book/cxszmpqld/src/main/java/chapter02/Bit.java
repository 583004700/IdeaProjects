package chapter02;

public class Bit {
    public static void main(String[] args) {
        //位左移运算如果是int类型，以32位为空间计算溢出，long类型，以64位为空间溢出，1左移31位虽然没有溢出，但也变成负数了，和我们想要的结果不一样了
        //左移运算如果溢出时，会在最低位补上溢出的内容
        long b = 1 << 30;
        System.out.println(b);
        System.out.println("--------------------------------");
        //计算机保存负数的方式是使用补码，比如32位时，-1是 11111111111111111111111111111111，补码的计算是反码+1
        String s = Long.toBinaryString(-1);
        System.out.println(s);
        System.out.println("--------------------------------");
        //算术右移(>>)运算如果是正数，在最高位补0，负数在最高位补1，可实现 1/2 ， 1/4 等效果，逻辑右移(>>>)不管正数还是负数都补0
        long d = -24 >> 2;
        System.out.println(d);
        System.out.println(Integer.toBinaryString(-24));
        System.out.println(Integer.toBinaryString(-6));
        System.out.println(-24>>>2);
        System.out.println("--------------------------------");
        //符号扩充，将8位二进制转为16位二进制或32位二进制
        //只需要把符号位扩充即可(正数最高位补0，负数最高位补1)
        int e = -56;
        System.out.println(Integer.toBinaryString(e));
        System.out.println(Long.toBinaryString(e));
        //运算 & 同时为1时为1，否则为0
        // | 只要有一个为1就为1，否则为0
        // ^ 相同的时候为0，否则为1
        // ~ 取反
        int a1 = 2&3;
        int a2 = 2|3;
        int a3 = 2^3;
        int a4 = ~2;

    }
}
