package dataStructure.com.atguigu.recursion;

public class Recursion {
    public static void main(String[] args) {
        test(4);
        long res = factorial(4);
        System.out.println(res);
    }

    public static void test(int n){
        if(n > 2){
            test(n-1);
        }
        System.out.println("n=" + n);
    }

    public static int factorial(int n){
        if(n == 1){
            return 1;
        }else{
            return factorial(n-1)*n;
        }
    }
}
