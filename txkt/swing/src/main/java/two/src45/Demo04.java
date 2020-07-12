package two.src45;

import javax.swing.JFrame;

public class Demo04 {
	public static void main(String[] args) {
		NewFrame04 newFrame = new NewFrame04();
		newFrame.setSize(600, 400);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("复选框组件");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true); 
	}
}
