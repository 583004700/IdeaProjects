package mysources.dac;

import java.util.Stack;

public class HanoitowerNoRecursion {

	public static void main(String[] args) {
		new HanoitowerNoRecursion().hanoiTower(5, 'A', 'B', 'C');
	}
	
	//汉诺塔的移动的方法
	//使用分治算法

	public static Stack<Param> paramStack = new Stack<Param>();

	class Param{
		public int num;
		public char a;
		public char b;
		public char c;

		public Param(int num, char a, char b, char c) {
			this.num = num;
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
	
	public void hanoiTower(int num, char a, char b, char c) {
		Param param = new Param(num,a,b,c);
		paramStack.push(param);

		while(!paramStack.empty()) {
			while (true) {
				//如果只有一个盘
				if (param.num == 1) {
					//唯一的出口
					param = paramStack.pop();
					System.out.println("第1个盘从 " + param.a + "->" + param.c);
					if(paramStack.isEmpty()){
						return;
					}
					param = paramStack.pop();
					break;
				} else {
					//如果我们有 n >= 2 情况，我们总是可以看做是两个盘 1.最下边的一个盘 2. 上面的所有盘
					//1. 先把 最上面的所有盘 A->B， 移动过程会使用到 c
					//hanoiTower(num - 1, a, c, b);
					param = new Param(param.num-1, param.a, param.c, param.b);
					paramStack.push(param);
				}
			}
			//2. 把最下边的盘 A->C
			System.out.println("第" + param.num + "个盘从 " + param.a + "->" + param.c);
			//3. 把B塔的所有盘 从 B->C , 移动过程使用到 a塔
			//hanoiTower(num - 1, b, a, c);
			param = new Param(param.num - 1, param.b, param.a, param.c);
			paramStack.push(param);
		}
	}

}
