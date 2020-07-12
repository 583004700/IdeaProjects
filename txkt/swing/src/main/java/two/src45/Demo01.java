package two.src45;

import javax.swing.JFrame;

public class Demo01 {

	public static void main(String[] args) {
		NewFrame01 newFrame = new NewFrame01();
		newFrame.setSize(600, 400);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("标签组件");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true); 

	}

}
