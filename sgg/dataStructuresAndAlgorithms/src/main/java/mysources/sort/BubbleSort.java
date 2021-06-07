package mysources.sort;

import java.util.Arrays;

public class BubbleSort {
    public static int[] arr = {3,9,-1,10,20};

    public static void main(String[] args) {
        bubbleSort();
        System.out.println(Arrays.toString(arr));
    }


    public static void bubbleSort(){
        for(int i=0;i<arr.length-1;i++){
            boolean flag = false;
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    flag = true;
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            if(!flag){
                break;
            }
        }
    }
}
