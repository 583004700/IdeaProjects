package one.demo05Swing界面.demo04Dialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame(){
        JPasswordField jPasswordField = new JPasswordField(10);
        JButton jb = new JButton("获取");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] cs = jPasswordField.getPassword();
                System.out.println(cs);
            }
        });

        jp.add(jPasswordField);
        jp.add(jb);
        add(jp);
        setSize(900,600);
        setLocation(200,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyJFrame();
    }
}
