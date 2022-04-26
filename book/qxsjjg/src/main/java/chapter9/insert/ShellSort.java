package chapter9.insert;

/**
 * 希尔排序（插入的一种）
 */
public class ShellSort {
    static int[] arr = new int[]{12, 2, 16, 30, 28, 10, 16, 6, 20, 18};

    public static void main(String[] args) {
        int[] cArr = new int[]{5, 3, 1};  // 这个增长因子，也可以使用 Math.floor(cArr.length<2) 循环计算
        int i = 0;
        while (i < cArr.length) {
            int c = cArr[i];
            int index = 0;
            while (index + c < arr.length) {
                int current = index + c;
                int val = arr[current];
                while (current - c >= 0 && val < arr[current - c]) {
                    arr[current] = arr[current - c];
                    current -= c;
                }
                arr[current] = val;
                index++;
            }
            print();
            i++;
        }
    }

    public static void print() {
        for (int i : arr) {
            System.out.println(i);
        }
        System.out.println("--------------");
    }
}
