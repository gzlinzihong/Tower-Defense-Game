package MyClass;

import javax.swing.*;
import java.awt.*;

/**
 * GIF图片加载器
 *
 * @author 嘿 林梓鸿
 * @date 2019年 11月29日 16:57:50
 */
public class MyGif extends JLabel {
    private ImageIcon Icon;
    private int width;
    private int height;
    private Image iBuffer;
    private Graphics gBuffer;

    public MyGif(String path,int width,int height){
        this.width = width;
        this.height = height;
        Icon = new ImageIcon(path);
        this.setIcon(Icon);

        Icon.setImage(Icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT ));
    }

    @Override
    public void update(Graphics scr)
    {
        if(iBuffer==null)
        {
            iBuffer=createImage(this.getSize().width,this.getSize().height);
            gBuffer=iBuffer.getGraphics();
        }
        gBuffer.fillRect(0,0,this.getSize().width,this.getSize().height);
        paint(gBuffer);
        scr.drawImage(iBuffer,0,0,null);
    }
}
