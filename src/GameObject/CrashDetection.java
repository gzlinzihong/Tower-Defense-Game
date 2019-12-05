package GameObject;

import MyInterfaces.JudgeHP;

import java.util.ArrayList;

/**
 * 碰撞检测类
 *
 * @author 嘿 林梓鸿
 * @date 2019年 12月05日 11:43:22
 */
public class CrashDetection implements JudgeHP {
    private int AD;
    private int HPX;
    private int HPY;
    private Monster monster;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public void initObject(Monster monster,ArrayList<Bullet> bullet){
        this.monster = monster;
        this.bullets = bullet;
    }

    public CrashDetection(){
    }
    @Override
    public void getAD(Tower tower) {
        AD = tower.getAD();
    }

    @Override
    public void getHP(Monster monster) {
        HPX = monster.getHPX();
        HPY = monster.getHPY();
    }

    @Override
    public void setHP(Monster monster) {
        monster.setHPX(HPX);
        monster.setHPY(HPY);
    }

    @Override
    public boolean judgeIntersect(Monster monster, Bullet bullet) {
        return monster.getRect().intersects(bullet.getRect());
    }

    @Override
    public void calculateHP() {
        this.HPX = this.HPX - this.AD;
    }
}
