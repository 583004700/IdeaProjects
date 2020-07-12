package one.demo05Swing界面.demo06JPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 自定义面板进行图形编程
 */

public class MyJFrame extends JFrame {
    private MyPanel jp = new MyPanel();

    public MyJFrame(){
        JButton jButton = new JButton("按钮");
        jp.add(jButton);

        add(jp);
        setSize(900,600);
        setLocation(200,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    class MyPanel extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            //画笔对象
            //画直线
            //g.drawLine(100,100,300,300);
            //画矩形
            g.drawRect(100,100,300,300);
            //画实心矩形
            g.setColor(Color.PINK);
//            g.fillRect(100,100,300,200);
            //画圆角矩形
//            g.drawRoundRect(100,100,300,300,300,300);
//            //画填充矩形
//            g.fillRoundRect(100,100,300,300,300,300);
            //画圆
//            g.drawOval(100,100,300,400);
            //填充圆
            g.fillOval(100,100,300,400);
        }
    }

    public static void main(String[] args) {
        new MyJFrame();
    }
}
