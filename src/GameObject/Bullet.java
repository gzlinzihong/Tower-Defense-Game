package GameObject;

import java.awt.*;

/**
 * 炮塔子弹类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月05日 11:24:13
 */
public class Bullet extends GameObject implements Runnable{
    private double degree;
    int test_X;
    int test_Y;
    public Bullet(int x, int y) {
        super();
        this.test_X = x;
        this.test_Y = y;
        this.X = 10;
        this.Y = 10;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

//    @Override
//    public void paint(Graphics g) {
//        Color c = g.getColor();
//        super.paint(g);
//        g.fillRect(X,Y,5,10);
//        g.setColor(Color.RED);
//        g.setColor(c);
//    }

    @Override
    public void run() {
            this.X += 1;
            this.Y += 1;
            System.out.println(this.X+","+this.Y);
            repaint();
    }

//    public void test() {
//        while(this.X < test_X) {
//            this.X += 10;
//            System.out.println(this.X);
//        }
//    }

    @Override
    public void paint(Graphics g) {

    }
}
