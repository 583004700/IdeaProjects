package chapter9.insert;

/**
 * 插入排序
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = new int[]{12, 2, 16, 30, 28, 10, 16, 20, 6, 18};
        int selected = 0;
        while (selected < arr.length - 1) {
            int current = selected + 1;
            int val = arr[current];
            while (current > 0 && val < arr[current - 1]) {
                arr[current] = arr[current - 1];
                current--;
            }
            arr[current] = val;
            selected++;
        }
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
