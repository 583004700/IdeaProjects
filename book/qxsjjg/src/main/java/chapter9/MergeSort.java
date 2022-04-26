package chapter9;

/**
 * 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = new int[]{42, 15, 20, 6, 8, 38, 50, 12};
        //int[] arr = new int[]{12,43};
        int[] result = mergeSort(arr, 0, arr.length - 1);
        for (int i : result) {
            System.out.println(i);
        }
    }

    public static int[] mergeSort(int[] arr, int left, int right) {
        if (right == left) {
            return new int[]{arr[left]};
        }
        if (left < 0 || right >= arr.length || left > right) {
            return new int[]{};
        }
        int middle = (left + right) / 2;
        int[] arr1 = mergeSort(arr, left, middle);
        int[] arr2 = mergeSort(arr, middle + 1, right);
        int[] temp = new int[arr1.length + arr2.length];
        int arr1Index = 0;
        int arr2Index = 0;
        int tempIndex = 0;
        while (arr1Index < arr1.length) {
            if (arr2Index < arr2.length) {
                if (arr1[arr1Index] < arr2[arr2Index]) {
                    temp[tempIndex++] = arr1[arr1Index];
                    arr1Index++;
                } else {
                    temp[tempIndex++] = arr2[arr2Index];
                    arr2Index++;
                }
            }else{
                temp[tempIndex++] = arr1[arr1Index];
                arr1Index++;
            }
        }
        while (arr2Index < arr2.length){
            temp[tempIndex++] = arr2[arr2Index];
            arr2Index++;
        }
        return temp;
    }
}
