package mysources.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 非递归实现的归并排序
 */
public class MergeSortNoRecursion {

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

        int[] sortArr = new MergeSortNoRecursion().mergeSort(arr,0,arr.length-1);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
        System.out.println(Arrays.toString(sortArr));
    }

    //参数栈
    public static MyStack<Param> paramStack = new MyStack<Param>(100000);
    //返回值栈
    public static MyStack<int[]> resultStack = new MyStack<int[]>(10000);

    class Param{
        public int[] arr;
        public int left;
        public int right;

        public Param(int[] arr, int left, int right) {
            this.setAttrAll(arr,left,right);
        }

        public Param setAttrAll(int[] arr, int left, int right){
            this.arr = arr;
            this.left = left;
            this.right = right;
            return this;
        }
    }

    /**
     * 数组实现的栈比jdk提供的更快
     * @param <T>
     */
    static class MyStack<T>{
        //容量
        public int o;
        //当前有效数据长度
        public int size = 0;
        public Object[] arr;
        public MyStack(int o){
            this.o = o;
            arr = new Object[o];
        }

        public void push(T t){
            arr[size++] = t;
        }

        public T pop(){
            return (T)arr[--size];
        }

        public T peek(){
            return (T)arr[size-1];
        }

        public int size(){
            return size;
        }

        public boolean isEmpty(){
            return size == 0;
        }
    }

    /**
     * 用栈实现带返回值的递归过程，还要判断边界值之类的，所以代码比较复杂
     * @param arr
     * @param left
     * @param right
     * @return
     */
    public int[] mergeSort(int[] arr,int left,int right){
        Param param = new Param(arr,left,right);
        paramStack.push(param);
        Param firstParam = new Param(param.arr,param.left,param.right);
        while(!paramStack.isEmpty()) {
            firstParam.setAttrAll(param.arr,param.left,param.right);
            if(firstParam.left <= firstParam.right) {
                while (true) {
                    if (param.left >= param.right) {
                        param = paramStack.pop();
                        //return new int[]{arr[left]};
                        resultStack.push(new int[]{param.arr[param.left]});
                        if (paramStack.isEmpty()) {
                            return resultStack.pop();
                        }
                        param = paramStack.pop();
                        break;
                    }
                    int middle = (param.left + param.right) / 2;
                    //int[] arr1 = mergeSort(arr, left, middle);
                    param = new Param(param.arr, param.left, middle);
                    paramStack.push(param);
                }
            }else{
                param = paramStack.pop();
                if (!paramStack.isEmpty()) {
                    param = paramStack.pop();
                }
            }
            int[] arr1 = resultStack.pop();
            if(firstParam.left < firstParam.right) {
                while (true) {
                    if (param.left >= param.right) {
                        param = paramStack.pop();
                        //return new int[]{arr[left]};
                        resultStack.push(new int[]{param.arr[param.left]});
                        break;
                    }
                    int middle = (param.left + param.right) / 2;
                    //int[] arr2 = mergeSort(arr, middle + 1, right);
                    param = new Param(param.arr, middle + 1, param.right);
                    paramStack.push(param);
                }
            }
            int[] arr2 = resultStack.pop();
            int sortArr[] = new int[arr1.length + arr2.length];
            int arr1Index = 0;
            int arr2Index = 0;
            for (int i = 0; i < sortArr.length; i++) {
                if (arr1Index == arr1.length) {
                    sortArr[i] = arr2[arr2Index];
                    arr2Index++;
                    continue;
                } else if (arr2Index == arr2.length) {
                    sortArr[i] = arr1[arr1Index];
                    arr1Index++;
                    continue;
                } else if (arr1[arr1Index] < arr2[arr2Index]) {
                    sortArr[i] = arr1[arr1Index];
                    arr1Index++;
                } else if (arr2[arr2Index] <= arr1[arr1Index]) {
                    sortArr[i] = arr2[arr2Index];
                    arr2Index++;
                }
            }
            //return sortArr;
            resultStack.push(sortArr);
            if(paramStack.size() == 0){
                break;
            }
            Param oldParam = paramStack.peek();
            param = new Param(param.arr,param.right + 1,oldParam.right);
            paramStack.push(param);
        }
        return resultStack.pop();
    }

}
