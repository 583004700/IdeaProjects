package one.demo05Swing界面.demo02Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame2 extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame2(){
        JCheckBox jc1 = new JCheckBox("吃饭");
        JCheckBox jc2 = new JCheckBox("睡觉",true);
        JCheckBox jc3 = new JCheckBox("打豆豆");

        final JLabel jLabel = new JLabel("我来显示");
        JButton jb = new JButton("选择");
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        jp.add(jc1);
        jp.add(jc2);
        jp.add(jc3);
        jp.add(jb);
        jp.add(jLabel);
        add(jp);
        setSize(900,600);
        setLocation(200,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyJFrame2();
    }
}
