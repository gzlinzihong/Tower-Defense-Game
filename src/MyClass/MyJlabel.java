package MyClass;

import MainClass.MainGameJframe;

import javax.swing.*;
import java.awt.*;

/**
 * 生成JLabel
 *
 * @author 嘿 林梓鸿
 * @date 2019年 11月25日 17:54:29
 */
public class MyJlabel extends JLabel {
    /**
     * screenWidth -> 屏幕宽度
     */
    private final int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

    /**
     * screenHeight -> 屏幕高度
     */
    private final int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    /**
     *  参数表
     *  init -> 初始值
     *  x -> screenwith - x = x轴位置
     *  y -> y轴位置
     *  width -> 宽度
     *  height -> 高度
     */
    public MyJlabel(MainGameJframe game, String init, int x, int y, int width, int height){
        super(init);
        setFont(new Font("Serief",Font.BOLD, 30));
        setBounds(screenWidth-x,y,width,height);
        game.add(this);
    }
}
