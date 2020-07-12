package one._01_javase_code.singleton_code.com.atguigu.test;

import one._01_javase_code.singleton_code.com.atguigu.single.Singleton2;

public class TestSingleton2 {

	public static void main(String[] args) {
		Singleton2 s = Singleton2.INSTANCE;
		System.out.println(s);
	}

}
