package two.src45;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NewFrame03 extends JFrame{
	
	JTextArea txaSrc;
	JTextArea txaDest;
	JButton btnAppend;
	JButton btnInsert;
	JButton btnSelectAll;
	JButton btnCopy;
	JButton btnPaste;
	JButton btnUndo;
	JButton btnRedo;
	UndoManager undoManager;
	JScrollPane jspSrc;
	public NewFrame03(){
		init();
	}
	private void init( ){
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);
		undoManager = new UndoManager();
		
		txaSrc = new JTextArea();
		txaSrc.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		txaSrc.setLineWrap(true);
		txaSrc.getDocument().addUndoableEditListener(undoManager);
		txaSrc.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				//实现撤销操作的快捷键
				if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Z){
					if(undoManager.canUndo()){
						undoManager.undo();
					}
				}
				//实现重做操作的快捷键
				if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Y){
					if(undoManager.canRedo()){
						undoManager.redo();
					}
				}
			}
		});
		
		jspSrc = new JScrollPane(txaSrc);
		jspSrc.setSize(300, 150);
		jspSrc.setLocation(80, 0);
		
		txaDest = new JTextArea();
		txaDest.setSize(300, 150);
		txaDest.setLocation(420, 0);
		txaDest.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		txaDest.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
		
		btnAppend = new JButton("添加");
		btnAppend.setSize(80, 40);
		btnAppend.setLocation(60, 200);
		btnAppend.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnAppend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txaSrc.append("ab");
				
			}
		});
		
		btnInsert = new JButton("插入");
		btnInsert.setSize(80, 40);
		btnInsert.setLocation(160, 200);
		btnInsert.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txaSrc.insert("c", 2);
				
			}
		});
		
		btnSelectAll = new JButton("全选");
		btnSelectAll.setSize(80, 40);
		btnSelectAll.setLocation(260, 200);
		btnSelectAll.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnSelectAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txaSrc.requestFocus();
				txaSrc.selectAll();
				
			}
		});
		
		btnCopy = new JButton("复制");
		btnCopy.setSize(80, 40);
		btnCopy.setLocation(360, 200);
		btnCopy.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnCopy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txaSrc.copy();
				
			}
		});
		
		btnPaste = new JButton("粘贴");
		btnPaste.setSize(80, 40);
		btnPaste.setLocation(460, 200);
		btnPaste.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnPaste.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txaDest.paste();
				
			}
		});
		
		btnUndo = new JButton("撤销");
		btnUndo.setSize(80, 40);
		btnUndo.setLocation(560, 200);
		btnUndo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnUndo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					if(undoManager.canUndo()){
						undoManager.undo();
					}				
			}
		});
		
		btnRedo = new JButton("重做");
		btnRedo.setSize(80, 40);
		btnRedo.setLocation(660, 200);
		btnRedo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnRedo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(undoManager.canRedo()){
					undoManager.redo();
				}	
				
			}
		});

		container.add(jspSrc);
		container.add(txaDest);
		container.add(btnAppend);
		container.add(btnInsert);
		container.add(btnCopy);
		container.add(btnPaste);
		container.add(btnUndo);
		container.add(btnSelectAll);
		container.add(btnRedo);
	}
}
