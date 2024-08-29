package Tank.game;

import javax.xml.soap.Node;
import java.io.*;
import java.util.Vector;

//用于记录相关信息，与文件交互
public class recorder {
    //我方击毁坦克数量
    private static int allentanknum = 0;

    private static FileWriter fw= null;
    private static BufferedWriter bw= null;
    private static BufferedReader br= null;

    private static Vector<enemgytank> enemgytanks = null;
    private static String filepath = "src\\myrecord.txt";

    //定义node的vector，保存敌人信息
    public static Vector<node> nodes = new Vector<>();

    public static void setEnemgytanks(Vector<enemgytank> enemgytanks) {
        recorder.enemgytanks = enemgytanks;
    }

    //保存信息到下一局
    public static Vector<node> getNodesandenemgytanks() {
        try {
            br = new BufferedReader(new FileReader(filepath));
            allentanknum = Integer.parseInt(br.readLine());

            //循环读取，生成node集合
            String line =null;
            while((line = br.readLine()) != null){
                String[] xyd = line.split(" ");
                node n = new node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),Integer.parseInt(xyd[2]));
                nodes.add(n);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
                try {
                    if(br!=null){
                        br.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }
        return nodes;
    }


    //退出游戏时，保存到filepath
    public static void record() {
        try {
            bw= new BufferedWriter(new FileWriter(filepath));
            bw.write(allentanknum + "\r\n");

            //保存敌人坦克坐标和方向
            //遍历敌方vector 看情况保存
            for(int i = 0; i<enemgytanks.size(); i++) {
                enemgytank en = enemgytanks.get(i);
                if(en.tlive){
                    String recorder = en.getX() + " " + en.getY() + " " + en.getDirect();
                    bw.write(recorder + "\r\n");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw!=null){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static int getAllentanknum() {
        return allentanknum;
    }

    public static void setAllentanknum(int allentanknum) {
        recorder.allentanknum = allentanknum;
    }

    public static void add() {
        recorder.allentanknum++;
    }
}
