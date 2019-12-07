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

    double X;//x坐标
    double Y;//y坐标
    double width;//宽度
    double height;//高度
    int NUMBER;

    public GameObject(String path) {
        super(path);
    }
    public GameObject() {
        super();
    }

    public Rectangle getRect() {
        return new Rectangle((int)X,(int)Y,(int)width,(int)height);
    }

//    public Boolean getCenter(double x,double y,int r) {
//        if (Math.sqrt(Math.pow((this.X - x), 2)+Math.pow(this.Y - y, 2)) < (r + Math.sqrt(MAX_BGWIDTH)) ){
//            return true;
//        } else {
//            return false;
//        }
//    }

    public void getCenter() {

    }


}
