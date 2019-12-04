package MainClass;

import GameObject.Monster;
import GameObject.Tower;
import MyClass.MyGif;
import MyClass.MyImgJpanel;
import MyClass.MyJlabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * 游戏界面
 *
 * @author 嘿 林梓鸿
 * @date 2019年 11月25日 18:02:08
 */
public class MainGameJframe extends JFrame  {


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
    ArrayList<JPanel> Towers = new ArrayList<>();

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
    public JLabel money;

    /**
     * HP -> 血量 初始值100
     */
    public JLabel HP ;

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
    private MyImgJpanel test = new MyImgJpanel("Image/tower.png");

    /**
     * hasClickedTowerFlag -> 是否点击炮塔标记
     */
    boolean hasClickedTowerFlag = false;

    /**
     * isPutDownFlag -> 是否将炮塔放下标记
     */
    boolean isPutDownFlag = false;

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
    private int monsterSpeed = 20;



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

    /**
     * Drawing 动画线程类 继承Thread
     */

    private class Drawing extends Thread{
        @Override
        public void run(){
            for (int i = 0;i<8;i++){
                Monster monster1 = new Monster("Image/howl.png", monsterSpeed, 70, 70);
                jLayeredPane.add(monster1,Integer.valueOf(300));
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }



    public MainGameJframe(int monsterInterval,int monsterSpeed){
        super("Tower Defense Game");
        this.monsterInterval = monsterInterval;
        this.monsterSpeed = monsterSpeed;
        this.initComponents();

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


        this.setLayout(null);
        //将容器的布局设为绝对布局

        this.setBounds(100,100,screenWidth-200,screenHeight-200);
        //窗口大小以及位置


        this.map = new MyImgJpanel("Image/map.png");
        //生成地图

        map.setBounds(0,4,1190,770);
        jLayeredPane.add(map, Integer.valueOf(100));
        //地图大小位置

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (hasClickedTowerFlag == true) {
                    isPutDownFlag = true;
                }
                    if (isPutDownFlag == true) {

                        Tower t = new Tower("Image/tower.png");
                        jLayeredPane.add(t,Integer.valueOf(2000));
                        t.setBounds(sbx-50,sby-90,100,100);
                        Towers.add(t);
                        test.setBounds(screenWidth-500, 300, 100, 100);
                        hasClickedTowerFlag = false;
                        isPutDownFlag = false;
                    }

            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                sbx = e.getX();
                sby = e.getY();
                if (hasClickedTowerFlag == true){
                    test.setBounds(sbx-50,sby-90,100,100);
                    test.repaint();
//                    Tower t = new Tower(1, 140 + (((e.getX() - MAX_LEFT) / MAX_BGWIDTH) - 1) * 32,
//                            170 + (((e.getY() - MAX_TOP) / MAX_BGWIDTH)) * 32,
//                            MAX_BGWIDTH,MAX_BGWIDTH,Towers.size());
                }
            }
        });



        moneytitile = new MyJlabel(this,"金币:",550,30,100,100);
        HPtitle = new MyJlabel(this,"血量:",550,100,100,100);
        money = new MyJlabel(this,"0",400,30,100,100);
        HP = new MyJlabel(this,"100",400,100,100,100);


        this.tower = new MyImgJpanel("Image/tower.png");
        tower.setBounds(screenWidth-500,300,100,100);
        jLayeredPane.add(tower,Integer.valueOf(300));


        tower.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        //炮塔光标
        test.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(tower);



//        this.monster = new MyImgJpanel("Image/test.png");
//        monster.setBounds(5,64,100,100);
//        jLayeredPane.add(monster,Integer.valueOf(200));



        tower.addMouseListener(new MouseAdapter() {
            //炮塔监听事件
            @Override
            public void mouseClicked(MouseEvent e) {
                test.setBounds(screenWidth-500,300,100,100);
                jLayeredPane.add(test,Integer.valueOf(1000));
                hasClickedTowerFlag = true;


//                future = threadPool.submit(new Callable<Integer>() {
//                    public Integer call() throws Exception {
//                        return new Random().nextInt(100);
//                    }
//                });
//                hasTowerFlag = true;Press;
            }
        });






    }

    /**
     *
     * @param money
     * 设置money
     */
    public void setMoney(int money){
        this.money.setText(String.valueOf(money));
    }

    /**
     *
     * @param HP
     * 设置血量
     */
    public void setHP(int HP){
        this.HP.setText(String.valueOf(HP));
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
}
