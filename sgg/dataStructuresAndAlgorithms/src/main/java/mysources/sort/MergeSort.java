package mysources.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MergeSort {

    public static int arr[] = { 2,4,6,8,10 ,1,3,5,7,9 ,10,11,13 , 8,7,12,45,22,3,24,456,
         13 , 8,7,12,100,256,875,666,397,111,357,48,33,87,9,57,35,61,666,555,333,444,111,22,89,999,1314,-5,-10,-49,34
     };

    public static void main(String[] args) {
//        int[] arr = new int[80000000];
//        for (int i = 0; i < 80000000; i++) {
//            arr[i] = (int) (Math.random() * 80000000); // 生成一个[0, 8000000) 数
//        }
        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        int[] sortArr = mergeSort(arr,0,arr.length-1);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
        //System.out.println(Arrays.toString(sortArr));
    }

    public static int[] mergeSort(int[] arr,int left,int right){

        if(left >= right){
            return new int[]{arr[left]};
        }

        int middle = (left + right) / 2;

        int[] arr1 = mergeSort(arr,left,middle);
        int[] arr2 = mergeSort(arr,middle+1,right);

        int sortArr[] = new int[arr1.length+arr2.length];
        int arr1Index = 0;
        int arr2Index = 0;
        for(int i=0;i<sortArr.length;i++){
            if(arr1Index == arr1.length){
                sortArr[i] = arr2[arr2Index];
                arr2Index++;
                continue;
            }else if(arr2Index == arr2.length){
                sortArr[i] = arr1[arr1Index];
                arr1Index++;
                continue;
            }else if(arr1[arr1Index] < arr2[arr2Index]){
                sortArr[i] = arr1[arr1Index];
                arr1Index++;
            }else if(arr2[arr2Index] <= arr1[arr1Index]){
                sortArr[i] = arr2[arr2Index];
                arr2Index++;
            }
        }
        return sortArr;
    }

}
