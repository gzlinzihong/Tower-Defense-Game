package GameObject;

import MyClass.MyImgJpanel;

import java.util.ArrayList;

/**
 * 火球类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月07日 14:49:39
 */
public class FireBall extends GameObject implements Runnable {
    private MyImgJpanel fireBallTest;
    private int x;
    private int y;
    public FireBall(MyImgJpanel fireBallTest,int x,int y){
        super("Image/rock.png");
        this.x = x;
        this.y = y;
        this.setBounds(x,0,50,100);
        this.fireBallTest = fireBallTest;
        new Thread(this).start();
    }

    @Override
    public void run() {
        int tmp = 0;
        int times = y/7;
        for (int i = 0;i<times;i++){
            tmp = tmp+7;
            this.setBounds(x+100,tmp,50,100);

            this.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.fireBallTest.setBounds(0,0,0,0);
    }

//    public void finalXY(double x,double y,int step,int movespeed){
//
//    }

}
