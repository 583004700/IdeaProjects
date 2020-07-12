package one.demo01Swing;

import javax.swing.*;

/**
 * JFrame.EXIT_ON_CLOSE  关闭时退出程序
 * JFrame.DISPOSE_ON_CLOSE  关闭时销毁窗口，如果有其它线程在运行，不退出程序
 * JFrame.HIDE_ON_CLOSE  关闭时隐藏窗口
 * JFrame.DO_NOTHING_ON_CLOSE  什么也不做
 *
 */

public class MyJframe3 extends JFrame {
    public MyJframe3(){
        // 面板    上面可以放组件（控件）
        JPanel jp = new JPanel();

        //jp.setBackground(Color.PINK);
        JButton button = new JButton("我不是按钮");
        JTextField jtf = new JTextField(20);


        jp.add(button);
        jp.add(jtf);

        this.add(jp);

        this.setSize(400,400);
        this.setLocation(200,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        new MyJframe3();
    }
}
