package two.src45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NewFrame07 extends JFrame{
	JLabel lblSelect;
	JList<String> lstLeft;
	JList<String> lstRight;
	
	JButton btnSelectAll;
	JButton btnSelect;
	JButton btnCancel;
	JButton btnCancelAll;
	//String[] leftData;
	//Vector<String> leftData;
	DefaultListModel<String> leftData;
	DefaultListModel<String> rightData;
	public NewFrame07() { 
		init();
	}

	private void init() {
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);

		//leftData = new String[]{"关羽","张飞","赵云","马超","黄忠","魏延"};
		leftData = new DefaultListModel<String>();
		rightData = new DefaultListModel<String>();
		initLeftData();
		
		lblSelect = new JLabel("你准备带谁出征？");
		lstLeft = new JList<String>();
		lstLeft.setModel(leftData);
		lstRight = new JList<String>();
		
		btnSelectAll = new JButton("全带");
		btnSelect = new JButton("带走");
		btnCancel = new JButton("撤销");
		btnCancelAll = new JButton("重选");

		lblSelect.setSize(200,40);
		lblSelect.setLocation(30, 30);
		lblSelect.setFont(new Font("微软雅黑", Font.PLAIN, 20));
	
		lstLeft.setSize(200,240);
		lstLeft.setLocation(30, 80);
		lstLeft.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lstLeft.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
		lstLeft.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2){
					int index = lstLeft.getSelectedIndex();
					rightData.addElement(leftData.remove(index));
				}
			}
		});
		
		lstRight.setSize(200,240);
		lstRight.setLocation(350, 80);
		lstRight.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lstRight.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
		
		btnSelectAll.setSize(80,40);
		btnSelectAll.setLocation(250, 90);
		btnSelectAll.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnSelectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = rightData.size();
				for(int i=leftData.size()-1;i>=0;i--){
					rightData.insertElementAt(leftData.remove(i),index);
				}
				lstRight.setModel(rightData);
			}
		});
		
		btnSelect.setSize(80,40);
		btnSelect.setLocation(250, 150);
		btnSelect.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {  
				int[] idx  = lstLeft.getSelectedIndices();
				int index = rightData.size();
				for(int i=idx.length-1;i>=0;i--){
					rightData.insertElementAt(leftData.remove(idx[i]),index);
				}
				lstRight.setModel(rightData);
			}
		});
		
		btnCancel.setSize(80,40);
		btnCancel.setLocation(250, 210);
		btnCancel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {  
				int[] idx  = lstRight.getSelectedIndices();
				int index = leftData.size();
				for(int i=idx.length-1;i>=0;i--){
					leftData.insertElementAt(rightData.remove(idx[i]),index);
				}
				lstLeft.setModel(leftData);
			}
		});
		
		btnCancelAll.setSize(80,40);
		btnCancelAll.setLocation(250, 270);
		btnCancelAll.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnCancelAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rightData.removeAllElements();
				leftData.removeAllElements();
				initLeftData();				
			}
		});
		
		container.add(lblSelect);
		container.add(lstLeft);
		container.add(lstRight);
		container.add(btnSelectAll); 
		container.add(btnSelect);
		container.add(btnCancel);
		container.add(btnCancelAll); 
	}
	private void initLeftData(){
		leftData.addElement("关羽");
		leftData.addElement("张飞");
		leftData.addElement("赵云");
		leftData.addElement("马超");
		leftData.addElement("黄忠");
		leftData.addElement("魏延");
	}
}













