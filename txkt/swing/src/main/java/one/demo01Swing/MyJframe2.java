package one.demo01Swing;

import javax.swing.*;
import java.awt.*;

/**
 * JFrame.EXIT_ON_CLOSE  关闭时退出程序
 * JFrame.DISPOSE_ON_CLOSE  关闭时销毁窗口，如果有其它线程在运行，不退出程序
 * JFrame.HIDE_ON_CLOSE  关闭时隐藏窗口
 * JFrame.DO_NOTHING_ON_CLOSE  什么也不做
 *
 */

public class MyJframe2 extends JFrame {
    public MyJframe2(){
        //this.setSize(400,400);
        this.setSize(new Dimension(400,400));
        //this.setLocation(200,100);
        this.setLocation(new Point(200,100));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MyJframe2();
    }
}
