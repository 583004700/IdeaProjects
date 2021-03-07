package one._01_javase_code.singleton_code.com.atguigu.single;

public class Singleton4 {
	private static Singleton4 instance;
	private Singleton4(){
		
	}
	public static Singleton4 getInstance(){
		if(instance == null){
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			instance = new Singleton4();
		}
		return instance;
	}
}
