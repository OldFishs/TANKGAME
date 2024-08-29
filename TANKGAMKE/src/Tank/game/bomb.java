package Tank.game;

public class bomb {
    int x;
    int y;
    int livetime = 10;
    boolean live = true;

    public bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void Livetimedown(){
        if(livetime > 0){
            livetime--;
        }else{
            live = false;
        }
    }
}
