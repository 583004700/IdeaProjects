package mysources.sort;

import java.util.Arrays;

public class InsertSort {

    public static int[] arr = {3,9,-2,10,-1};

    public static void main(String[] args) {
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort(int[] arr){
        for(int i=1;i<arr.length;i++){
            int insertValue = arr[i];
            int j = i - 1;
            for(;j>=0;j--){
                if(insertValue<arr[j]){
                    arr[j+1] = arr[j];
                }else{
                    break;
                }
            }
            int insertIndex = j + 1;
            arr[insertIndex] = insertValue;
        }
    }
}
