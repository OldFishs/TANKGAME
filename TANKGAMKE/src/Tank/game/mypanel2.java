package Tank.game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class mypanel2 extends JFrame {
    mypanel my = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        mypanel2 m = new mypanel2();


    }

    public mypanel2(){
        System.out.println("请输入选择1：新游戏  2：继续上局游戏");
        String key = scanner.next();
        my = new mypanel(key);
        Thread t = new Thread(my);
        t.start();
        this.add(my);
        this.setSize(1240,790);
        this.addKeyListener(my);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //监听关闭窗口
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                recorder.record();
                System.exit(0);
            }
        });
    }
}
