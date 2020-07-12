package two.src45;

import javax.swing.JFrame;

public class Demo09 {
	public static void main(String[] args) {
		NewFrame09 newFrame = new NewFrame09();
		newFrame.setSize(800, 400);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("表格组件");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true);
	}
}
