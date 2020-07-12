package one.demo05Swing界面.demo02Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame1 extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame1(){
        //单选按钮
        JRadioButton jr1 = new JRadioButton("男");
        JRadioButton jr2 = new JRadioButton("女");
        //放到一个单选按钮组里面实现单选
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jr1);
        buttonGroup.add(jr2);

        final JRadioButton jr3 = new JRadioButton("中国");
        final JRadioButton jr4 = new JRadioButton("岛国");
        //放到一个单选按钮组里面实现单选
        ButtonGroup buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(jr3);
        buttonGroup2.add(jr4);

        final JLabel jLabel = new JLabel("我来显示");
        JButton jb = new JButton("选择");
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(jr3.isSelected()){
                    jLabel.setText("你的国籍是"+jr3.getText());
                }else if(jr4.isSelected()){
                    jLabel.setText("你的国籍是"+jr4.getText());
                }else{
                    jLabel.setText("没有选择国籍");
                }
            }
        });

        jp.add(jr1);
        jp.add(jr2);
        jp.add(jr3);
        jp.add(jr4);
        jp.add(jb);
        jp.add(jLabel);
        add(jp);
        setSize(900,600);
        setLocation(200,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyJFrame1();
    }
}
