package MyInterfaces;

import GameObject.Monster;
import GameObject.Tower;

/**
 * @author 嘿 林梓鸿
 */
public interface Assaultable {
    void attack(Monster monster);
    void upgrade(Tower tower);
    void destroy(Tower tower);
}
