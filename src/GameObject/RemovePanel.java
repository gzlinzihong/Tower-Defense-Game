package GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 炮塔移除
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月15日 18:00:22
 */

public class RemovePanel extends GameObject{
    private Tower t;
    private boolean Click = false;

    public RemovePanel(){
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.addListener();
    }

    public void setT(Tower t) {
        this.t = t;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(new ImageIcon("Image/remove01.png").getImage(),0,0,getWidth(),getHeight(),null);
    }

    public void addListener(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println(Click);
                // 点击一次之后隐藏面板
                t.setDEATHFLAG(true);
                t.setVisible(false);
                t.updateflag = !t.removeflag;
                t.removeflag = !t.removeflag;
                t.updatePanel.setVisible(t.updateflag);
                t.removePanel.setVisible(t.removeflag);

            }
        });
    }

}
