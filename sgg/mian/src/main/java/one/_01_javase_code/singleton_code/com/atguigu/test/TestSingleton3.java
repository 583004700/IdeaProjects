package one._01_javase_code.singleton_code.com.atguigu.test;

import one._01_javase_code.singleton_code.com.atguigu.single.Singleton3;

public class TestSingleton3 {

	public static void main(String[] args) {
		Singleton3 s = Singleton3.INSTANCE;
		System.out.println(s);
	}

}
