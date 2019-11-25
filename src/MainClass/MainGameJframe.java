package MainClass;

import MyClass.MyImgJpanel;
import MyClass.MyJlabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 游戏界面
 *
 * @author 嘿 林梓鸿
 * @date 2019年 11月25日 18:02:08
 */
public class MainGameJframe extends JFrame {
    /**
     * map -> 地图
     */
    private JPanel map;

    /**
     * tower ->炮塔
     */
    private JPanel tower;

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

//    private boolean hasTowerFlag = false;
//    private ExecutorService threadPool = Executors.newSingleThreadExecutor();
//    private Future<Integer> future;



    public MainGameJframe(){
        super("Tower Defense Game");
        this.initComponents();
        /**
         * 生成怪物线程
         */

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

        map.setBounds(0,0,screenWidth-600,screenHeight-300);
        this.add(map);
        //地图大小位置

        moneytitile = new MyJlabel(this,"金币:",550,30,100,100);
        HPtitle = new MyJlabel(this,"血量:",550,100,100,100);
        money = new MyJlabel(this,"0",400,30,100,100);
        HP = new MyJlabel(this,"100",400,100,100,100);


        this.tower = new MyImgJpanel("Image/tower.png");
        tower.setBounds(screenWidth-500,300,100,100);


        tower.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        //炮塔光标
        add(tower);

        tower.addMouseListener(new MouseAdapter() {
            //炮塔监听事件
            @Override
            public void mouseClicked(MouseEvent e) {
                /*
                 当炮塔被点击时
                 生成炮塔线程
                 */

//                future = threadPool.submit(new Callable<Integer>() {
//                    public Integer call() throws Exception {
//                        return new Random().nextInt(100);
//                    }
//                });
//                hasTowerFlag = true;
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
