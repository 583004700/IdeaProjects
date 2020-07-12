package one.demo03Component;

import javax.swing.*;
import java.awt.*;

public class MyJFrame1 extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame1(){
        GridLayout gl = new GridLayout(1,3);
        JLabel jLabel1 = new JLabel("第一个标签");
        JLabel jLabel2 = new JLabel("第二个标签");
        JLabel jLabel3 = new JLabel("第三个标签");
        jLabel1.setOpaque(true);
        jLabel2.setOpaque(true);
        jLabel3.setOpaque(true);
        jLabel1.setBackground(Color.RED);
        jLabel2.setBackground(Color.GREEN);
        jLabel3.setBackground(Color.BLUE);

        //设置文字对齐方式
        jLabel1.setHorizontalAlignment(JLabel.RIGHT);
        jLabel2.setVerticalAlignment(JLabel.TOP);
        jLabel3.setHorizontalAlignment(JLabel.RIGHT);
        jLabel3.setVerticalAlignment(JLabel.BOTTOM);

        jp.setLayout(gl);
        jp.add(jLabel1);
        jp.add(jLabel2);
        jp.add(jLabel3);

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
