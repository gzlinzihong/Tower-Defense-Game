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
    private ImageIcon img;
    private Image iBuffer;
    private Graphics gBuffer;
    public MyImgJpanel(String path){
        this.path = path;
    }



    @Override
    protected void paintComponent(Graphics g){
        img = new ImageIcon(path);
        g.drawImage(img.getImage(),0,0,getWidth(),getHeight(),null);
    }

    @Override
    public void update(Graphics scr)
    {
        iBuffer=createImage(getWidth(),getHeight());
        gBuffer=iBuffer.getGraphics();
        gBuffer.drawImage(new ImageIcon(path).getImage(),0,0,getWidth(),getHeight(),null);
        paint(gBuffer);
        scr.drawImage(iBuffer,0,0,this);
    }

    public void setPath(String path) {
        this.path = path;
    }
}
