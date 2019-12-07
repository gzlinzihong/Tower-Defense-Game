package GameObject;

import MyClass.MyImgJpanel;
import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * 炮塔类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月02日 23:37:25
 */
public class Tower extends GameObject implements Runnable{

    boolean isPutDownFlag = false;
    private Bullet bullet;

    public Bullet getBullet() {
        return bullet;
    }

    public int getAD() {
        return AD;
    }

    public void setAD(int AD) {
        this.AD = AD;
    }

    int AD;
    //攻击力

    double R;
    //攻击半径

    int Level;
    // 等级

    int Num;

    int number;

    ArrayList<Monster> monsters;


//    ImageIcon Img = new ImageIcon("Image/tower.png");
    //炮塔图片


//,int level,int x,int y,int w,int h,int num
    public Tower (String path) {
        super(path);
        this.AD = AD;
        this.addListener();
////        super();
//        this.Level = level;
//        if(level == 1) {
//            this.AD = 34;
//            this.X = x;
//            this.Y = y;
//            this.Num = num;
//        }
//        new Thread(this).start();
    }

    public void setArray(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }


    // 設置x,y,r
    public void setCenterXY(double x, double y,double r) {
        this.X = x;
        this.Y = y;
        this.R = r;
    }

    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(X +","+ Y);
            }
        });
    }

    @Override
    public void run() {
        while (true) {
            /**
             * 当怪物集合中有怪物时开始判断
             */
            if(monsters.size() > 0 ) {
                for(int i = 0; i < monsters.size(); i++) {
                    /**
                     * 將当前炮塔的x，y，r值放入每個怪物中
                     */
                    monsters.get(i).setTower(X,Y,R);
                    //m.setTower((int)this.X,(int)this.Y,this.R);
//                    if (m.getCenter(this.X,this.Y,this.R)) {
//                        number++;
//                        System.out.println(this.Num+"号炮塔发现"+number+"个敌人");
//                    }
                    /**
                     * 判断每个怪物是否在当前炮塔的攻击范围内
                     */
                    monsters.get(i).getCenter();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for(Monster m : monsters) {
                    //m.setTower((int)this.X,(int)this.Y,this.R);
//                    if (m.getCenter(this.X,this.Y,this.R)) {
//                        number++;
//                        System.out.println(this.Num+"号炮塔发现"+number+"个敌人");
//                    }
                    m.getCenter();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
