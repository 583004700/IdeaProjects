package chapter9.select;

/**
 * 堆排序
 */
public class HeapSort {
    static int[] arr = new int[]{28, 20, 16, 18, 2, 16, 6, 10, 12, 30};

    public static void main(String[] args) {
        for (int i = arr.length / 2; i >= 0; i--) {
            down(i,arr.length);
        }
        int last = arr.length - 1;
        while(last>0) {
            int temp = arr[last];
            arr[last] = arr[0];
            arr[0] = temp;
            down(0,last);
            last--;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public static void down(int index,int length) {
        while (index < length) {
            int leftIndex = index * 2 + 1;
            int maxIndex = leftIndex;
            if (leftIndex < length) {
                int rightIndex = leftIndex + 1;
                if (rightIndex < length) {
                    if (arr[rightIndex] > arr[leftIndex]) {
                        maxIndex = rightIndex;
                    }
                }
                if (arr[index] < arr[maxIndex]) {
                    int temp = arr[index];
                    arr[index] = arr[maxIndex];
                    arr[maxIndex] = temp;
                }
            } else {
                break;
            }
            index = maxIndex;
        }

    }
}
