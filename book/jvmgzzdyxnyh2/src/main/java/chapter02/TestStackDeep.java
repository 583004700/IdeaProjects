package chapter02;

public class TestStackDeep {
    public static int count;

    public static void recursion() {
        count++;
        recursion();
    }

    public static void recursion(long a, long b, long c) {
        //由于局部变量表在栈帧之中，因此，如果函数的参数和局部变量较多，会使局部变量 表膨胀，
        // 从而每一次函数调用就会占用更多的栈空间，最终导致函数的嵌套调用次数减 少。
        long e = 1, f = 2, g = 3, h = 4, i = 5, j = 6, k = 7, l = 8, m = 9, n = 10;
        count++;
        recursion(a, b, c);
    }

    public static void main(String[] args) {
        try {
            // 可以使用 -Xss 设置最大栈空间大小
            // 函数嵌套调用的层次在很大程度上由栈的大小决定，栈越大，函数可以支持 的嵌套调用次数就越多。
            //recursion();
            recursion(1, 2, 3);
        } catch (Throwable e) {
            System.out.println("deep of calling = " + count);
            e.printStackTrace();
        }
    }
}
