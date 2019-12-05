package MyInterfaces;

import GameObject.Bullet;
import GameObject.Monster;
import GameObject.Tower;

import java.awt.*;

public interface JudgeHP {
    void getAD(Tower tower);
    void getHP(Monster monster);
    void setHP(Monster monster);
    boolean judgeIntersect(Monster monster, Bullet bullet);
    void calculateHP();
}
