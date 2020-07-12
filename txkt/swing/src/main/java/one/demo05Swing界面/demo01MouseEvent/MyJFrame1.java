package one.demo05Swing界面.demo01MouseEvent;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyJFrame1 extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame1(){
        final JLabel jLabel = new JLabel("我来显示");

        jp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jLabel.setText("鼠标点击了");
            }
        });

        jp.add(jLabel);
        add(jp);
        setSize(900,600);
        setLocation(200,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyJFrame1();
    }
}
