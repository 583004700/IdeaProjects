package chapter9.distribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序
 */
public class TongSort {
    public static void main(String[] args) {
        int[] arr = new int[]{2, 3, 4, 50, 98, 23, 23, 40, 29, 44, 68, 65, 34, 54, 47};
        List<Integer>[] tong = new ArrayList[10];
        for (int i : arr) {
            int tongIndex = i / 10;
            if(tong[tongIndex] == null){
                tong[tongIndex] = new ArrayList<>();
            }
            tong[tongIndex].add(i);
        }
        List<Integer> result = new ArrayList<>();
        for (List<Integer> l : tong) {
            if(l != null) {
                Collections.sort(l);
                result.addAll(l);
            }
        }
        for (Integer integer : result) {
            System.out.println(integer);
        }
    }
}
