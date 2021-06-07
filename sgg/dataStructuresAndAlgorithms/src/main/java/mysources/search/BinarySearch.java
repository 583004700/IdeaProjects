package mysources.search;

import java.util.HashSet;

public class BinarySearch {
    public static int arr[] = { 1, 3, 4, 5, 6, 7, 8, 9, 10 , 11, 12, 13,14,15,16,17,18,19,20 };

    public static void main(String[] args) {
        System.out.println(binarySearch(arr,20));
    }

    public static int binarySearch(int[] arr,int val){
        int left = 0;
        int right = arr.length - 1;
        int index = 0;
        do{
            if(left>right){
                return -1;
            }
            index = (left + right) / 2;
            if(arr[index] < val) {
                left = index + 1;
            }
            if(arr[index] > val) {
                right = index - 1;
            }
        }while(arr[index] != val);
        return index;
    }
}
