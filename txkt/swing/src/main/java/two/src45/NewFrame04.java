package two.src45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class NewFrame04 extends JFrame {
	
	JCheckBox cbxConfirm;
	JButton btnNext;
	String[] names;
	JCheckBox[] cbxCities;
	JButton btnOK;
	JLabel lblCities;
	
	public NewFrame04() {
		init();
	}

	private void init() {
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);
		
		cbxConfirm = new JCheckBox("我已阅读并接受条款");
		btnNext = new JButton("下一步");
		lblCities = new JLabel("选择你去过的城市");
		names = new String[]{"北京","上海","广州","深圳"};
		cbxCities = new JCheckBox[names.length];
		btnOK = new JButton("提交");

		cbxConfirm.setSize(250, 40);
		cbxConfirm.setLocation(30, 30);
		cbxConfirm.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//		cbxConfirm.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				boolean flag = cbxConfirm.isSelected();
//				btnNext.setEnabled(flag);
//			}
//		});
		cbxConfirm.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean flag = cbxConfirm.isSelected();
				btnNext.setEnabled(flag);
				
			}
		});
		
		btnNext.setSize(100, 40);
		btnNext.setLocation(30, 80);
		btnNext.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnNext.setEnabled(false);
		
		lblCities.setSize(200, 40);
		lblCities.setLocation(30, 150);
		lblCities.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		for(int i=0;i<cbxCities.length;i++){
			cbxCities[i] = new JCheckBox(names[i]);
			cbxCities[i].setSize(70, 40);
			cbxCities[i].setLocation(30+i*70, 200);
			cbxCities[i].setFont(new Font("微软雅黑", Font.PLAIN, 18));
			container.add(cbxCities[i]);
		}
		btnOK.setSize(80, 40);
		btnOK.setLocation(30, 250);
		btnOK.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = "";
				for(int i=0;i<cbxCities.length;i++){
					if(cbxCities[i].isSelected()==true){
						str = str+cbxCities[i].getText()+" ";
					}
				}
				System.out.println(str);
			}
		});

		container.add(cbxConfirm);
		container.add(btnNext);
		container.add(lblCities);
		container.add(btnOK);
	}
}







