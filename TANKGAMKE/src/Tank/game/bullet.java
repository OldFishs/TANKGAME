package Tank.game;

public class bullet implements Runnable {
    int x;
    int y;
    int direct = 0;
    static int speed;
    boolean live = true;

    public bullet(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {

        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            switch(direct){
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }

            //System.out.println(x + " "+ y);
            //子弹销毁
            if(x<0 || x>1000 || y<0 || y>750 || !live){
                live = false;
                break;
            }

        }
    }
}
