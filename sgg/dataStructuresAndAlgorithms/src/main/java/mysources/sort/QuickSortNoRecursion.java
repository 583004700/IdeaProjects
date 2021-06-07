package mysources.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Stack;

public class QuickSortNoRecursion {
    public static int[] arr = {3,9,-1,10,-2,7,9,5,3,2,8,6,-10,-9,-1};

    public static void main(String[] args) {
        int[] arr = new int[800];
        for (int i = 0; i < 800; i++) {
            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        new QuickSortNoRecursion().quickSort(arr, 0, arr.length-1);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);

        System.out.println(Arrays.toString(arr));
    }

    static Stack<Param> paramStack = new Stack<Param>();

    class Param{
        public int[] arr;
        public int left;
        public int right;

        public Param(int[] arr, int left, int right) {
            this.arr = arr;
            this.left = left;
            this.right = right;
        }
    }

    public void quickSort(int[] arr,int left,int right){
        paramStack.push(new Param(arr,left,right));

        Param param = null;

        while(paramStack.size() != 0) {
            param = paramStack.pop();
            left = param.left;
            right = param.right;
            if (left >= right) {
               continue;
            }
            int jz = arr[left];
            int oldLeft = left;
            int oldRight = right;
            boolean b = false;
            for (; right > left; right--) {
                if (arr[right] < jz) {
                    for (; left < right; left++) {
                        if (arr[left] > jz) {
                            swap(arr, left, right);
                            break;
                        }
                    }
                }
            }
            swap(arr, oldLeft, left);
            //quickSort(arr, oldLeft, left - 1);
            //quickSort(arr, left + 1, oldRight);
            paramStack.push(new Param(arr,left + 1,oldRight));
            paramStack.push(new Param(arr,oldLeft,left-1));
        }

    }

    public static void swap(int[] arr,int a,int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
