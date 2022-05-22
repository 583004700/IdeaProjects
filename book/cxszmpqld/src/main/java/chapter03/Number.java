package chapter03;

public class Number {
    public static void main(String[] args) {
        float f = 0.1f;
        for (int i = 0; i < 100; i++) {
            f+=0.1;
        }
        System.out.println(f);
        System.out.println("---------------------------");
        // 二进制数的小数 1011.0011    1*2^3+0*2^2+1*2^1+1*2^0 + 0*2^-1+0*2^-2+1*2^-3+1*2^-4
    }
}
