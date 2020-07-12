package one.demo03Component;

import javax.swing.*;
import java.awt.*;

public class MyJFrame extends JFrame {
    private JPanel jp = new JPanel();
    Icon icon = new ImageIcon("swing/src/main/resources/qq.png");

    public MyJFrame(){
        //按钮
        JButton bt = new JButton();
        //bt.setText("我是动态文字");
        bt.setIcon(icon);
        jp.add(bt);

        //文本框
        JTextField jtf = new JTextField();
        jp.add(jtf);
        //设置文本框宽度
        jtf.setColumns(10);
        //设置不可编辑
        jtf.setEditable(false);
        jtf.setText("默认文字");
        //设置不可用，不可复制
        jtf.setEnabled(false);
        Font font = new Font("simsun",Font.BOLD,30);
        jtf.setFont(font);

        //标签
        JLabel jLabel = new JLabel();
        //先修改背景不透明，再设置背景颜色
        //jLabel.setOpaque(true);
        //设置背景颜色
        jLabel.setBackground(Color.RED);
        jLabel.setIcon(icon);



        jp.add(jLabel);

        add(jp);
        setSize(600,400);
        setLocation(500,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MyJFrame jFrame = new MyJFrame();

    }
}
