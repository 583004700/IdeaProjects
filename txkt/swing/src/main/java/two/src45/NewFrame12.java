package two.src45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFrame12 extends JFrame{

	JButton[] buttons;
	JLabel lblResult;
	public NewFrame12(){
		init();
	}
	private void init( ){
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);
		buttons = new JButton[5];
		buttons[0] = new JButton("消息对话框");
		buttons[1] = new JButton("确认对话框");
		buttons[2] = new JButton("输入对话框");
		buttons[3] = new JButton("内部信息对话框");
		buttons[4] = new JButton("自定义对话框");
		
		lblResult = new JLabel("结果");
		
		for(int i=0;i<buttons.length;i++){
			buttons[i].setSize(200, 40);
			buttons[i].setLocation(50, i*60+20);
			buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 20));
			container.add(buttons[i]);
		}
		
		lblResult.setSize(200, 40);
		lblResult.setLocation(50, 310);
		lblResult.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(NewFrame12.this, "您的会员资格将于3天后到期",
					"会员到期提醒",JOptionPane.WARNING_MESSAGE,new ImageIcon("a.png"));
				
			}
		});
		
		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				int option = JOptionPane.showConfirmDialog(NewFrame12.this, "在关闭前是否保存文档？",
//						"关闭文档提示",JOptionPane.YES_NO_CANCEL_OPTION);
				int option = JOptionPane.showOptionDialog(NewFrame12.this, "在关闭前是否保存文档", 
						"关闭文档提示", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, new String[]{"保存","不保存","取消"}, null);
				lblResult.setText("结果 "+option);
			}
		});
		
		buttons[2].addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Object input = JOptionPane.showInputDialog(NewFrame12.this,"请问你想要什么礼物", "选择 礼物", JOptionPane.QUESTION_MESSAGE, 
						null, new String[]{"iPhone","iPad","icecream"},"iPad");
				lblResult.setText("结果 "+input);   
			}
		});
		
		buttons[3].addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInternalMessageDialog(container, "消息对话框");
			}
		});
		
		buttons[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyDialog myDialog = new MyDialog(NewFrame12.this,"自定义对话框");
				String input = myDialog.getValue();
				lblResult.setText("结果 "+input);
			}
		});

		container.add(lblResult);
	}
}












