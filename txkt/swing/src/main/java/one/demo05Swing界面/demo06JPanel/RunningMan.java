package one.demo05Swing界面.demo06JPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 自定义面板进行图形编程
 * 最好不好将长时间耗时的线程放在主线程，会导致界面卡死
 */

public class RunningMan extends JFrame {
    private MyPanel jp = new MyPanel();
    private JButton jButton = new JButton("开始");
    private Image img = Toolkit.getDefaultToolkit().createImage("swing/src/main/resources/qq.png");
    int x = 30;
    public RunningMan(){

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                x = 100;
                //重绘，重新把改变之后的内容绘制到面板上
//                jp.repaint();
                new MyThread().start();
            }
        });

        jp.add(jButton);
        add(jp);
        setSize(900,600);
        setLocation(200,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            while(x<850){
                try {
                    Thread.sleep(10);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                x+=10;
                jp.repaint();
            }
        }
    }

    class MyPanel extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.drawImage(img,x,100,this);
        }
    }

    public static void main(String[] args) {
        new RunningMan();
    }
}
