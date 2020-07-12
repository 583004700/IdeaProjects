package one.demo04Event;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame1 extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame1(){
        final JTextField jtf = new JTextField(10);
        JButton jButton = new JButton("获取");
        final JLabel jLabel = new JLabel("我来显示");

        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = jtf.getText();
                jLabel.setText(text);
            }
        });


        jp.add(jtf);
        jp.add(jButton);
        jp.add(jLabel);
        add(jp);
        setSize(600,400);
        setLocation(500,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MyJFrame1 myJFrame1 = new MyJFrame1();

    }
}
