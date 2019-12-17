package GameObject;

/**
 * 常量类
 */
public class Constant {
    public static int PlayerHP = 100;                       // 玩家血量，便于设置血量容器里的值
    public static int Money = 0;                            // 金币数量，便于设置金币容器里的值
    public static final int MAX_LEFT = 143;                 // 可放置炮塔的最左部x值
    public static final int MAX_RIGHT = 1059;               // 可放置炮塔的最右部x值
    public static final int MAX_TOP = 171;                  // 可放置炮塔的最顶部y值
    public static final int MAX_BOTTOM = 662;               // 可放置炮塔的最底部y值
    public static final int MAX_BGWIDTH = 70;               // 每个方块的像素长度
    public static final int RELATIVE_LOCATION = 141;        // 炮塔相对于左上角第一个塔座的距离
    public static final int MAP1_COORD[][] = {
            //地图转弯处标记点
            {1050,1},
            {143,2},
            {985,3},
            {143,2},
            {985,1},
            {143,2},
            {985,3},
            {143,2},
            {930,1}
    };
    public static final int MAP1_MONSTERS[][] = {
            //怪物生成的数量以及每个怪物出现的时间间隔
            {5,6000,7000},
            {16,4000,5000},
            {4,7000,9000},
            {20,2000,4000},
            {10,6000,7000},
            {30,2000,4000}
    };
    public static final String FATTY_PATH[] = {
            "Image/fatty1.gif",
            "Image/fatty2.gif"
    };
    public static final String WOLFMAN_PATH[] = {
            "Image/wolfman1.gif",
            "Image/wolfman2.gif"
    };
    public static final String DRAGON_PATH[] = {
            "Image/dragon1.gif",
            "Image/dragon2.gif"
    };
}
