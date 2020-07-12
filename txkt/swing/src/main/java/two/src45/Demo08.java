package two.src45;

import javax.swing.JFrame;

public class Demo08 {
	public static void main(String[] args) {
		NewFrame08 newFrame = new NewFrame08();
		newFrame.setSize(600, 400);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("组合框组件");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true); 
	}
}
