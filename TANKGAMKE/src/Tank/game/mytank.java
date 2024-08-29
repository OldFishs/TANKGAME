package Tank.game;

import java.util.Vector;

public class mytank extends tank{
    bullet shot = null;
    //boolean live = true;
    Vector<bullet> shots = new Vector<>();

    public mytank(int y, int x) {
        super(y, x);
    }
    public mytank(int x, int y, int direct) {
        super(x, y, direct);
    }

    public void shotbullet(){
        switch(getDirect()){
            case 0:
                shot = new bullet(getX()+ 20,getY(),0);
                break;
            case 1:
                shot = new bullet(getX()+60,getY()+ 20,1);
                break;
            case 2:
                shot = new bullet(getX()+ 20,getY() + 60,2);
                break;
            case 3:
                shot = new bullet(getX(),getY() +20,3);
                break;
        }
        shots.add(shot);
        new Thread(shot).start();
    }
}
