package Tank.game;

import java.util.Vector;

public class enemgytank extends tank implements Runnable{
    //boolean tlive = true;
    public enemgytank(int y, int x) {
        super(y, x);
    }

    public enemgytank(int x, int y, int direct) {
        super(x, y, direct);
    }

    //敌方子弹
    Vector<bullet> bullets = new Vector<>();



    @SuppressWarnings({"all"})
    @Override
    public void run() {
        while(true){
            //敌方多颗子弹
            if(tlive && bullets.size() < 10){
                bullet b = null;
                switch(getDirect()){
                    case 0:
                        b = new bullet(getX()+ 20,getY(),0);
                        break;
                    case 1:
                        b = new bullet(getX()+ 60,getY() + 20,1);
                        break;
                    case 2:
                        b = new bullet(getX()+ 20,getY() + 60,2);
                        break;
                    case 3:
                        b = new bullet(getX(),getY() + 20,3);
                        break;
                }
                bullets.add(b);
                new Thread(b).start();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //移动坦克
            switch(getDirect()){

                case 0:
                    for(int i = 0; i < 40; i++){
                        if(getY() > 0 && !istouchenemgytank()) {
                            up();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                case 1:
                    for(int i = 0; i < 40; i++){
                        if(getX() + 60 < 1000 && !istouchenemgytank()) {
                            right();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                case 2:
                    for(int i = 0; i < 40; i++){
                        if(getY() + 60 < 750 && !istouchenemgytank()) {
                            down();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                case 3:
                    for(int i = 0; i < 40; i++){
                        if(getX() > 0 && !istouchenemgytank()) {
                            left();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

            }

            setDirect((int)(Math.random() * 4));
            if(!tlive){
                break;
            }

        }
    }

    //判断敌方坦克是否重叠
    Vector<enemgytank> enemgytanks = new Vector<>();

    //获取每个敌方坦克
    public void setEnemgytanks(Vector<enemgytank> enemgytanks){
        this.enemgytanks = enemgytanks;
    }

    //判断敌方坦克是否发生重叠/碰撞
    public boolean istouchenemgytank(){
        //判断敌人坦克现在方向
        switch(this.getDirect()){
            case 0://shang
                for(int i = 0; i < enemgytanks.size(); i++){
                    //取一个敌方坦克
                    enemgytank enemgytank = enemgytanks.get(i);
                    if(enemgytank != this){
                        //敌方上/下
                        if(enemgytank.getDirect() == 0 || enemgytank.getDirect() == 2){

                            //我方左上角判断
                            if(this.getX() >= enemgytank.getX()
                                    && this.getY() >= enemgytank.getY()
                                    &&this.getX() <= enemgytank.getX() + 40
                                    && this.getY() <= enemgytank.getY() + 60){
                                return true;
                            }
                            //右上角判断
                            if(this.getX() + 40 >= enemgytank.getX()
                                    && this.getY() + 40 >= enemgytank.getY()
                                    &&this.getX() <= enemgytank.getX() + 40
                                    && this.getY() <= enemgytank.getY() + 60){
                                return true;
                            }

                        }

                        //敌方左/右
                        if(enemgytank.getDirect() == 1 || enemgytank.getDirect() == 3){

                            if(this.getX() >= enemgytank.getX()
                                    && this.getY() >= enemgytank.getY()
                                    && this.getX() <= enemgytank.getX() + 60
                                    && this.getY() <= enemgytank.getY() + 40){
                                return true;
                            }

                            if(this.getX() + 40 >= enemgytank.getX()
                                    && this.getY()>= enemgytank.getY()
                                    && this.getX() + 40 <= enemgytank.getX() + 60
                                    && this.getY() <= enemgytank.getY() + 40){
                                return true;
                            }

                        }
                    }
                }
                break;

            case 1://you
                for(int i = 0; i < enemgytanks.size(); i++){
                    enemgytank enemgytank = enemgytanks.get(i);
                    if(enemgytank != this){
                        //敌方上/下
                        if(enemgytank.getDirect() == 0 || enemgytank.getDirect() == 2){

                            //我方右上角判断
                            if(this.getX() + 60 >= enemgytank.getX()
                                    && this.getY() >= enemgytank.getY()
                                    &&this.getX() + 60 <= enemgytank.getX() + 40
                                    && this.getY() <= enemgytank.getY() + 60){
                                return true;
                            }
                            //右下角判断
                            if(this.getX() + 60 >= enemgytank.getX()
                                    && this.getY() + 40 >= enemgytank.getY()
                                    &&this.getX() + 60 <= enemgytank.getX() + 40
                                    && this.getY() + 40 <= enemgytank.getY() + 60){
                                return true;
                            }

                        }

                        //敌方左/右
                        if(enemgytank.getDirect() == 1 || enemgytank.getDirect() == 3){

                            //右上角
                            if(this.getX() + 60>= enemgytank.getX()
                                    && this.getY() >= enemgytank.getY()
                                    && this.getX() + 60 <= enemgytank.getX() + 60
                                    && this.getY() <= enemgytank.getY() + 40){
                                return true;
                            }
                            //右下角
                            if(this.getX() + 60 >= enemgytank.getX()
                                    && this.getY() + 40 >= enemgytank.getY()
                                    && this.getX() + 60 <= enemgytank.getX() + 60
                                    && this.getY() + 40 <= enemgytank.getY() + 40){
                                return true;
                            }

                        }
                    }
                }
                break;

            case 2://xia
                for(int i = 0; i < enemgytanks.size(); i++){
                    enemgytank enemgytank = enemgytanks.get(i);
                    if(enemgytank != this){
                        //敌方上/下
                        if(enemgytank.getDirect() == 0 || enemgytank.getDirect() == 2){

                            //我方左下角判断
                            if(this.getX() >= enemgytank.getX()
                                    && this.getY() + 60 >= enemgytank.getY()
                                    &&this.getX() <= enemgytank.getX() + 40
                                    && this.getY() + 60 <= enemgytank.getY() + 60){
                                return true;
                            }
                            //右下角判断
                            if(this.getX() + 40 >= enemgytank.getX()
                                    && this.getY() + 60 >= enemgytank.getY()
                                    &&this.getX() + 40 <= enemgytank.getX() + 40
                                    && this.getY() + 60 <= enemgytank.getY() + 60){
                                return true;
                            }

                        }

                        //敌方左/右
                        if(enemgytank.getDirect() == 1 || enemgytank.getDirect() == 3){

                            //左下角
                            if(this.getX() >= enemgytank.getX()
                                    && this.getY() + 60 >= enemgytank.getY()
                                    && this.getX() <= enemgytank.getX() + 60
                                    && this.getY() + 60 <= enemgytank.getY() + 40){
                                return true;
                            }
                            //右下角
                            if(this.getX() + 40 >= enemgytank.getX()
                                    && this.getY() + 60 >= enemgytank.getY()
                                    && this.getX() + 40 <= enemgytank.getX() + 60
                                    && this.getY() + 60 <= enemgytank.getY() + 40){
                                return true;
                            }

                        }
                    }
                }
                break;

            case 3://zuo
                for(int i = 0; i < enemgytanks.size(); i++){
                    enemgytank enemgytank = enemgytanks.get(i);
                    if(enemgytank != this){
                        //敌方上/下
                        if(enemgytank.getDirect() == 0 || enemgytank.getDirect() == 2){

                            //我方左上角判断
                            if(this.getX() >= enemgytank.getX()
                                    && this.getY() >= enemgytank.getY()
                                    &&this.getX() <= enemgytank.getX() + 40
                                    && this.getY() <= enemgytank.getY() + 60){
                                return true;
                            }
                            //左下角判断
                            if(this.getX() >= enemgytank.getX()
                                    && this.getY() + 40 >= enemgytank.getY()
                                    &&this.getX() <= enemgytank.getX() + 40
                                    && this.getY() + 40 <= enemgytank.getY() + 60){
                                return true;
                            }

                        }

                        //敌方左/右
                        if(enemgytank.getDirect() == 1 || enemgytank.getDirect() == 3){

                            //左上角
                            if(this.getX() >= enemgytank.getX()
                                    && this.getY() >= enemgytank.getY()
                                    && this.getX() <= enemgytank.getX() + 60
                                    && this.getY() <= enemgytank.getY() + 40){
                                return true;
                            }
                            //左下角
                            if(this.getX() >= enemgytank.getX()
                                    && this.getY() +40 >= enemgytank.getY()
                                    && this.getX() <= enemgytank.getX() + 60
                                    && this.getY() + 40 <= enemgytank.getY() + 40){
                                return true;
                            }

                        }
                    }
                }
                break;

        }
        return false;
    }
}
