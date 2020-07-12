package mysources.sort;

import java.util.Arrays;

public class SelectSort {
    public static int[] arr = {3,9,-1,10,-2};

    public static void main(String[] args) {
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void selectSort(int[] arr){
        for(int i=0;i<arr.length;i++){
            int minIndex = i;
            for(int j=i;j<arr.length;j++){
                if(arr[j]<arr[minIndex]){
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}
