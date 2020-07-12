package two.src45;

import javax.swing.JFrame;

public class Demo12 {
	public static void main(String[] args) {
		NewFrame12 newFrame = new NewFrame12();
		newFrame.setSize(310, 400);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("对话框组件");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true);
	}
}
