package two.src45;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

//
class MyEditor extends DefaultCellEditor {
	public MyEditor() {
		super(new JTextField());
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// 获得默认表格单元格控件
		JTextField editor = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);

		if (value != null)
			editor.setText(value.toString());
		if (column == 0) {
			// 设置对齐方式
			editor.setHorizontalAlignment(SwingConstants.CENTER);
			editor.setFont(new Font("Serif", Font.BOLD, 18));
		} else {
			editor.setHorizontalAlignment(SwingConstants.RIGHT);
			editor.setFont(new Font("Serif", Font.ITALIC, 18));
		}
		return editor;
	}
}
public class CellEditorDemo extends JFrame {
	DefaultTableModel model = new DefaultTableModel(
			new Object[][] { 
			{ "张三", "16" }, 
			{ "李四", "17" },
			{ "王五", "18" }, 
		},
		new Object[] { "姓名", "年龄" });

	public CellEditorDemo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTable table = new JTable(model);

		// 设置默认编辑对象
		table.setDefaultEditor(Object.class, new MyEditor());
		table.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		table.setRowHeight(25);
		table.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 20));
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		pack();
	}

	public static void main(String arg[]) {
		new CellEditorDemo().setVisible(true);
	}
}


