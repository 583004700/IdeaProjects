package one.demo01Swing;

import javax.swing.*;

/**
 *
 */
public class Demo {
    public static void main(String[] args) {
        //创建窗口
        JFrame jFrame = new JFrame();
        jFrame.setSize(400,400);
        jFrame.setLocation(100,100);
        //设置关闭方式
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
