package two.src45;

import javax.swing.JFrame;

public class Demo05 {

	public static void main(String[] args) {
		NewFrame05 newFrame = new NewFrame05();
		newFrame.setSize(600, 400);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("单选按钮组件");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true); 
	}

}
