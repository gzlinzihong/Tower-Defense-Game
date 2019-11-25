package MyClass;

import javax.swing.*;
import java.awt.*;

/**
 * 图片面板类
 * path 图片路径
 * @author 嘿 林梓鸿
 * @date 2019年 11月25日 17:56:04
 */
public class MyImgJpanel extends JPanel {
    private String path;
    public MyImgJpanel(String path){
        this.path = path;
    }



    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon img = new ImageIcon(path);
        g.drawImage(img.getImage(),0,0,getWidth(),getHeight(),null);
    }

}
