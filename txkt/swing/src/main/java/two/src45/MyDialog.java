package two.src45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyDialog extends JDialog{
	JTextField txtUserName;
	JPasswordField pwdPassword;
	JButton btnOk,btnCancel;
	JLabel lblUserName, lblPassword;
	String value = null;
	
	public MyDialog(Frame owner, String title){
		super(owner, title);
		init();
	}
	private void init( ){
		Container container = this.getContentPane();
		container.setLayout(null);
		
		txtUserName = new JTextField();
		pwdPassword = new JPasswordField();
		btnOk = new JButton("确定");
		btnCancel = new JButton("取消");
		lblUserName = new JLabel("用户名");
		lblPassword = new JLabel("密  码");

		this.setSize(300,200);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		
		
		lblUserName.setSize(80, 30);
		lblUserName.setLocation(30, 20);
		lblUserName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		txtUserName.setSize(140, 30);
		txtUserName.setLocation(110, 20);
		txtUserName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		lblPassword.setSize(80, 30);
		lblPassword.setLocation(30, 60);
		lblPassword.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		pwdPassword.setSize(140, 30);
		pwdPassword.setLocation(110, 60);
		pwdPassword.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		btnOk.setSize(80, 30);
		btnOk.setLocation(50, 100);
		btnOk.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				value = txtUserName.getText()+"  "+new String(pwdPassword.getPassword());
				MyDialog.this.dispose();
			}
		});
		
		btnCancel.setSize(80, 30);
		btnCancel.setLocation(150, 100);
		btnCancel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MyDialog.this.dispose();
				
			}
		});
		
		container.add(txtUserName);
		container.add(pwdPassword);
		container.add(btnOk);
		container.add(btnCancel);
		container.add(lblUserName);
		container.add(lblPassword);
		this.setVisible(true);
	}
	
	public String getValue(){
		
		return value;
	}

}












