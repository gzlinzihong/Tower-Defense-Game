package GameObject;

import MainClass.MainGameJframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 炮塔升级
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月15日 18:01:23
 */
public class UpdatePanel extends GameObject {
    private String path = "Image/update01.png";
    private Tower t;
    public UpdatePanel() {
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//        new Thread(this).start();
        this.addListener();
    }

//    private Boolean show = false;

    //    public void setShow() {
//        this.show = !this.show;
//        System.out.println(this.show);
//    }
//
    public void setT(Tower t) {
        this.t = t;
    }


    @Override
    public void setPath(String path) {
        this.path = path;
    }

    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(Integer.valueOf(MainGameJframe.money.getText()) > 40) {
                    /**
                     * 扣除金币进行升级，加20攻击力
                     */
                    MainGameJframe.money.setText(String.valueOf(Integer.valueOf(MainGameJframe.money.getText()) - 40));
                    t.AD += 20;

                    // 点击一次之后隐藏面板
                    t.updateflag = !t.removeflag;
                    t.removeflag = !t.removeflag;
                    t.updatePanel.setVisible(t.updateflag);
                    t.removePanel.setVisible(t.removeflag);
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(new ImageIcon(path).getImage(),0,0,getWidth(),getHeight(),null);
    }

//    public void run() {
//        while (true) {
////            if(Integer.valueOf(MainGameJframe.money.getText()) >= 40) {
////                path = "Image/update01.png";
////            } else {
////                path = "Image/update02.png";
////            }
//        }
//    }
}
