package two.src45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFrame02 extends JFrame{
	
	JLabel lblUserName;
	JLabel lblPassword;
	JTextField txtUserName;
	JPasswordField pwdPassword;
	JButton btnLogin;
	JButton btnCancel;
	
	public NewFrame02(){
		init();
	}
	private void init( ){
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);
		
		lblUserName = new JLabel("用户名");
		lblPassword = new JLabel("密    码");
		txtUserName = new JTextField();
		pwdPassword = new JPasswordField();
		btnLogin = new JButton("登录");
		btnCancel = new JButton("取消");
		
		lblUserName.setSize(70, 40);
		lblUserName.setLocation(100, 60);
		lblUserName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		lblPassword.setSize(70, 40);
		lblPassword.setLocation(100, 160);
		lblPassword.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		txtUserName.setSize(300, 40);
		txtUserName.setLocation(200, 60);
		txtUserName.setColumns(5);
		txtUserName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pwdPassword.requestFocus();
				
			}
		});

		pwdPassword.setSize(300, 40);
		pwdPassword.setLocation(200, 160);
		pwdPassword.setEchoChar('*');
		pwdPassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLogin.doClick();
				
			}
		});
		
		btnLogin.setSize(150, 40);
		btnLogin.setLocation(130, 240);
		btnLogin.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = txtUserName.getText();
				String password =new String( pwdPassword.getPassword());
				System.out.println("用户名："+userName+" 密码："+password);
				System.out.println("登录中。。。");
			}
		});
		
		btnCancel.setSize(150, 40);
		btnCancel.setLocation(320, 240);
		btnCancel.setFont(new Font("微软雅黑", Font.PLAIN, 20));

		
		container.add(lblUserName);
		container.add(lblPassword);
		container.add(txtUserName);
		container.add(pwdPassword);
		container.add(btnLogin);
		container.add(btnCancel);
	}
}

