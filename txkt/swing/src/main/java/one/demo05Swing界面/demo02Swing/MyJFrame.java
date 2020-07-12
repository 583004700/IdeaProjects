package one.demo05Swing界面.demo02Swing;

import javax.swing.*;

public class MyJFrame extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame(){
        JTextArea ta = new JTextArea(20,10);
        //设置tab键缩进字符数量
//        ta.setTabSize(2);
        //设置是否自动换行
//        ta.setLineWrap(true);
        //滚动条
        JScrollPane js = new JScrollPane(ta);
        jp.add(js);

        add(jp);
        setSize(900,600);
        setLocation(200,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyJFrame();
    }
}
