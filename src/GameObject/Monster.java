package GameObject;

import MyClass.MyGif;
import MyInterfaces.Moveable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * 怪物类
 * @author 嘿 林梓鸿
 * @date 2019年 12月02日 23:36:32
 */
public class Monster extends GameObject implements Runnable{
    private int DEATH = 0;                      //怪物死亡标志
//    private final int MNUMS = 8;              //MNUMS -> 怪物数量
    private int repaintSpeed = 30;              //speed -> 怪物走动的速度,即线程刷新的快慢
    private int monsterSpeedDifference = 3;     //monsterSpeedDifference -> 怪物一波比一波快的差值
    private ImageIcon img;                      //怪物图标
    private int hp;                             //怪物血量
    private String[] pathArray;                 //图片路径数组
    private String path;                        //path -> 图片路径
    private int HPX = 100;                      //血量图的宽度
    private int HPY = 10;                       //血量图的高度
    private int step;                           //怪物步数
    private String hpImgPath = "Image/hp.png";  //血量图的路径
    private ImageIcon hpImg ;                   //血量图
    private int hpImgX = 0;                     //血量图的x轴
    private int hpImgY = 0;                     //血量图的y轴
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();        //子弹集合
    private CrashDetection crashListener = new CrashDetection();   //碰撞监听器

    private double Tower_x;
    private double Tower_y;
    private double Tower_r;

    private int attack;                     //怪物攻击力
    private JLabel PlayerHP;                //玩家HP值容器
    private JLabel PlayerMoney;             //玩家金币值容器
    private Thread MonsterThread;           //此怪物线程
    private boolean exit = true;            //怪物线程终止标志，用于退出 run() 方法中的while循环

    @Override
    protected void paintComponent(Graphics g){
        img = new ImageIcon(path);
        hpImg = new ImageIcon(hpImgPath);
        g.drawImage(img.getImage(),0,1,(int)width,getHeight()-10,null);
        g.drawImage(hpImg.getImage(),hpImgX,hpImgY,HPX,HPY,null);
    }

    public Monster(String[] pathArray,int monsterHP,int monsterAttack,int width,int height,int step,JLabel playerHP,JLabel playerMoney) {
        super(pathArray[0]);
        this.path = pathArray[0];
        this.pathArray = pathArray;
        this.hp = monsterHP;
        this.attack = monsterAttack;
        this.X = 5;
        this.Y = 70;
        this.width = width;
        this.height = height;
        this.step = step;
        this.PlayerHP = playerHP;
        this.PlayerMoney = playerMoney;
        MonsterThread = new Thread(this);
        MonsterThread.start();
        this.createMouseAdapter();
    }

    public void createMouseAdapter(){
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hp -=100;
                System.out.println(hp);
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    @Override
    public void run() {
        while(exit){
            move();
            this.setVisible(false);
            //到达终点，攻击玩家
            this.reducePlayerHP(this.PlayerHP);
        }
    }

    /**
     * 检测怪物是否在死之前到达终点
     */
    private void testArriveDestination(int destination,JLabel HP){
        if(hp >= 1){
            reducePlayerHP(HP);
        }
    }

    /**
     * 怪物移动方法
     * 0 -> up,1 -> right,2 -> down,3 -> left
     */
    public void move(){
        //检测是否经过标记点
        int j = 0;
        for(int i = 0 ; i <= Constant.MAP1_COORD[j][0]/step ; i++){
            //修改坐标等相应的变量
            switch (Constant.MAP1_COORD[j][1]){
                case 0:
                    moveRepeatOption(0,pathArray[0]);
                    break;
                case 1:
                    moveRepeatOption(1,pathArray[0]);
                    break;
                case 2:
                    moveRepeatOption(2,pathArray[1]);
                    break;
                case 3:
                    moveRepeatOption(3,pathArray[1]);
                    break;
                default:this.crashListener.initObject(this,this.bullets);
            }

            //修改i,j,每走完一段，j++,i = 0
            if(i >= Constant.MAP1_COORD[j][0]/step){
                j++;
                if(j >= Constant.MAP1_COORD.length)
                    break;
                i = 0;
            }

            revalidate();
            //这个方法是只重绘组件。解决图片闪烁问题。但偶尔会卡顿
            try {
                Thread.sleep(repaintSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 怪物移动方法中的重复操作
     * @param direct
     */
    private void moveRepeatOption(int direct,String pathItem){
        if(hp < 1) {
            this.DEATH = 1;
            this.exit = false;
            this.setVisible(false);
        } else {
            switch (direct){
                case 0:
                    Y -= step;
                    break;
                case 1:
                    X = X + step;
                    break;
                case 2:
                    Y = Y + step;
                    break;
                case 3:
                    X  -= step;
            }
        }
        this.setBounds((int) X, (int) Y, (int) width, (int) height);
        //更换怪物图片
        path = pathItem;
    }

    /**
     * 减少玩家血量，怪物到达终点后，攻击玩家
     * @param e
     */
    public void reducePlayerHP(JLabel e){
        if(this.DEATH == 0 ) {
            Constant.PlayerHP -= this.attack;
            e.setText("" + Constant.PlayerHP);
        }
    }

    /**
     * 增加玩家金币数量，在怪物被炮塔打死后
     * @param e
     */
    public void increasePlayerMoney(JLabel e){
        Constant.Money += 10;
        e.setText("" + Constant.Money);
    }

    /**
     * 处理怪物集合中已被打死的怪物，增加相应的金币数
     * @param monsters
     */
    public static void dealDeadMonster(ArrayList<Monster> monsters,JLabel money){
        for(int j = 0; j < monsters.size(); j++) {
            if (monsters.get(j).getDEATH() == 1) {
                monsters.get(j).increasePlayerMoney(money);
                monsters.remove(j);
            }
        }
    }

    /**
     * 返回 n ~ m 毫秒的随机数，作为每个怪物出现的间隔
     * @param n
     * @param m
     * @return
     */
    public int getInterval(int n,int m){
        return (int)(Math.random()*(m - n + 1) + n);
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

    public void setTower(double x,double y, double r) {
        this.Tower_x = x;
        this.Tower_y = y;
        this.Tower_r = r;
    }

    public int getDEATH() {
        return this.DEATH;
    }

    public int getHp(){
        return this.hp;
    }

    @Override
    public void getCenter() {
        if(Math.sqrt(Math.pow((this.X - this.Tower_x), 2) + Math.pow(this.Y - this.Tower_y, 2)) <= (this.Tower_r + MAX_BGWIDTH*Math.sqrt(2))) {
            this.hp -= 20;
        }
    }
}
