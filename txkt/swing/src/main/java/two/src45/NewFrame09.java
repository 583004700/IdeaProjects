package two.src45;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFrame09 extends JFrame {
	JTable tblData;//表格
	JScrollPane jspData;//滚动面板
	JLabel lblNum, lblName,lblSex, lblAge;//标签
	JTextField txtNum,txtName,txtAge;//文本框
	JComboBox<String> jcbSex;//组合框
	JButton btnAdd,btnUpdate,btnDelete,btnSelectAll,btnDeselectAll;//按钮
	DefaultTableModel dm;
	Object[][] data;
	Object[] columns;
	
	public NewFrame09() {
		init();
	}

	private void init() {
		Container container = this.getContentPane();// 获得窗体的主体区域
		container.setLayout(null);
		
		//创建组件对象
		data = new Object[][]{
			{new Boolean(false),"1","张三","男","16"},
			{new Boolean(false),"2","李四","男","17"},
			{new Boolean(false),"3","王小花","女","16"}
		};
		columns  = new Object[]{"删除选择","学号","姓名","性别","年龄"};
		dm = new DefaultTableModel(data, columns){
			public Class getColumnClass(int c){
				if(c==0){
					return Boolean.class;
				}else{
					return Object.class;
				}
			}
			
		};
		tblData = new JTable(dm){
			public boolean isCellEditable(int row ,int column){
				if(column==0){
					return true;
				}else{
					return false;
				}
			}
		};//创建表格
		jspData = new JScrollPane(tblData);
		
		lblNum = new JLabel("学号");
		lblName = new JLabel("姓名");
		lblSex = new JLabel("性别");
		lblAge = new JLabel("年龄");
		
		txtNum = new JTextField();
		txtName = new JTextField();
		jcbSex = new JComboBox<String>();
		txtAge = new JTextField();
		
		btnAdd = new JButton("新增");
		btnUpdate = new JButton("修改");
		btnDelete = new JButton("删除");
		btnSelectAll = new JButton("全选");
		btnDeselectAll = new JButton("全不选");
		
		
		
		//初始化各组件基础属性
		jspData.setSize(600, 150);
		jspData.setLocation(100, 30);
		
		tblData.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		tblData.setRowHeight(25);
		tblData.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 18));
		tblData.getColumnModel().getColumn(0).setPreferredWidth(150);
		tblData.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblData.getColumnModel().getColumn(2).setPreferredWidth(150);
		tblData.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblData.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		tblData.addMouseListener(new MouseAdapter(){
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				System.out.println("-----");
//				int row = tblData.getSelectedRow();
//				String num = (String)tblData.getValueAt(row, 0);
//				txtNum.setText(num);
//			}
//		});
		tblData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()==true){
					int row = tblData.getSelectedRow();
					String num = (String)tblData.getValueAt(row, 1);
					String name = (String)tblData.getValueAt(row,2);
					String sex = (String)tblData.getValueAt(row, 3);
					String age = (String)tblData.getValueAt(row, 4);
					txtNum.setText(num);
					txtName.setText(name);
					txtAge.setText(age);
					jcbSex.setSelectedItem(sex);
				}
				
				
			}
		});
	
		lblNum.setSize(50,30);
		lblNum.setLocation(100, 200);
		lblNum.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		txtNum.setSize(80,30);
		txtNum.setLocation(150, 200);
		txtNum.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		lblName.setSize(50,30);
		lblName.setLocation(250, 200);
		lblName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		txtName.setSize(80,30);
		txtName.setLocation(300, 200);
		txtName.setFont(new Font("微软雅黑", Font.PLAIN, 20));

		lblSex.setSize(50,30);
		lblSex.setLocation(400, 200);
		lblSex.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		jcbSex.setSize(80,30);
		jcbSex.setLocation(450, 200);
		jcbSex.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		jcbSex.addItem("男");
		jcbSex.addItem("女");
		
		lblAge.setSize(50,30);
		lblAge.setLocation(550, 200);
		lblAge.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		txtAge.setSize(80,30);
		txtAge.setLocation(600, 200);
		txtAge.setFont(new Font("微软雅黑", Font.PLAIN, 20));

		btnAdd.setSize(80,30);
		btnAdd.setLocation(100, 250);
		btnAdd.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String num = txtNum.getText();
				String name = txtName.getText();
				String sex =(String) jcbSex.getSelectedItem();
				String age = txtAge.getText();
				
				boolean flag = validateAge(age);
				if(flag == true){
					dm.addRow(new Object[]{new Boolean(false),num,name,sex,age});
				}else{
					System.out.println("年龄值错误！");
				}
			}
		});
			
		btnUpdate.setSize(80,30);
		btnUpdate.setLocation(200, 250);
		btnUpdate.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tblData.getSelectedRow();
				if(row==-1){
					System.out.println("请选择数据！");
					return;
				}
				String num = txtNum.getText();
				String name = txtName.getText();
				String sex =(String) jcbSex.getSelectedItem();
				String age = txtAge.getText();
				if(validateAge(age)==false){
					System.out.println("年龄值错误！");
					return;
				}
				tblData.setValueAt(num, row, 1);
				tblData.setValueAt(name, row, 2);
				tblData.setValueAt(sex, row, 3);
				tblData.setValueAt(age, row, 4);
				
			}
		});
		
		btnDelete.setSize(80,30);
		btnDelete.setLocation(300, 250);
		btnDelete.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validateSelection()==true){
					for(int i=dm.getRowCount()-1;i>=0;i--){
						if(dm.getValueAt(i, 0).equals(Boolean.TRUE)){
							dm.removeRow(i);
						}
					}
				}else{
					System.out.println("请至少选择一条数据！");
				}
				
				
			}
		});
			
		btnSelectAll.setSize(80,30);
		btnSelectAll.setLocation(400, 250);
		btnSelectAll.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnSelectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<dm.getRowCount();i++){
					dm.setValueAt(new Boolean(true), i, 0);
				}
				
			}
		});
		
		btnDeselectAll.setSize(100,30);
		btnDeselectAll.setLocation(500, 250);
		btnDeselectAll.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnDeselectAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<dm.getRowCount();i++){
					dm.setValueAt(new Boolean(false), i, 0);
				}
				
			}
		});
		
		
		//把组件添加到窗体中
		container.add(jspData);
		container.add(lblNum);
		container.add(lblName);
		container.add(lblSex);
		container.add(lblAge);
		container.add(txtNum);
		container.add(txtName);
		container.add(jcbSex);
		container.add(txtAge);
		container.add(btnAdd);
		container.add(btnUpdate);
		container.add(btnDelete);
		container.add(btnSelectAll);
		container.add(btnDeselectAll);
	}
	boolean validateAge(String age){
		//不能为空
		// 只能出现数字
		//数字范围在10-20之间
		if(age==null || age.equals("")){
			return false;
		}
		for(int i=0;i<age.length();i++){
			int chr = age.charAt(i);
			if(chr<48 || chr>57){
				return false;
			}
		}
		int intAge = Integer.parseInt(age);//把字符串转换为整数
		if(intAge<10||intAge>20){
			return false;
		}
		return true;
	}
	
	boolean validateSelection(){
		boolean selection = false;
		for(int i=0;i<dm.getRowCount();i++){
			if(dm.getValueAt(i, 0).equals(Boolean.TRUE)){
				selection = true;
				break;
			}
		}
		return selection;
	}
}
















