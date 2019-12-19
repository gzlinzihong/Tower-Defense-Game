package MainClass;

import GameObject.*;
import MyClass.GameMusic;
import MyClass.MyImgJpanel;
import MyClass.MyJlabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * 游戏界面
 *
 * @author 嘿 林梓鸿
 * @date 2019年 11月25日 18:02:08
 */
public class MainGameJframe extends JFrame  {
    /**
     * 常量
     */
    static final int MAX_LEFT = (int)((double)Constant.sceenwidth*0.042);// 可放置炮塔的最左部x值
//    (int)((double)Constant.sceenwidth*0.68)
    static final int MAX_RIGHT = (int)((double)Constant.sceenwidth*0.71);// 可放置炮塔的最右部x值
    static final int MAX_TOP = (int)((double)Constant.sceenwidth*0.1);// 可放置炮塔的最顶部y值
    static final int MAX_BOTTOM = (int)((double)Constant.sceenwidth*0.38);// 可放置炮塔的最底部y值
    static final int MAX_BGWIDTH = (int)((double)Constant.sceenwidth*0.042);// 每个方块的像素长度
    static final int RELATIVE_LOCATION = MAX_BGWIDTH/2;  // 炮塔相对于左上角第一个塔座的距离

    /**
     * map -> 地图
     */
    private JPanel map;

    /**
     * tower ->炮塔图片
     */
    private JPanel tower;

    /**
     * Towers -> 炮塔集合
     */
    ArrayList<Tower> Towers = new ArrayList<>();


    /**
     * monsters -> 怪物集合
     */
    volatile ArrayList<Monster> monsters = new ArrayList<>();

    /**
     * screenWidth -> 屏幕宽度
     */
    private int screenWidth;

    /**
     * screenHeight -> 屏幕高度
     */
    private int screenHeight;

    /**
     *  moneytitle -> "金币:"
     */
    private JLabel moneytitile;

    /**
     * HPtitle -> "血量:"
     */
    private JLabel HPtitle ;

    /**
     * money -> 金币数 初始值0
     */
    static public JLabel money;

    /**
     * HP -> 血量 初始值100
     */
    static public JLabel HP ;

    /**
     * sbx -> 鼠标x轴坐标
     */
    private int sbx;

    /**
     * sby -> 鼠标y轴坐标
     */
    private int sby;

    /**
     * test -> 工具人图片
     */
    private MyImgJpanel test = new MyImgJpanel("Image/tank0.png");

    /**
     * hasClickedTowerFlag -> 是否点击炮塔标记
     */
    boolean towerHasClickedTowerFlag = false;

    /**
     * isPutDownFlag -> 是否将炮塔放下标记
     */
    boolean towerIsPutDownFlag = false;

    /**
     * hasClickedTowerFlag -> 是否点击炮塔标记
     */

    /**
     * test -> 工具人图片
     */
    private MyImgJpanel fireBalltest = new MyImgJpanel("Image/MagicCircle.png");

    boolean fireBallHasClickedTowerFlag = false;

    /**
     * isPutDownFlag -> 是否将炮塔放下标记
     */
    boolean fireBallIsPutDownFlag = false;

    /**
     * monsterInterval -> 怪物出现的时间间隔,由窗口1传递过来。默认为一般
     * 简单为 -1 即每波怪物的时间间隔比默认值多1s
     * 一般为 0 即每波怪物的时间间隔一致
     * 困难为 1 即每波怪物的时间间隔比默认值少1
     */
    private int monsterInterval = 0;

    /**
     * monsterSpeed -> 怪物走动的速度,即线程刷新的快慢
     */
    private int monsterSpeed = 1000;

    private MyImgJpanel fireball ;


//    private boolean hasTowerFlag = false;
//    private ExecutorService threadPool = Executors.newSingleThreadExecutor();
//    private Future<Integer> future;

    /**
     * monster -> 怪物
     */
    private MyImgJpanel monster;

    /**
     * jLayeredPane ->  JLayeredPane层,用来解决JPanel重叠问题
     */
    private JLayeredPane jLayeredPane = this.getLayeredPane();

    private int monsterStep;                                    //从MainStartJframe传过来的值,怪物的步数
    private int monsterAttack;                                  //从MainStartJframe传过来的值,怪物的攻击力值
    private int monsterHP;

    public static MyImgJpanel gameover = new MyImgJpanel("Image/gameover.png");
    public static boolean gameoverflag = false;
    private static MyImgJpanel victory = new MyImgJpanel("Image/victory.png");

    /**
     * Drawing 动画线程类 继承Thread
     */

    private class Drawing extends Thread{
        @Override
        public void run(){
            //怪物生成
            for(int i = 0 ; i < Constant.MAP1_MONSTERS.length - 1 ; i++){
                for(int j = 0 ; j < Constant.MAP1_MONSTERS[i][0] ; j++){
                    Monster monster1;
                    if(j % 3 == 0 && j != 0){
                         monster1 = new Monster(Constant.DRAGON_PATH,monsterHP + 50,monsterAttack + 5,monsterStep, MAX_BGWIDTH, MAX_BGWIDTH);
                    }else if(j % 5 == 0 && j != 0){
                         monster1 = new Monster(Constant.WOLFMAN_PATH,monsterHP + 100,monsterAttack + 10,monsterStep, MAX_BGWIDTH, MAX_BGWIDTH);
                    }
                    else {
                         monster1 = new Monster(Constant.FATTY_PATH,monsterHP,monsterAttack,monsterStep ,MAX_BGWIDTH, MAX_BGWIDTH);
                    }
                    monsters.add(monster1);
                    jLayeredPane.add(monster1,Integer.valueOf(800));

                    try {
                        sleep(monster1.getInterval(Constant.MAP1_MONSTERS[i][1],Constant.MAP1_MONSTERS[i][2]));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //修改怪物血量以及速度
                monsterHP += 30;
                if(monsterStep == 1){
                    continue;
                }
                monsterStep--;
            }
            new JudgeVictory().start();
        }
    }

    private class JudgeVictory extends Thread{
        @Override
        public void run(){
            while (monsters.size()!=0){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            victory.setVisible(true);
        }
    }

    private class JudgeMonstersDeath extends Thread{
        @Override
        public void run(){
            while (gameoverflag==false){

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (monsters.size() > 0) {
                    for(int i = 0; i < monsters.size(); i++) {
                        if (monsters.get(i).getDEATH() == 1) {
                            monsters.remove(i);
                            //System.out.println(monsters.size());
                        }
                    }
                }
            }
        }
    }

    private class JudgeTowersDeath extends Thread{
        @Override
        public void run(){
            while (gameoverflag==false){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(Towers.size() > 0) {
//                System.out.println("剩余"+Towers.size()+"个");
                    for (int i = 0; i < Towers.size(); i++) {
                        if (Towers.get(i).getDEATH()) {
                            System.out.println("炮塔"+i+"被移除");
                            Towers.remove(i);
                            i = 0;
                        }
                    }
                }
            }
        }
    }



    public MainGameJframe(int monsterInterval,int monsterStep,int monsterAttack,int monsterHP){
        super("Tower Defense Game");
        this.monsterInterval = monsterInterval;
        this.monsterStep = monsterStep;
        this.monsterAttack = monsterAttack;
        this.monsterHP = monsterHP;
        this.initComponents();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (gameoverflag==false){
                    GameMusic.Play("bgm/bgm.wav");
                }

            }
        }).start();
        new JudgeMonstersDeath().start();
        new JudgeTowersDeath().start();
        /**
         * 生成怪物线程
         */
        new Drawing().start();
//        for (int i = 0;i<5;i++){
//            new Drawing().start();
//
//        }

    }

    private void initComponents() {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = (int) screensize.getWidth();
        //获得屏幕得宽
        this.screenHeight = (int) screensize.getHeight();
        //获得屏幕得高

        this.setResizable(false);
        //设置窗口大小不可更改




        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //设置用户在此窗体上发起 "close" 时默认执行System.exit(0)
        gameover.setBounds((int)((double)screenWidth*0.052),(int)((double)screenHeight*0.065),(int)((double)screenWidth*0.78),(int)((double)screenHeight*0.65));
        gameover.setVisible(false);
        gameover.setBackground(null);
        gameover.setOpaque(false);
        jLayeredPane.add(gameover,Integer.valueOf(10000));

        victory.setBounds((int)((double)screenWidth*0.052),(int)((double)screenHeight*0.065),(int)((double)screenWidth*0.78),(int)((double)screenHeight*0.65));
        victory.setVisible(false);
        victory.setBackground(null);
        victory.setOpaque(false);
        jLayeredPane.add(victory,Integer.valueOf(10000));


        this.setLayout(null);
        //将容器的布局设为绝对布局

        this.setBounds((int)((double)screenWidth*0.052),(int)((double)screenHeight*0.093),screenWidth-screenWidth/10,(int)(screenHeight-((double)screenHeight*0.186)));
        //窗口大小以及位置


        this.map = new MyImgJpanel("Image/map.png");
        //生成地图

        map.setBounds(0,4,screenWidth-screenWidth/10,(int)(screenHeight-((double)screenHeight*0.186)));
        jLayeredPane.add(map, Integer.valueOf(100));
        //地图大小位置

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println(134+ ((e.getX() - MAX_LEFT) / MAX_BGWIDTH) * MAX_BGWIDTH);
                jframeClick(e);
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
               jframeMover(e);
            }
        });



        moneytitile = new MyJlabel(this,"金币:",(int)(((double)screenWidth*0.23)),(int)(((double)screenHeight*0.028)),100,100);
        HPtitle = new MyJlabel(this,"血量:",(int)(((double)screenWidth*0.23)),(int)(((double)screenHeight*0.093)),100,100);
        money = new MyJlabel(this,"60",(int)(((double)screenWidth*0.16)),(int)(((double)screenHeight*0.028)),100,100);
        HP = new MyJlabel(this,"100",(int)(((double)screenWidth*0.16)),(int)(((double)screenHeight*0.093)),100,100);
        jLayeredPane.add(moneytitile,Integer.valueOf(200));
        jLayeredPane.add(HPtitle,Integer.valueOf(200));
        jLayeredPane.add(money,Integer.valueOf(200));
        jLayeredPane.add(HP,Integer.valueOf(200));

        fireball = new MyImgJpanel("Image/rock.png");
        fireball.setBounds((int)(screenWidth-((double)screenWidth*0.20)),(int)(((double)screenHeight*0.46)),100,100);
        jLayeredPane.add(fireball,Integer.valueOf(400));
        fireball.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        fireball.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jLayeredPane.add(fireBalltest,Integer.valueOf(600));
                fireBallHasClickedTowerFlag = true;

            }
        });


        this.tower = new MyImgJpanel("Image/tank0.png");
        tower.setBounds((int)(screenWidth-((double)screenWidth*0.20)),(int)(((double)screenHeight*0.28)),100,100);
        jLayeredPane.add(tower,Integer.valueOf(700));


        tower.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        //炮塔光标


        tower.addMouseListener(new MouseAdapter() {
            //炮塔监听事件
            @Override
            public void mouseClicked(MouseEvent e) {
                jLayeredPane.add(test,Integer.valueOf(1000));
                towerHasClickedTowerFlag = true;


//                future = threadPool.submit(new Callable<Integer>() {
//                    public Integer call() throws Exception {
//                        return new Random().nextInt(100);
//                    }
//                });
//                hasTowerFlag = true;Press;
            }
        });






    }
    public void jframeClick(MouseEvent e){
        System.out.println(e.getX()+"  "+e.getY());
        if (towerHasClickedTowerFlag == true) {
            towerIsPutDownFlag = true;
            if (towerIsPutDownFlag == true && legalLocation(e.getX(),e.getY())&&Integer.valueOf(money.getText())>=20) {

                /**
                 * 生成炮塔
                 */
                money.setText(String.valueOf(Integer.valueOf(money.getText()) - 20));
                Tower t = new Tower("Image/tank0.png");
                t.setArray(monsters);
                Thread t1 = new Thread(t);
                for (Monster monster:monsters){
                    monster.addBullets(t.getBullet());
                }
                //子弹模块
                Bullet bullet = new Bullet();
                bullet.setBounds(0,0,30,30);
                bullet.setVisible(false);
                jLayeredPane.add(bullet,Integer.valueOf(2001));

                //炮塔升级模块
                UpdatePanel updatePanel = new UpdatePanel();
                updatePanel.setBounds(0,0,MAX_BGWIDTH,MAX_BGWIDTH);
                jLayeredPane.add(updatePanel,Integer.valueOf(2002));

                //炮塔移除模块
                RemovePanel removePanel = new RemovePanel();
                removePanel.setBounds(0,0,MAX_BGWIDTH,MAX_BGWIDTH);
                jLayeredPane.add(removePanel,Integer.valueOf(2002));

                jLayeredPane.add(t,Integer.valueOf(2000));
                setLocation(e.getX(),e.getY(),t);
                t.setBullet(bullet);
                t.setRemovePanel(removePanel);
                t.setUpdatePanel(updatePanel);
                Towers.add(t);
                t1.start();
                test.setBounds(0, 0, 0, 0);
                towerHasClickedTowerFlag = false;
                towerIsPutDownFlag = false;
            } else {
                test.setBounds(0, 0, 0, 0);
                towerHasClickedTowerFlag = false;
                towerIsPutDownFlag = false;
            }
        }

        if (fireBallHasClickedTowerFlag == true) {
            fireBallIsPutDownFlag = true;
            if (fireBallIsPutDownFlag == true) {
                fireBalltest.setBounds(sbx-140,sby-100,250,120);
                    fireball = new FireBall(fireBalltest,e.getX(),e.getY());
                    jLayeredPane.add(fireball,Integer.valueOf(2001));
//                fireball = new FireBall(fireBalltest,e.getX()-140,e.getY()-100);
//                jLayeredPane.add(fireball,Integer.valueOf(1001));
//            Bullet bullet = new Bullet(10,10);
//            t.setBullet(bullet);
//            bullet.setBounds(0,0,100,200);
//            jLayeredPane.add(bullet,Integer.valueOf(2001));
//                jLayeredPane.add(t,Integer.valueOf(2000));
//                test.setBounds(0, 0, 0, 0);
                fireBallHasClickedTowerFlag = false;
                fireBallIsPutDownFlag = false;
            } else {
//                test.setBounds(0, 0, 0, 0);
//                towerHasClickedTowerFlag = false;
//                towerIsPutDownFlag = false;
            }
        }

    }

    public void jframeMover(MouseEvent e){
        sbx = e.getX();
        sby = e.getY();
        if (towerHasClickedTowerFlag == true){
            test.setBounds(sbx-50,sby-90,MAX_BGWIDTH,MAX_BGWIDTH);
            test.repaint();
//                    Tower t = new Tower(1, 140 + (((e.getX() - MAX_LEFT) / MAX_BGWIDTH) - 1) * 32,
//                            170 + (((e.getY() - MAX_TOP) / MAX_BGWIDTH)) * 32,
//                            MAX_BGWIDTH,MAX_BGWIDTH,Towers.size());
        }

        if (fireBallHasClickedTowerFlag == true){
            fireBalltest.setBounds(sbx-140,sby-100,250,120);
            fireBalltest.repaint();
        }
    }


//    public void getModify(){
//                try {
//                    System.out.println(future.get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//
//
//    }
    public void setLocation(int x,int  y, Tower t) {
        x = x-50;
        y = y-90;
//        t.setCenterXY(RELATIVE_LOCATION + ((x - MAX_LEFT) / MAX_BGWIDTH) * MAX_BGWIDTH + MAX_BGWIDTH / 2.0,RELATIVE_LOCATION + (((y - MAX_TOP) / MAX_BGWIDTH)) * MAX_BGWIDTH + MAX_BGWIDTH / 2.0, MAX_BGWIDTH);
//        t.setBounds(RELATIVE_LOCATION + ((x - MAX_LEFT) / MAX_BGWIDTH) * MAX_BGWIDTH, RELATIVE_LOCATION + (((y - MAX_TOP) / MAX_BGWIDTH)) * MAX_BGWIDTH, MAX_BGWIDTH,MAX_BGWIDTH);
        //System.out.println(134+ ((x - MAX_LEFT) / MAX_BGWIDTH) * 32);
        t.setCenterXY(x,y,MAX_BGWIDTH);
        t.setBounds(x,y,MAX_BGWIDTH,MAX_BGWIDTH);
    }

    public Boolean legalLocation (int x, int y) {
//        if (x < MAX_RIGHT && x > MAX_LEFT && y > MAX_TOP && y < MAX_BOTTOM && (x - MAX_LEFT) / MAX_BGWIDTH % 2 == 0 && ((y - MAX_TOP) / MAX_BGWIDTH) % 2 == 0) {
//            return true;
//        } else {
//            return false;
//            //System.out.println(false);
//        }
        if ((x/MAX_BGWIDTH==1&&y/MAX_BGWIDTH==5)||(x/MAX_BGWIDTH==1&&y/MAX_BGWIDTH==9)){
            return false;
        }

        Rectangle rectangle = new Rectangle(x,y,MAX_BGWIDTH,MAX_BGWIDTH);
        if (x<MAX_RIGHT && x>MAX_LEFT && y>MAX_TOP && y<MAX_BOTTOM && ((y - MAX_TOP) / MAX_BGWIDTH) % 2 == 0){
            for (int i = 0;i<Towers.size();i++){
                if (rectangle.intersects(Towers.get(i).getRect())){
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

}
