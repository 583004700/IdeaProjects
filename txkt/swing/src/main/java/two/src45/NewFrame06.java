package two.src45;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewFrame06 extends JFrame{
	JProgressBar pgbMission;
	JProgressBar pgbIndeterminate;
	JButton btnMission;
	int count = 0;

	public NewFrame06(){
		init();
	}
	private void init( ){
		Container container = this.getContentPane();//获得窗体的主体区域
		container.setLayout(null);
		
		pgbIndeterminate = new JProgressBar();
		pgbMission = new JProgressBar();
		btnMission = new JButton("点击10次");
		pgbMission.setMinimum(0);
		pgbMission.setMaximum(10);
		pgbMission.setStringPainted(true);
//		pgbMission.setString("任务还未完成");
		pgbMission.setValue(0);
		pgbMission.setSize(200, 30);
		pgbMission.setLocation(30, 30);
		pgbMission.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		pgbMission.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("changed");
				if(pgbMission.getValue() == pgbMission.getMaximum()){
					pgbMission.setString("任务完成！");
				}
				
			}
		});
		
		pgbIndeterminate.setSize(200, 30);
		pgbIndeterminate.setLocation(30, 100);
		pgbIndeterminate.setIndeterminate(true);
		
		btnMission.setSize(120, 30);
		btnMission.setLocation(250, 30);
		btnMission.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		btnMission.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pgbMission.setValue(++count);
				
			}
		});
		
		container.add(pgbMission);
		container.add(btnMission);
		container.add(pgbIndeterminate);
	}
}










