package chapter2.algorithm;

public class MergeSortArray {
    public static void main(String[] args) {
        int[] arr1 = {4, 9, 15, 24, 30};
        int[] arr2 = {2, 6, 18, 20};
        int[] arr3 = new int[arr1.length + arr2.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while(i<arr1.length&&j<arr2.length){
            if(arr1[i]<arr2[j]){
                arr3[k] = arr1[i];
                i++;
            }else if(arr1[i]>arr2[j]){
                arr3[k] = arr2[j];
                j++;
            }
            k++;
        }
        if(i<arr1.length){
            for(int a=i;a<arr1.length;a++){
                arr3[k++] = arr1[a];
            }
        }
        if(j<arr2.length){
            for(int a=j;a<arr2.length;a++){
                arr3[k++] = arr2[a];
            }
        }
        for (int l = 0; l < arr3.length; l++) {
            System.out.println(arr3[l]);
        }
    }
}
