package one._01_javase_code.singleton_code.com.atguigu.test;

import one._01_javase_code.singleton_code.com.atguigu.single.Singleton1;

public class TestSingleton1 {

	public static void main(String[] args) {
		Singleton1 s = Singleton1.INSTANCE;
		System.out.println(s);
	}

}
