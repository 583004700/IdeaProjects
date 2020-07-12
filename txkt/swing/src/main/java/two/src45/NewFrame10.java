package two.src45;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFrame10 extends JFrame{
	JTree familyTree;
	JScrollPane jspFamily;
	
	JTree dynTree;
	JScrollPane jspDyn;
	DefaultMutableTreeNode treeRoot ,temp;
	JTextField txtName;
	JButton btnAdd,btnRename,btnDelete;
	TreePath path;
	public NewFrame10(){
		init();
	}
	private void init( ){
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);
		
		treeRoot = new DefaultMutableTreeNode("根");
		dynTree = new JTree(treeRoot);
		jspDyn = new JScrollPane(dynTree);
		txtName = new JTextField();
		btnAdd = new JButton("新增");
		btnRename = new JButton("修改");
		btnDelete = new JButton("删除");
		
		dynTree.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		dynTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		dynTree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				path = e.getPath();
				
			}
		});
		
		jspDyn.setSize(350, 250);
		jspDyn.setLocation(400, 30);
		
		txtName.setSize(80, 30);
		txtName.setLocation(400, 300);
		txtName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		btnAdd.setSize(80, 30);
		btnAdd.setLocation(490, 300);
		btnAdd.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				temp = (DefaultMutableTreeNode)dynTree.getLastSelectedPathComponent();
				String name = txtName.getText();
				if(validate(temp,name)==true){
					temp.add(new DefaultMutableTreeNode(name));
					dynTree.expandPath(path);
					dynTree.updateUI();
				}
				
			}
		});
		
		btnRename.setSize(80, 30);
		btnRename.setLocation(580, 300);
		btnRename.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnRename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				temp = (DefaultMutableTreeNode)dynTree.getLastSelectedPathComponent();
				String name = txtName.getText();
				if(validate(temp,name)==true){
					temp.setUserObject(name);
					dynTree.updateUI();
				}
				
			}
		});

		btnDelete.setSize(80, 30);
		btnDelete.setLocation(670, 300);
		btnDelete.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				temp = (DefaultMutableTreeNode)dynTree.getLastSelectedPathComponent();
				if(temp==null){
					System.out.println("请选中一个节点！");
					return;
				}
				if(temp.isRoot()==true){
					System.out.println("不能删除根节点！");
					return;
				}
				temp.removeFromParent();
				dynTree.updateUI();
			}
		});
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("曹操");
		DefaultMutableTreeNode a1 = new DefaultMutableTreeNode("曹昂");
		DefaultMutableTreeNode a2 = new DefaultMutableTreeNode("曹丕");
		DefaultMutableTreeNode a3 = new DefaultMutableTreeNode("曹植");
		DefaultMutableTreeNode a4 = new DefaultMutableTreeNode("曹彰");
		root.add(a1);
		root.add(a2);
		root.add(a3);
		root.add(a4);
		DefaultMutableTreeNode b1 = new DefaultMutableTreeNode("曹睿");
		DefaultMutableTreeNode b2 = new DefaultMutableTreeNode("曹霖");
		DefaultMutableTreeNode b3 = new DefaultMutableTreeNode("曹志");
		a2.add(b1);
		a2.add(b2);
		a3.add(b3);
		DefaultMutableTreeNode c1 = new DefaultMutableTreeNode("曹芳");
		b1.add(c1);
		
		familyTree = new JTree(root);
		familyTree.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		jspFamily = new JScrollPane(familyTree);
		jspFamily.setSize(300, 250);
		jspFamily.setLocation(30, 30);
		
		container.add(jspFamily);
		container.add(jspDyn);
		container.add(txtName);
		container.add(btnAdd);
		container.add(btnRename);
		container.add(btnDelete);

	}
	
	boolean validate(DefaultMutableTreeNode node,String name){
		if(node==null){
			System.out.println("请选择一个节点！");
			return false;
		}
		 name = txtName.getText();
		if(name.equals("")){
			System.out.println("请填写节点的名称");
			return false;
		}
		return true;
	}
}














