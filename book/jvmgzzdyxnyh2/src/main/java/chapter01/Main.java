package chapter01;

public class Main {
    public static void main(String[] args) {
        float a = -5;
        // 单精度浮点数的 IEEE 754 的表示
        System.out.println(Integer.toBinaryString(Float.floatToRawIntBits(a)));
    }
}
