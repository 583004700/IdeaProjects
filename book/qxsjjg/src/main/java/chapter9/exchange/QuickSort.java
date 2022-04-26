package chapter9.exchange;

/**
 * 快速排序
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[]{30, 24, 5, 58, 18, 36, 12, 42, 39};
        sort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    public static void sort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = left;
        int val = arr[left];
        int leftIndex = left + 1;
        int rightIndex = right;
        while (leftIndex < rightIndex) {
            while (arr[rightIndex] > val && leftIndex < rightIndex) {
                rightIndex--;
            }
            while (arr[leftIndex] < val && leftIndex < rightIndex) {
                leftIndex++;
            }
            int temp = arr[leftIndex];
            arr[leftIndex] = arr[rightIndex];
            arr[rightIndex] = temp;
        }
        if (arr[left] > arr[leftIndex]) {
            arr[left] = arr[leftIndex];
            arr[leftIndex] = val;
            middle = leftIndex;
        }
        sort(arr, left, middle - 1);
        sort(arr, middle + 1, right);
    }
}
