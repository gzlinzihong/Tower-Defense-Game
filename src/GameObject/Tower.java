package GameObject;

import MainClass.MainGameJframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * 炮塔类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月02日 23:37:25
 */
public class Tower extends GameObject implements Runnable{

    boolean isPutDownFlag = false;
    private Bullet bullet;

    int AD;                         //攻击力
    double R;                       //攻击半径
    int Level;                      // 等级
    int Num;
    int number;
    ArrayList<Monster> monsters;    //怪物对象数组

    //弹窗容器以及菜单选项
    private JPopupMenu MenuContainer = new JPopupMenu("menu");
    private JMenuItem upGradeItem = new JMenuItem("升级");
    private JMenuItem downGradeItem = new JMenuItem("降级");
    private JMenuItem sellOutItem = new JMenuItem("出售");
    private JMenuItem cancelItem = new JMenuItem("取消");

    public Bullet getBullet() {
        return bullet;
    }

    public int getAD() {
        return AD;
    }

    public void setAD(int AD) {
        this.AD = AD;
    }

    public Tower (String path, int AD) {
        super(path);
        this.AD = AD;
        this.addListener();
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.prepareMenu();
    }

    /**
     * 初始化弹窗
     */
    private void prepareMenu(){
        //创建菜单选项监听
        MenuItemListener itemListener = new MenuItemListener();
        this.upGradeItem.addActionListener(itemListener);
        this.downGradeItem.addActionListener(itemListener);
        this.sellOutItem.addActionListener(itemListener);
        this.cancelItem.addActionListener(itemListener);

        //添加菜单选项
        MenuContainer.add(upGradeItem);
        MenuContainer.add(downGradeItem);
        MenuContainer.add(sellOutItem);

        //添加分隔符
        MenuContainer.addSeparator();
        MenuContainer.add(cancelItem);
    }

    /**
     * 显示菜单
     */
    public void showMenu(){

    }

    /**
     * 菜单选项监听内部类
     */
    class MenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    public void setArray(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }


    // 設置x,y,r
    public void setCenterXY(double x, double y,double r) {
        this.X = x;
        this.Y = y;
        this.R = r;
    }

    /**
     * 炮塔鼠标监听事件
     */
    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(X +","+ Y);
//                MenuContainer.show(this,(int)(X + 20),(int)Y);
            }
        });
    }

    @Override
    public void run() {
        while (true) {
            //当怪物集合中有怪物时开始判断
            if(monsters.size() > 0 ) {
                for(int i = 0; i < monsters.size(); i++) {
                    //將当前炮塔的x，y，r值放入每個怪物中
                    monsters.get(i).setTower(X,Y,R);
                    //判断每个怪物是否在当前炮塔的攻击范围内
                    monsters.get(i).getCenter();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
