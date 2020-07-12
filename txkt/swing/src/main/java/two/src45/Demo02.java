package two.src45;

import javax.swing.JFrame;

public class Demo02 {

	public static void main(String[] args) {
		NewFrame02 newFrame = new NewFrame02();
		newFrame.setSize(600, 400);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("文本框及密码框");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true); 
		//newFrame.jTtextField2.requestFocus();
	}

}
