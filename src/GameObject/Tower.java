package GameObject;

import MyClass.MyImgJpanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

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

    int R;
    //攻击半径

    int Level;
    // 等级

    int Num;


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
    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(AD);
            }
        });
    }

    @Override
    public void run() {

    }
}
