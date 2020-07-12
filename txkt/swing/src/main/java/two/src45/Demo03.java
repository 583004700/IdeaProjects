package two.src45;

import javax.swing.JFrame;

public class Demo03 {

	public static void main(String[] args) {
		NewFrame03 newFrame = new NewFrame03();
		newFrame.setSize(800, 400);
		newFrame.setLocationRelativeTo(null);
		newFrame.setTitle("文本区组件");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true); 
		//newFrame.jTtextField2.requestFocus();
	

	}

}
