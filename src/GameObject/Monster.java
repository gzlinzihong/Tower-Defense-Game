package GameObject;

import MyInterfaces.Moveable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

/**
 * 怪物类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月02日 23:36:32
 */
public class Monster extends GameObject implements Runnable, Moveable, ComponentListener{
    /**
     * MNUMS -> 怪物数量
     */
    private final int MNUMS = 8;

    /**
     * path -> 图片路径
     */
    private String path;
    /**
     * monsterSpeed -> 怪物走动的速度,即线程刷新的快慢
     */
    private int monsterSpeed = 20;

    /**
     * monsterSpeedDifference -> 怪物一波比一波快的差值
     */
    private int monsterSpeedDifference = 3;

    private ArrayList<Monster> monsters = new ArrayList<Monster>();

    private int HP;
    private int speed;
    private int step = 3;



    public Monster(String path,int monsterSpeed,int width,int height) {
        super(path);
        initParameter(path,monsterSpeed,width,height);
    }

    public void initParameter(String path,int monsterSpeed,int width,int height){
        this.path = path;
        this.monsterSpeed = monsterSpeed;
        this.X = 5;
        this.Y = 70;
        this.width = width;
        this.height = height;
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        runMap();
    }

    public void runMap(){
        moveMonster(1050,step,1);
        moveMonster(140,step,3);
        moveMonster(985,step,2);
        moveMonster(140,step,3);
        moveMonster(985,step,1);
        moveMonster(140,step,3);
        moveMonster(985,step,2);
        moveMonster(140,step,3);
        moveMonster(930,step,1);
        this.setVisible(false);
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {

    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {
        Component component = componentEvent.getComponent();

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }

    /**
     * @param distance
     * @param step
     */
    public void moveMonster(int distance,int step,int flag){
        for (int i = 0;i<distance/step;i++){
            switch (flag){
                case 1: this.setPath("Image/howl.png"); this.moveRight(i,step);break;
                case 2: this.setPath("Image/howl2.png");this.moveLeft(i,step);break;
                case 3: this.setPath("Image/howl1.png");this.moveDown(i,step);break;
            }
            revalidate();
            try {
                Thread.sleep(monsterSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void moveRight(int i,int step) {
        X = X+step;
        this.setBounds(X,Y,width,height);
    }

    @Override
    public void moveLeft(int i,int step) {
        X = X-step;
        this.setBounds(X,Y,width,height);
    }

    @Override
    public void moveDown(int i,int step) {
        Y = Y+step;
        this.setBounds(X,Y,width,height);
    }
}
