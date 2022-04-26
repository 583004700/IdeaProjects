package question.cj;

import java.util.ArrayList;
import java.util.List;

public class CJava {
    static String[] arr = new String[]{"C", "J", "J", "J", "J", "C", "C","J","J","C","C"};

    public static void swap(int i,int j){
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void print(){
        for (String s : arr) {
            System.out.print(s+",");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("原数组：");
        print();
        List<Integer> errorCList = new ArrayList<Integer>();
        List<Integer> errorJList = new ArrayList<Integer>();

        List<Integer> error2CList = new ArrayList<Integer>();
        List<Integer> error2JList = new ArrayList<Integer>();

        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            if (i % 2 == 1 && s.equals("C")) {
                errorCList.add(i);
            } else if (i % 2 == 0 && s.equals("J")) {
                errorJList.add(i);
            }
        }
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            if (i % 2 == 0 && s.equals("C")) {
                error2CList.add(i);
            } else if (i % 2 == 1 && s.equals("J")) {
                error2JList.add(i);
            }
        }
        boolean b = false;
        boolean b2 = false;
        int count = Integer.MAX_VALUE;
        if(!(Math.abs(errorJList.size() - errorCList.size())>0)){
            b = true;
            count = errorJList.size();
        }
        if(!(Math.abs(error2JList.size() - error2CList.size())>0)){
            if(error2CList.size()<count){
                b2 = true;
                count = error2CList.size();
            }
        }
        System.out.println("能否成立："+(b || b2));
        System.out.println("需要调整的次数："+count);
        List<Integer> c = new ArrayList<Integer>();
        List<Integer> j = new ArrayList<Integer>();
        if(b2){
            c = error2CList;
            j = error2JList;
        }else if(b){
            c = errorCList;
            j = errorJList;
        }
        for (int i = 0; i < c.size(); i++) {
            System.out.println("交换"+c.get(i)+"与"+j.get(i)+"的位置");
            swap(c.get(i),j.get(i));
        }
        System.out.println("互换之后：");
        print();
    }
}
