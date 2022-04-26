package chapter10;

/**
 * 并查集，用来判断是否有交集
 */
public class DSU {

    static int[] arr = new int[]{-1, 1, 2, 3, 4, 5, 6, 7};

    public static void main(String[] args) {
        connect(1,2);
        connect(3,4);
        System.out.println(isConnect(1,4));
        connect(2,3);
        System.out.println(isConnect(1,4));
    }

    public static boolean isConnect(int a, int b) {
        return search(a) == search(b);
    }

    public static void connect(int a, int b) {
        int as = search(a);
        int bs = search(b);
        if (as > bs) {
            arr[as] = bs;
        } else {
            arr[bs] = as;
        }
    }

    public static int search(int i) {
        while (arr[i] != i) {
            i = arr[i];
        }
        return i;
    }
}
