package two.src45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFrame05 extends JFrame{

	JButton btnOK;
	JLabel lblSex;
	JRadioButton rbtMale;
	JRadioButton rbtFemale;
	ButtonGroup btgSex;
	
	public NewFrame05() { 
		init();
	}

	private void init() {
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);

		lblSex = new JLabel("请选择性别");
		rbtMale = new JRadioButton("男",true);
		rbtFemale = new JRadioButton("女",false);
		btnOK = new JButton("确定");
		btgSex = new ButtonGroup();

		lblSex.setSize(200,40);
		lblSex.setLocation(30, 30);
		lblSex.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		rbtMale.setSize(70,40);
		rbtMale.setLocation(30, 70);
		rbtMale.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		rbtMale.setSelected(true);
		rbtMale.setActionCommand("男");
		
		rbtFemale.setSize(70,40);
		rbtFemale.setLocation(100, 70);
		rbtFemale.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		rbtFemale.setActionCommand("女");

		btnOK.setSize(80, 40);
		btnOK.setLocation(30, 130);
		btnOK.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				if(rbtMale.isSelected()){
//					System.out.println("你选择了"+rbtMale.getText());
//				}else {
//					System.out.println("你选择了"+rbtFemale.getText());
//				}
				System.out.println("你选择了："+btgSex.getSelection().getActionCommand());
			}
		});
				
		container.add(lblSex);
		container.add(btnOK);
		container.add(rbtMale);
		container.add(rbtFemale);
		btgSex.add(rbtMale);
		btgSex.add(rbtFemale);
		
	}
}
