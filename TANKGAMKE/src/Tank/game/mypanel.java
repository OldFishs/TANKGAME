package Tank.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class mypanel extends JPanel implements KeyListener,Runnable{
    mytank mytank = null;

    //敌方坦克集合
    Vector<enemgytank> enemgytanks = new Vector<>();

    //存放node对象的vector，恢复敌方坦克坐标方向
    Vector<node> nodes = new Vector<>();

    int enemgytanksize = 5;

    //炸弹bomb集合
    Vector<bomb> bombs = new Vector<>();
    Image i1 = null;
    Image i2 = null;
    Image i3 = null;
    //初始化
    public mypanel(String key) {

        //将enemgytank 设置给recorder Vector
        recorder.setEnemgytanks(enemgytanks);
        nodes = recorder.getNodesandenemgytanks();

        //初始化自己坦克
        mytank = new mytank(500,500);
        mytank.setM(10);
        bullet.speed = 3;//子弹速度

        //初始化炸弹
        i1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.png"));
        i2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.png"));
        i3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.png"));

        switch(key){
            case "1"://新游戏
                //初始化敌方坦克
                for(int j = 0; j < enemgytanksize; j++) {
                    //创建一辆敌方坦克
                    enemgytank enemgytank = new enemgytank(0,100 * (j + 1));
                    //将敌方坦克设置给集合
                    enemgytank.setEnemgytanks(enemgytanks);
                    //设置初始方向
                    enemgytank.setDirect(2);
                    //坦克线程
                    new Thread(enemgytank).start();
                    //子弹
                    bullet b = new bullet(enemgytank.getX()+20,enemgytank.getY()+60,enemgytank.getDirect() );
                    enemgytank.bullets.add(b);
                    new Thread(b).start();
                    enemgytanks.add(enemgytank);
                    enemgytank.setM(1);



                }
                break;

            case "2"://继续上局游戏
                //初始化敌方坦克
                for(int j = 0; j < nodes.size(); j++) {
                    node n = nodes.get(j);
                    //创建一辆敌方坦克
                    enemgytank enemgytank = new enemgytank(n.getY(),n.getX());
                    //将敌方坦克设置给集合
                    enemgytank.setEnemgytanks(enemgytanks);
                    //设置初始方向
                    enemgytank.setDirect(n.getDirect());
                    //坦克线程
                    new Thread(enemgytank).start();
                    //子弹
                    bullet b = new bullet(enemgytank.getX()+20,enemgytank.getY()+60,enemgytank.getDirect() );
                    enemgytank.bullets.add(b);
                    new Thread(b).start();
                    enemgytanks.add(enemgytank);
                    enemgytank.setM(1);


                }
                break;

            default:
                System.out.println("别几把乱按啊，只能输入1或者2！！");
        }

    }

    //显示我方击毁敌方坦克的信息
    public void showinfo(Graphics g){
        //玩家总成绩
        g.setColor(Color.black);
        Font font = new Font("微软雅黑",Font.BOLD,20);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克",1020,40);
        drawtank(1020,70,g,0,1);
        g.setColor(Color.black);
        g.drawString("X",1075,105);
        g.drawString(recorder.getAllentanknum() + "",1100,105);
    }

    //单位绘图
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        g.fillRect(0,0,1000,750);
        showinfo(g);
        //mytank
        if(mytank != null && mytank.tlive){
            drawtank(mytank.getX(),mytank.getY(),g,mytank.getDirect(),0);
        }
        //mybullet
        for(int i = 0; i <mytank.shots.size(); i++) {
            bullet b = mytank.shots.get(i);
            if(b != null && b.live){
                g.draw3DRect(b.x,b.y,2,2,false );
            }else{
                mytank.shots.remove(i);
            }
        }

        //enemgytank,bullet
        for(int i = 0; i<enemgytanks.size(); i++) {
            enemgytank enemgytank = enemgytanks.get(i);
            //判断坦克是否存活
            if(enemgytank.tlive){
                drawtank(enemgytank.getX(),enemgytank.getY(),g,enemgytank.getDirect(),1);
            }

            //敌方坦克射击
            for(int j = 0; j<enemgytank.bullets.size(); j++) {
                bullet b = enemgytank.bullets.get(j);
                if(b.live){
                    g.draw3DRect(b.x,b.y,2,2,false);
                }else{
                    enemgytank.bullets.remove(b);
                }
            }

        }

        //bomb
        for(int i = 0; i<bombs.size();i++){
            bomb bomb = bombs.get(i);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(bomb.livetime > 6){
                g.drawImage(i1,bomb.x,bomb.y,60,60,this);
            }else if(bomb.livetime > 3){
                g.drawImage(i2,bomb.x,bomb.y,60,60,this);
            }else {
                g.drawImage(i3,bomb.x,bomb.y,60,60,this);
            }
            bomb.Livetimedown();
            if(bomb.livetime == 0){
                bombs.remove(bomb);
            }
        }


    }

    //坦克颜色
    public void drawtank(int x,int y,Graphics g,int direct,int type){

        switch(type){
            case 0:
                g.setColor(Color.blue);
                break;
            case 1:
                g.setColor(Color.red);
                break;
            default:
                System.out.println("error");

        }

        //坦克方向
        switch(direct){
            case 0://up
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1://right
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2://down
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3://left
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;
            default:
                System.out.println("error");
        }



    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    //按键判断
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W){
            mytank.setDirect(0);
            mytank.up();
        }else if(e.getKeyCode()==KeyEvent.VK_A){
            mytank.setDirect(3);
            mytank.left();
        }else if(e.getKeyCode()==KeyEvent.VK_S){
            mytank.setDirect(2);
            mytank.down();
        }else if(e.getKeyCode()==KeyEvent.VK_D){
            mytank.setDirect(1);
            mytank.right();
        }
        this.repaint();

        //射击键
        if(e.getKeyCode()==KeyEvent.VK_J){
            //if(mytank.shot == null || !mytank.shot.live) {
                mytank.shotbullet();
            //}
        }


    }

    //击中我方判定
    public void hitmytank(){
        for(int i = 0; i < enemgytanks.size(); i++){
            enemgytank e = enemgytanks.get(i);
            for(int j = 0;j < e.bullets.size();j++){
                bullet b = e.bullets.get(j);
                //判断子弹b是否击中
                if(mytank.tlive && b.live){
                    hittank(b,mytank);
                }


            }
        }
    }

    //击中敌方判定
    public void hitenemgytank(){
        for(int j = 0; j<mytank.shots.size(); j++) {
            bullet b = mytank.shots.get(j);
            if(b != null && mytank.shot.live){
                for(int i=0;i<enemgytanks.size();i++){
                    enemgytank enemgytank = enemgytanks.get(i);
                    hittank(mytank.shot,enemgytank);
                }
            }
        }

    }

    //击中判定
    public void hittank(bullet b,tank tank){
        switch (tank.getDirect()){
            case 0:
            case 2:
                if(b.x>tank.getX() && b.x<tank.getX()+40
                && b.y>tank.getY() && b.y<tank.getY()+60){
                    b.live = false;
                    tank.tlive = false;

                    //消除坦克
                    enemgytanks.remove(tank);

                    //击毁计数
                    if(tank instanceof enemgytank){
                        recorder.add();
                    }
                    //bomb
                    bomb bomb = new bomb(tank.getX(),tank.getY());
                    bombs.add(bomb);

                }
            case 1:
            case 3:
                if(b.x>tank.getX() && b.x<tank.getX()+60
                        && b.y>tank.getY() && b.y<tank.getY()+40){
                    b.live = false;
                    tank.tlive = false;

                    //消除坦克
                    enemgytanks.remove(tank);

                    //击毁计数
                    if(tank instanceof enemgytank){
                        recorder.add();
                    }

                    //bomb
                    bomb bomb = new bomb(tank.getX(),tank.getY());
                    bombs.add(bomb);
                }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }


    @SuppressWarnings({"all"})
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hitenemgytank();
            hitmytank();
            this.repaint();
        }


    }
}
