package one._01_javase_code.step_code.com.atguigu.step;

import org.junit.Test;

public class TestStep2 {
	@Test
	public void test(){
		long start = System.currentTimeMillis();
		System.out.println(loop(100));//165580141
		long end = System.currentTimeMillis();
		System.out.println(end-start);//<1ms
	}
	
	public int loop(int n){
		if(n<1){
			throw new IllegalArgumentException(n + "");
		}
		if(n==1 || n==2){
			return n;
		}
		
		int one = 2;
		int two = 1;
		int sum = 0;
		
		for(int i=3; i<=n; i++){
			sum = two + one;
			two = one;
			one = sum;
		}
		return sum;
	}
}
