package GameObject;

import MyInterfaces.Moveable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 怪物类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月02日 23:36:32
 */
public class Monster extends GameObject implements Runnable, Moveable{
    /**
     * MNUMS -> 怪物数量
     */
//    private final int MNUMS = 8;

    /**
     * monsterSpeed -> 怪物走动的速度,即线程刷新的快慢
     */
    private int monsterSpeed = 20;

    /**
     * monsterSpeedDifference -> 怪物一波比一波快的差值
     */
    private int monsterSpeedDifference = 3;

//    private ArrayList<Monster> monsters = new ArrayList<Monster>();

    /**
     * 怪物图标
     */
    private ImageIcon img;

    /**
     * path -> 图片路径
     */
    private String path;

    /**
     * 血量图的宽度
     */
    private int HPX=100;

    /**
     * 血量图的高度
     */
    private int HPY=6;

    /**
     * 怪物步数
     */
    private int step = 3;


    /**
     * 血量图的路径
     */
    private String hpImgPath = "Image/hp.png";

    /**
     * 血量图
     */
    private ImageIcon hpImg ;

    /**
     * 血量图的x轴
     */
    private int hpImgX = 0;

    /**
     * 血量图的y轴
     */
    private int hpImgY = 0;

    /**
     * 子弹集合
     */
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    /**
     * 碰撞监听器
     */
    private CrashDetection crashListener = new CrashDetection();



    @Override
    protected void paintComponent(Graphics g){
        img = new ImageIcon(path);
        hpImg = new ImageIcon(hpImgPath);
        g.drawImage(img.getImage(),0,1,width,getHeight()-10,null);
        g.drawImage(hpImg.getImage(),hpImgX,hpImgY,HPX,HPY,null);
    }



    public Monster(String path,int monsterSpeed,int width,int height) {
        super(path);
        initParameter(path,monsterSpeed,width,height);
    }

    /**
     * 初始化变量
     * @param path
     * @param monsterSpeed
     * @param width
     * @param height
     */
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

    /**
     * 跑地图
     * flag = 1 向右跑
     * flag = 2 向左跑
     * flag = 3 想下跑
     */
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

    /**
     * 移动怪物
     * @param distance
     * @param step
     */
    public void moveMonster(int distance,int step,int flag){
        for (int i = 0;i<distance/step;i++){
            switch (flag){
                case 1:
                    hpImgX = 0;
                    hpImgY = 0;
                    HPX = 100;
                    HPY = 10;
                    path = "Image/howl.png" ;
                    hpImgPath = "Image/hp.png";
                    this.moveRight(i,step);
                    break;
                case 2:
                    hpImgX = 0;
                    hpImgY = 0;
                    HPX = 100;
                    HPY = 10;
                    path = "Image/howl2.png" ;
                    hpImgPath = "Image/hp2.png";
                    this.moveLeft(i,step);
                    break;
                case 3:
                    hpImgX = width-10;
                    hpImgY = 0;
                    HPX = 10;
                    HPY = 100;
                    path = "Image/howl3.png" ;
                    hpImgPath = "Image/hp3.png";
                    this.moveDown(i,step);
                    break;
                default:this.crashListener.initObject(this,this.bullets);
            }
            revalidate();
            //这个方法是只重绘组件。解决图片闪烁问题。但偶尔会卡顿
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

    public int getHPX() {
        return HPX;
    }

    public void setHPX(int HPX) {
        this.HPX = HPX;
    }

    public int getHPY() {
        return HPY;
    }

    public void setHPY(int HPY) {
        this.HPY = HPY;
    }

    public void addBullets(Bullet bullet){
        this.bullets.add(bullet);
    }
}
