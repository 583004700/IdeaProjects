package two.src45;

import javax.swing.*;
import java.awt.*;

public class NewFrame11 extends JFrame{

	JTabbedPane tbpMain;
	JButton btn1,btn2;
	JPanel jpn1;
		public NewFrame11(){
		init();
	}
	private void init( ){
		Container container = this.getContentPane();//获得窗体的主体区域
		//container.setLayout(null);
		this.setResizable(false);
		jpn1 = new JPanel(null);
		tbpMain=new JTabbedPane();
		btn1=new JButton("按钮1");
		btn2=new JButton("按钮2");
		btn2.setSize(100,30);
		btn2.setLocation(50, 50);
		jpn1.add(btn2);
		btn1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btn2.setFont(new Font("微软雅黑", Font.PLAIN, 20));

		tbpMain.addTab("选项卡一", btn1);
		tbpMain.addTab("选项卡二", jpn1);
		
		container.add(tbpMain);
	}
}
