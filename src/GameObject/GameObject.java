package GameObject;

import MyClass.MyImgJpanel;

import javax.swing.*;
import java.awt.*;

/**
 * 模型总类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月02日 23:36:52
 */
public class GameObject extends MyImgJpanel {
     static final int MAX_LEFT = 140;
     static final int MAX_TOP = 170;
     static final int MAX_BGWIDTH = 70;

    int X;//x坐标
    int Y;//y坐标
    int width;//宽度
    int height;//高度

    public GameObject(String path) {
        super(path);
    }

    public Rectangle getRect() {
        return new Rectangle(X,Y,width,height);
    }


}
