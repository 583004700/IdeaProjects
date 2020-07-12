package two.src45;

import javax.swing.JFrame;

public class Demo13 {
	public static void main(String[] args) {
		NewFrame13 newFrame = new NewFrame13();
		newFrame.setSize(550, 250);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("文件选择器组件");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true);
	}
}
