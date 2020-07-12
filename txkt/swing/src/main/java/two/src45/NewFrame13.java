package two.src45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class NewFrame13  extends JFrame{
	JButton btnOpen,btnSave,btnSelect;
	JTextField txtOpen,txtSave,txtSelect;
	
	public NewFrame13(){
		init();
	}
	private void init( ){
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);

		btnOpen = new JButton("打开");
		btnSave = new JButton("保存");
		btnSelect = new JButton("选择");
		txtOpen = new JTextField();
		txtSave = new JTextField();
		txtSelect = new JTextField();
				
		txtOpen.setSize(350, 30);
		txtOpen.setLocation(30, 30);
		txtOpen.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		btnOpen.setSize(80, 30);
		btnOpen.setLocation(400, 30);
		btnOpen.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser("D:/");
				fileChooser.setMultiSelectionEnabled(true);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.addChoosableFileFilter(new MyFilter("txt"));
				fileChooser.addChoosableFileFilter(new MyFilter("jpg"));
				fileChooser.addChoosableFileFilter(new MyFilter("rar"));
				int option = fileChooser.showOpenDialog(NewFrame13.this);
				if(option==JFileChooser.APPROVE_OPTION){
					File[] files = fileChooser.getSelectedFiles();
					String fileNames = "";
					for(int i=0;i<files.length;i++){
						fileNames = fileNames+files[i].getAbsolutePath()+";";
					}
					 
					txtOpen.setText(fileNames);
				}else{
					txtOpen.setText("");
				}
//				File file = fileChooser.getSelectedFile();
//				if(file!=null){
//					String fileName = file.getAbsolutePath();
//					txtOpen.setText(fileName);
//				}else{
//					txtOpen.setText("");
//				}
				
			}
		});
		
		txtSave.setSize(350, 30);
		txtSave.setLocation(30, 90);
		txtSave.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		btnSave.setSize(80, 30);
		btnSave.setLocation(400, 90);
		btnSave.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showSaveDialog(NewFrame13.this);
				
			}
		});
		
		txtSelect.setSize(350, 30);
		txtSelect.setLocation(30, 150);
		txtSelect.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		btnSelect.setSize(80, 30);
		btnSelect.setLocation(400, 150);
		btnSelect.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showDialog(NewFrame13.this, "选择");
				
			}
		});
		
		container.add(btnOpen);
		container.add(btnSave);
		container.add(btnSelect);
		container.add(txtOpen);
		container.add(txtSave);
		container.add(txtSelect);
	}

}
