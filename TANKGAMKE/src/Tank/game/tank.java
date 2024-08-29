package Tank.game;

import java.util.Vector;

public class tank {
    private int x;
    private int y;
    private int direct;
    private int m ;
    boolean tlive = true;

    public tank(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public tank(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    //坦克移动
    public void up(){
        if(getY() > 0 ){
            y -=m;
        }
    }
    public void down(){
        if(getY() + 60 < 750){
            y +=m;
        }
    }
    public void left(){
        if(getX() > 0){
            x -=m;
        }
    }
    public void right(){
        if(getX() + 60 < 1000){
            x +=m;
        }

    }

    public void setM(int m) {
        this.m = m;
    }

}
