package mysources.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class QuickSort {
    public static int[] arr = {3,1,2,43,23,77,8,1,23,3,1,4,7};

    public static void main(String[] args) {
//        int[] arr = new int[8000000];
//        for (int i = 0; i < 8000000; i++) {
//            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
//        }

        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        quickSort(arr, 0, arr.length-1);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);

        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr,int left,int right){
        if(left>=right){
            return;
        }
        int jz = arr[left];
        int oldLeft = left;
        int oldRight = right;
        boolean b = false;
        for(;right>left;right--){
            if(arr[right]<jz){
                for(;left<right;left++){
                    if(arr[left]>jz){
                        swap(arr,left,right);
                        break;
                    }
                }
            }
        }
        swap(arr,oldLeft,left);
        quickSort(arr,oldLeft,left-1);
        quickSort(arr,left+1,oldRight);
    }

    public static void swap(int[] arr,int a,int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
