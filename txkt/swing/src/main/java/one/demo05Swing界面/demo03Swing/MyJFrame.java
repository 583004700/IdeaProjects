package one.demo05Swing界面.demo03Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MyJFrame extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame(){
        String[] strs = {"吃饭","睡觉","打豆豆"};
        JList<String> list = new JList<String>(strs);
        JButton jButton = new JButton("获取");
        JLabel jLabel = new JLabel("我来显示");

        JComboBox<String> jc = new JComboBox<String>(strs);


        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(Arrays.toString(list.getSelectedIndices()));
                System.out.println(list.getSelectedValuesList());

                System.out.println(jc.getSelectedItem());
            }
        });


        jp.add(list);
        jp.add(jButton);
        jp.add(jLabel);
        jp.add(jc);
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
