package mysources.sort;

import java.util.Arrays;

public class ShellSort {

    public static int[] arr = {3,9,-1,10,-2,7,9,5,3,2,8,6,-10,-9,-1};

    private static int c = 2;

    public static void main(String[] args) {
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void shellSort(int[] arr){
        int h = 1;
        while(h<arr.length/c){
            h = h*c+1;
        }
        while(h>0) {
            for (int i = h; i < arr.length; i++) {
                int insertValue = arr[i];
                int j = i;
                for (; j >= h; j -= h) {
                    if (arr[j - h] > insertValue) {
                        arr[j] = arr[j - h];
                    } else {
                        break;
                    }
                }
                int insertIndex = j;
                arr[insertIndex] = insertValue;
            }
            h = h / c;
        }
    }
}
