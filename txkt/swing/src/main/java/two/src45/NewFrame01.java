package two.src45;

import javax.swing.*;
import java.awt.*;
public class NewFrame01 extends JFrame{
	JLabel label1;
	JLabel label2;
	public NewFrame01(){
		init();
	}
	private void init( ){
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);
		
		label1 = new JLabel("你好");
		label1.setSize(200, 100);
		label1.setLocation(0, 0);
		label1.setFont(new Font("隶书", Font.ITALIC, 40));
		
		label2 = new JLabel();
		label2.setSize(300,300);
		label2.setLocation(220, 0);
		label2.setIcon(new ImageIcon("logo.png"));
		label2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label2.setToolTipText("这是一个Tip");
		container.add(label1);
		container.add(label2);
	}
}










