package GameObject;

import MainClass.MainGameJframe;
import MyClass.GameMusic;
import MyClass.MyImgJpanel;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 炮塔类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月02日 23:37:25
 */
public class Tower extends GameObject implements Runnable{



    boolean updateflag = false;//更新面板的显示信号
    boolean removeflag = false;// 移除面板的显示信号
    UpdatePanel updatePanel;
    RemovePanel removePanel;
    volatile private boolean DEATHFLAG = false; //炮塔移除的信号

    boolean isPutDownFlag = false;
    private ImageIcon img;
    private String path;
    private Bullet bullet ;
    private double degree;
    private Image iBuffer;
    private Graphics gBuffer;
    private Monster m;

    public Bullet getBullet() {
        return bullet;
    }

    public int getAD() {
        return AD;
    }

    public void setAD(int AD) {
        this.AD = AD;
    }

    int AD = 20;
    //攻击力

    double R;
    //攻击半径

    int Level;
    // 等级

    int Num;

    int number;

    ArrayList<Monster> monsters;
    private int size;


//    ImageIcon Img = new ImageIcon("Image/tower.png");
    //炮塔图片


//,int level,int x,int y,int w,int h,int num

    public Tower (String path) {
        super(path);
        this.path = path;
        this.addListener();
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
////        super();
//        this.Level = level;
//        if(level == 1) {
//            this.AD = 34;
//            this.X = x;
//            this.Y = y;
//            this.Num = num;
//        }
//        new Thread(this).start();

    }
    @Override
    protected void paintComponent(Graphics g){
        img = new ImageIcon(path);
//        File file = new File(path);//本地图片

//        try {
//            BufferedImage res=(BufferedImage) ImageIO.read(file);
//            BufferedImage res=(BufferedImage) img.getImage();
            Graphics2D g2 = (Graphics2D)g;
            g2.rotate(degree,getWidth()/2,getHeight()/2);
            g2.drawImage(img.getImage(),0,0,getWidth(),getHeight(),null);
        this.setVisible(true);
        this.setBackground(null);
        this.setOpaque(false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    @Override
    public void update(Graphics scr){
//        img = new ImageIcon(path);
//        iBuffer=createImage(getWidth(),getHeight());
//
//        gBuffer=iBuffer.getGraphics();
//        gBuffer = (Graphics2D)gBuffer;
//        gBuffer.drawImage(img.getImage(),0,0,getWidth(),getHeight(),null);
        this.setVisible(false);
        this.paintComponent(scr);
    }

    /**
     * 升级按钮初始化
     * @param updatePanel
     */
    public void setUpdatePanel(UpdatePanel updatePanel) {
        this.updatePanel = updatePanel;
        this.updatePanel.setT(this);
        this.updatePanel.setBounds((int)X + MAX_BGWIDTH / 2,(int)Y - 50 - 2,MAX_BGWIDTH - 20,MAX_BGWIDTH - 20);
        this.updatePanel.setVisible(updateflag);
    }

    /**
     * 移除模块初始化
     * @param removePanel
     */
    public void setRemovePanel(RemovePanel removePanel) {
        this.removePanel = removePanel;
        this.removePanel.setT(this);
        this.removePanel.setBounds((int)X + MAX_BGWIDTH / 2,(int)Y + 2,MAX_BGWIDTH - 20,MAX_BGWIDTH - 20);
        this.removePanel.setVisible(removeflag);
    }


    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
        this.bullet.setXY((int)this.X,(int)this.Y);
    }

    public void setArray(ArrayList<Monster> monsters) {
        this.monsters = monsters;
         size= monsters.size();
    }


    // 設置x,y,r
    public void setCenterXY(double x, double y,double r) {
        this.X = x;
        this.Y = y;
        this.R = r;
    }

    public void setDEATHFLAG(boolean DEATHFLAG) {
        this.DEATHFLAG = DEATHFLAG;
    }


    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //repaint();
//                System.out.println("Dianji");
                /**
                 * 若金币不够，则显示灰色升级按钮
                 */
                if(Integer.valueOf(MainGameJframe.money.getText()) >= 40) {
                    updatePanel.setPath("Image/update01.png");
                } else {
                    updatePanel.setPath("Image/update02.png");
                }
                /**
                 * 炮塔对更新和移除面板的操作
                 */
                updateflag = !updateflag;
                removeflag = !removeflag;
                updatePanel.setVisible(updateflag);
                removePanel.setVisible(removeflag);
            }
        });
    }

    @Override
    public void run() {
        while (!DEATHFLAG) {

            if(monsters.size() > 0 ) {
                //当怪物集合中有怪物时开始判断
                int i = 0;
                    while (i < monsters.size()) {
                        monsters.get(i).setTower(X, Y, R);
                        //將当前炮塔的x，y，r值放入每個怪物中
                        /**
                         * 判断每个怪物是否在当前炮塔的攻击范围内
                         */
                        if (monsters.get(i).getCenter()) {
                            m = monsters.get(i);
                            double linbian = Math.abs(m.getMonsterY() - this.Y);
                            double duibian = Math.abs(m.getMonsterX() - this.X);
                            double xiebian = Math.sqrt(linbian * linbian + duibian * duibian);
                                if (m.getMonsterY() < this.Y && m.getMonsterX() < this.X) {
                                    this.degree = (-1) * Math.atan(Math.abs(duibian / linbian));
                                } else if (m.getMonsterY() < this.Y && m.getMonsterX() > this.X) {
                                    this.degree = Math.atan(Math.abs(duibian / linbian));
                                } else if (m.getMonsterY() > this.Y && m.getMonsterX() > this.X) {
                                    this.degree = Math.PI - Math.atan(Math.abs(duibian / linbian));
                                } else if (m.getMonsterY() > this.Y && m.getMonsterX() < this.X) {
                                    this.degree = Math.PI + Math.atan(Math.abs(duibian / linbian));
                                }
                                if (DEATHFLAG){
                                    break;
                                }
                            this.paintComponent(this.getGraphics());
                                this.bullet.setAD(AD);
                                if (this.bullet.getFlag() == true) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            GameMusic.Play("bgm/shot.wav");
                                        }
                                    }).start();
                                    this.bullet.setXY((int) this.X, (int) this.Y);
                                    this.bullet.setMonster(m);
                                    this.bullet.setVisible(true);
                                    this.bullet.startAD();
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            i = 0;
                    }
                        else {
                            i++;
                        }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!DEATHFLAG&&degree!=0){
                    this.degree = 0;
                    this.paintComponent(super.getGraphics());
                }
            }

        }
        System.out.println("线程已总结");
    }

    public boolean getDEATH() {
        return DEATHFLAG;
    }
}
