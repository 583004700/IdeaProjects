package one.demo05Swing界面.demo05Dialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJFrame1 extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame1(){
        Icon icon = new ImageIcon("swing/src/main/resources/qq.png");
        JButton jb = new JButton("弹出");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //普通消息对话框 参数1：上下文，在哪一个对象的环境上弹出
                //JOptionPane.showMessageDialog(MyJFrame1.this,"你去死吧");
                //JOptionPane.INFORMATION_MESSAGE普通消息
                //JOptionPane.showMessageDialog(MyJFrame1.this,"放学别走...","你瞅啥",JOptionPane.WARNING_MESSAGE);
                //JOptionPane.showMessageDialog(MyJFrame1.this,"放学别走...","你瞅啥",JOptionPane.WARNING_MESSAGE,icon);

                //选择对话框
//                int choice = JOptionPane.showConfirmDialog(MyJFrame1.this,"请问放学别走好吗？","你等着",JOptionPane.YES_NO_CANCEL_OPTION);
//                if(choice == JOptionPane.YES_OPTION){
//                    System.out.println("YES");
//                }else if(choice == JOptionPane.NO_OPTION){
//                    System.out.println("NO");
//                }else if(choice == JOptionPane.CANCEL_OPTION){
//                    System.out.println("CANCEL");
//                }else if(choice == JOptionPane.CLOSED_OPTION){
//                    System.out.println("CLOSED");
//                }

                //自定义选项对话框
                String[] strs = {"1","2","3"};
                JOptionPane.showOptionDialog(MyJFrame1.this,"煤气泄露了，准备干嘛呢？","煤气！",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,icon,strs,strs[0]);
            }
        });

        jp.add(jb);
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
