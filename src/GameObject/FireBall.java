package GameObject;

import MyClass.MyImgJpanel;



/**
 * 火球类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月07日 14:49:39
 */
public class FireBall extends GameObject implements Runnable {
    private MyImgJpanel fireBallTest;
    private int tmpx;
    private int tmpy;
    private int x;
    private int y;
    public FireBall(MyImgJpanel fireBallTest,int x,int y){
        super("Image/rock.png");
        this.tmpx = x;
        this.tmpy = y;
        this.x = x;
        this.y = 0;
        this.setBounds(this.x-25,this.y,50,100);
        this.fireBallTest = fireBallTest;
        new Thread(this).start();
    }

    @Override
    public void run() {
        int distancey = Math.abs(this.tmpy-this.y)-60;
            for (int i = 0;i<distancey;i++){
                this.y+=1;
                this.setBounds(this.x-25,this.y,50,100);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.setVisible(false);
        this.fireBallTest.setBounds(0,0,0,0);
    }

//    public void finalXY(double x,double y,int step,int movespeed){
//
//    }

}
