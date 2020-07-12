package two.src45;

import javax.swing.JFrame;

public class Demo10 {

	public static void main(String[] args) {
		NewFrame10 newFrame = new NewFrame10();
		newFrame.setSize(800, 400);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("树形组件");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true);
	}

}
