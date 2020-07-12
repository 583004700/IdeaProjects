package one.demo01Swing;

import javax.swing.*;

public class MyJframe1 extends JFrame {
    public MyJframe1(){
        this.setSize(400,400);
        this.setLocation(200,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MyJframe1();
    }
}
