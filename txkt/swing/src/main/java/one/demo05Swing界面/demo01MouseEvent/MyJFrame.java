package one.demo05Swing界面.demo01MouseEvent;

import javax.swing.*;
import java.awt.event.*;

public class MyJFrame extends JFrame {
    private JPanel jp = new JPanel();

    public MyJFrame(){
        //鼠标滚轮监听
        //jp.addMouseWheelListener();
        //鼠标监听
        //jp.addMouseListener();
        //鼠标运动监听
       //jp.addMouseMotionListener();

        final JLabel jLabel = new JLabel("我来显示");

//        jp.addMouseWheelListener(new MouseWheelListener() {
//            public void mouseWheelMoved(MouseWheelEvent e) {
//                jLabel.setText("鼠标滚动了");
//            }
//        });

//        jp.addMouseMotionListener(new MouseMotionListener() {
//            public void mouseDragged(MouseEvent e) {
//                jLabel.setText("鼠标拖拽了"+e.getX()+":"+e.getY());
//            }
//
//            public void mouseMoved(MouseEvent e) {
//                jLabel.setText("鼠标移动了"+e.getX()+":"+e.getY());
//            }
//        });

        jp.addMouseListener(new MouseListener() {
            //鼠标点击
            public void mouseClicked(MouseEvent e) {
                //按下在其它地方抬起不算点击
                //jLabel.setText("鼠标点击了");
            }

            //鼠标按下
            public void mousePressed(MouseEvent e) {
                jLabel.setText("鼠标按下了");
            }

            //鼠标抬起
            public void mouseReleased(MouseEvent e) {
                jLabel.setText("鼠标抬起了");
            }

            //鼠标进入
            public void mouseEntered(MouseEvent e) {
                jLabel.setText("鼠标进入了");
            }

            //鼠标离开
            public void mouseExited(MouseEvent e) {
                jLabel.setText("鼠标离开了");
            }
        });

        jp.add(jLabel);
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
