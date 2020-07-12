package com.demo.mydemo.jdk;

public class Main {
    public static void main(String[] args) {
        System.out.println(one(30));
        System.out.println(two(30));
    }

    public static int one(int num){
        int a = 1;
        int b = 1;
        int sum = 0;
        for(int i=3;i<=num;i++){
            sum = a+b;
            a = b;
            b =sum;
        }
        return sum;
    }

    public static int two(int num){
        if(num == 1 || num == 2){
            return 1;
        }else{
            return two(num-1)+two(num-2);
        }
    }
}
