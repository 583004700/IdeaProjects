package two.src45;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button1ActionListener implements ActionListener{
	MyFrame myFrame;
	int count = 0;
	Button1ActionListener(MyFrame myFrame){
		this.myFrame = myFrame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		count++;
		myFrame.button2.setText("button1被点击了"+count+"次");
	}

}
