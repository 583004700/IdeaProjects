package two.src45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyFrame extends JFrame {
	  JButton button1;
	  JButton button2;

	public MyFrame( ){
		init( );
	}
	
	private void init( ){
		button1 = new JButton("按钮1");
		button2 = new JButton("按钮2");

		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);

		button1.setSize(200, 100);
		button1.setLocation(0, 0);
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//button2.setBackground(new Color(0XFFFFFF));
				button2.setBackground(Color.MAGENTA);
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//button2.setBackground(new Color(0X000000));
				button2.setBackground(Color.BLUE);
				
			}
		});
		button2.setBounds(100, 150, 200, 100);
		
		container.add(button2);
		container.add(button1);
	}
}












