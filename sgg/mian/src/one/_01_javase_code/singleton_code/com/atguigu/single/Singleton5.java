package one._01_javase_code.singleton_code.com.atguigu.single;

public class Singleton5 {
	private static Singleton5 instance;
	private Singleton5(){
		
	}
	public static Singleton5 getInstance(){
		if(instance == null){
			synchronized (Singleton5.class) {
				if(instance == null){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					instance = new Singleton5();
				}
			}
		}
		return instance;
	}
}
