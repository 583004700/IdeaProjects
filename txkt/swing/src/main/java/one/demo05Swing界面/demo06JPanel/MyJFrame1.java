package one.demo05Swing界面.demo06JPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 自定义面板进行图形编程
 */

public class MyJFrame1 extends JFrame {
    private MyPanel jp = new MyPanel();
    private Image img = Toolkit.getDefaultToolkit().createImage("swing/src/main/resources/qq.png");

    public MyJFrame1(){

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
//            g.setColor(Color.RED);
//            g.drawString("aeiou",100,100);
            //参数如果观察者没传，动态图片不会动，因为paint只会执行一次
            //如果传了观察者，paint方法会被多次调用
//            g.drawImage(img,50,50,this);

            g.drawImage(img,50,50,200,200,this);
        }
    }

    public static void main(String[] args) {
        new MyJFrame1();
    }
}
