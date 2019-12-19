package GameObject;


import MainClass.MainGameJframe;

import javax.swing.*;
import java.awt.*;

/**
 * 炮塔子弹类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月05日 11:24:13
 */
public class Bullet extends GameObject implements Runnable{
    private ImageIcon img = new ImageIcon("Image/AD.png");
    private double speedX;
    private double speedY;
    private boolean flag = true;
    private int AD;
    private int speed = 9;


    private Monster monster;
    public Bullet() {
        super();
        this.width=(int)((double)Constant.sceenwidth*0.026);
        this.height=(int)((double)Constant.sceenwidth*0.026);;
    }



    public void setAD(int AD) {
        this.AD = AD;
    }

    public void setXY(int x, int y ){
        this.X = x;
        this.Y = y;
    }
    public boolean getFlag() {
        return flag;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    @Override
    public void run() {
        this.setBounds((int)this.X,(int)this.Y,(int)width,(int)height);
        while (this.getRect().intersects(monster.getRect())==false&& MainGameJframe.gameoverflag==false){
            flag = false;
            double dis = Math.sqrt(Math.pow((monster.getMonsterX()-this.X), 2) + Math.pow((monster.getMonsterY() - this.Y), 2));
            double angleX = (monster.getMonsterX() - this.X)/dis;
            double angleY = (monster.getMonsterY() - this.Y)/dis;
            speedX = speed * angleX;
            speedY = speed * angleY;
            this.X += this.speedX;
            this.Y += this.speedY;
            this.setBounds((int)this.X,(int)this.Y,(int)width,(int)height);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (monster.getHp()<1||monster.getDEATH()==1){
            monster = null;
            flag = true;
            this.setVisible(false);
        }
        else {
            monster.setHp(AD);
            monster = null;
            flag = true;
            this.setVisible(false);
        }

    }
    public void startAD(){
        new Thread(this).start();
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle((int)X,(int)Y,(int)width,(int)height);
    }



    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img.getImage(),0,0,getWidth(),getHeight(),null);
    }

    @Override
    public void update(Graphics scr){
//        img = new ImageIcon(path);
//        iBuffer=createImage(getWidth(),getHeight());
//
//        gBuffer=iBuffer.getGraphics();
//        gBuffer = (Graphics2D)gBuffer;
//        gBuffer.drawImage(img.getImage(),0,0,getWidth(),getHeight(),null);
        this.paintComponent(scr);

    }
}
