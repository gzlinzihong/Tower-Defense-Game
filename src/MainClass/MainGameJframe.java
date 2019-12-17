package MainClass;

import GameObject.Bullet;
import GameObject.Monster;
import GameObject.Tower;
import GameObject.Constant;
import MyClass.MyGif;
import MyClass.MyImgJpanel;
import MyClass.MyJlabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import static GameObject.Monster.dealDeadMonster;

/**
 * 游戏界面
 *
 * @author 嘿 林梓鸿
 * @date 2019年 11月25日 18:02:08
 */
public class MainGameJframe extends JFrame  {

    private JPanel map;                                     //map -> 地图
    private JPanel tower;                                   //tower ->炮塔图片

    ArrayList<JPanel> Towers = new ArrayList<>();           //Towers -> 炮塔集合
    Map<Integer,Monster> AllMonsters = new HashMap<>();     //AllMonsters -> 所有怪物集合
    ArrayList<Monster> monsters = new ArrayList<>();        //monsters -> 怪物集合

    private int screenWidth;                                //screenWidth -> 屏幕宽度
    private int screenHeight;                               //screenHeight -> 屏幕高度
    private JLabel moneytitile;                             //moneytitle -> "金币:"
    private JLabel HPtitle ;                                //HPtitle -> "血量:"
    public JLabel money;                                    //money -> 金币数 初始值0
    public JLabel HP ;                                      //HP -> 玩家血量 初始值100
    private int sbx;                                        //sbx -> 鼠标x轴坐标
    private int sby;                                        //sby -> 鼠标y轴坐标
    private MyImgJpanel test = new MyImgJpanel("Image/tower.png");  //test -> 工具人图片
    boolean hasClickedTowerFlag = false;                    //hasClickedTowerFlag -> 是否点击炮塔标记
    boolean isPutDownFlag = false;                          //isPutDownFlag -> 是否将炮塔放下标记
    private JLayeredPane jLayeredPane = this.getLayeredPane();  //jLayeredPane ->  JLayeredPane层,用来解决JPanel重叠问题
    private int monsterInterval;                            //
    private int monsterStep;                                //从MainStartJframe传过来的值,怪物的步数
    private int monsterAttack;                              //从MainStartJframe传过来的值,怪物的攻击力值
    private int monsterHP;                                  //从MainStartJframe传过来的值,怪物的血量

    /**
     * Drawing 动画线程类
     * 继承Thread
     */
    private class Drawing extends Thread{
        @Override
        public void run(){
            //怪物生成
            for(int i = 0 ; i < Constant.MAP1_MONSTERS.length - 1 ; i++){
                for(int j = 0 ; j < Constant.MAP1_MONSTERS[i][0] ; j++){
                    Monster monster1 = new Monster(Constant.FATTY_PATH,monsterHP,monsterAttack, Constant.MAX_BGWIDTH, Constant.MAX_BGWIDTH,monsterStep,HP,money);
                    if(j % 3 == 0 && j != 0){
                        monster1 = new Monster(Constant.DRAGON_PATH,monsterHP + 50,monsterAttack + 5, Constant.MAX_BGWIDTH, Constant.MAX_BGWIDTH,monsterStep,HP,money);
                    }else if(j % 5 == 0 && j != 0){
                        monster1 = new Monster(Constant.WOLFMAN_PATH,monsterHP + 100,monsterAttack + 10, Constant.MAX_BGWIDTH, Constant.MAX_BGWIDTH,monsterStep + 1,HP,money);
                    }
                    monsters.add(monster1);
                    jLayeredPane.add(monster1,Integer.valueOf(300));

                    //处理被打死的怪物，增加相应的金币数，该方法是静态方法
                    dealDeadMonster(monsters,money);
                    try {
                        sleep(monster1.getInterval(Constant.MAP1_MONSTERS[i][1],Constant.MAP1_MONSTERS[i][2]));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            //检测并处理怪物数组中已被打死的怪物，增加相应的金币数
            while(monsters.size() > 0){
                dealDeadMonster(monsters,money);
            }
        }
    }

    /**
     * 构造方法
     */
    public MainGameJframe(int monsterInterval,int monsterStep,int monsterAttack,int monsterHP){
        super("Tower Defense Game");
        this.initComponents();
        this.monsterInterval = monsterInterval;
        this.monsterStep = monsterStep;
        this.monsterAttack = monsterAttack;
        this.monsterHP = monsterHP;

        //生成怪物线程,创建Drawing对象
        new Drawing().start();
    }

    private void initComponents() {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        //获得屏幕得宽
        this.screenWidth = (int) screensize.getWidth();
        //获得屏幕得高
        this.screenHeight = (int) screensize.getHeight();

        //设置窗口大小不可更改
        this.setResizable(false);

        //设置用户在此窗体上发起 "close" 时默认执行System.exit(0)
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //将容器的布局设为绝对布局
        this.setLayout(null);

        //窗口大小以及位置
        this.setBounds(100,100,screenWidth-200,screenHeight-200);

        //生成地图
        this.map = new MyImgJpanel("Image/map.png");

        //地图大小位置
        map.setBounds(0,4,1190,770);
        jLayeredPane.add(map, Integer.valueOf(100));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //是否点击右边的炮塔图片
                if (hasClickedTowerFlag == true) {
                    isPutDownFlag = true;
                }
                if (isPutDownFlag == true && legalLocation(e.getX(),e.getY())) {
                    //生成炮塔
                    Tower t = new Tower("Image/tank1.png",10);

                    //把现有的怪物集合传给这个炮塔对象
                    t.setArray(monsters);
                    Thread t1 = new Thread(t);

                    //为所有怪物添加监听子弹的事件
                    for (Monster monster:monsters){
                        monster.addBullets(t.getBullet());
                    }

                    jLayeredPane.add(t,Integer.valueOf(2000));

                    //设置炮塔放置的位置
                    setLocation(e.getX(),e.getY(),t);

                    //先将该炮塔对象添加进炮塔集合，再启动线程
                    Towers.add(t);
                    t1.start();

                    test.setBounds(screenWidth-500, 300, 100, 100);

                    hasClickedTowerFlag = false;
                    isPutDownFlag = false;
                } else {
                    test.setBounds(screenWidth-500, 300, 100, 100);
                    hasClickedTowerFlag = false;
                    isPutDownFlag = false;
                }
            }
        });

        //监听鼠标移动，使炮塔跟随鼠标移动
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                sbx = e.getX();
                sby = e.getY();
                if (hasClickedTowerFlag == true){
                    test.setBounds(sbx-50,sby-90,100,100);
                    test.repaint();
                }
            }
        });

        //右边玩家血量和金币数
        moneytitile = new MyJlabel(this,"金币:",550,30,100,100);
        HPtitle = new MyJlabel(this,"血量:",550,100,100,100);
        money = new MyJlabel(this,"" + Constant.Money,400,30,100,100);
        HP = new MyJlabel(this,"" + Constant.PlayerHP,400,100,100,100);

        this.tower = new MyImgJpanel("Image/tower.png");
        tower.setBounds(screenWidth-500,300,100,100);
        jLayeredPane.add(tower,Integer.valueOf(300));

        tower.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        //炮塔光标
        test.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.add(tower);

        tower.addMouseListener(new MouseAdapter() {
            //右边炮塔选项监听事件
            @Override
            public void mouseClicked(MouseEvent e) {
                test.setBounds(screenWidth-500,300,100,100);
                jLayeredPane.add(test,Integer.valueOf(1000));
                hasClickedTowerFlag = true;
            }
        });
    }

    /**
     * @param money
     * 设置money
     */
    public void setMoney(int money){
        this.money.setText(String.valueOf(money));
    }

    /**
     * 获取金币数量字符串
     * @return money
     */
    public String getMoney(){
        return money.getText();
    }

    /**
     * @param HP
     * 设置玩家血量
     */
    public void setHP(int HP){
        this.HP.setText(String.valueOf(HP));
    }

    /**
     * 返回玩家血量字符串
     * @return HP
     */
    public String getHP(){
        return HP.getText();
    }

    public void setLocation(int x,int  y, Tower t) {
        t.setCenterXY(Constant.RELATIVE_LOCATION + ((x - Constant.MAX_LEFT) / Constant.MAX_BGWIDTH)
                * Constant.MAX_BGWIDTH + Constant.MAX_BGWIDTH / 2.0,Constant.RELATIVE_LOCATION +
                (((y - Constant.MAX_TOP) / Constant.MAX_BGWIDTH)) * Constant.MAX_BGWIDTH + Constant.MAX_BGWIDTH / 2.0, Constant.MAX_BGWIDTH*1.5);
        t.setBounds(Constant.RELATIVE_LOCATION + ((x - Constant.MAX_LEFT) / Constant.MAX_BGWIDTH) * Constant.MAX_BGWIDTH,
                Constant.RELATIVE_LOCATION + (((y - Constant.MAX_TOP) / Constant.MAX_BGWIDTH)) * Constant.MAX_BGWIDTH, Constant.MAX_BGWIDTH,Constant.MAX_BGWIDTH);
    }

    public Boolean legalLocation (int x, int y) {
        if (x < Constant.MAX_RIGHT && x > Constant.MAX_LEFT && y > Constant.MAX_TOP && y < Constant.MAX_BOTTOM && (x - Constant.MAX_LEFT) / Constant.MAX_BGWIDTH % 2 == 0 &&
                ((y - Constant.MAX_TOP) / Constant.MAX_BGWIDTH) % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

}
