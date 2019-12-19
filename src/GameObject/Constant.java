package GameObject;

import java.awt.*;

/**
 * 常量类
 */
public class Constant {
    public final static int sceenwidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final static int sceenheight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int MAP1_COORD[][] = {
            //地图转弯处标记点
            {(int)((double)sceenwidth*0.68),1},
            {(int)((double)sceenheight*0.14),2},
            {(int)((double)sceenwidth*0.64),3},
            {(int)((double)sceenheight*0.16),2},
            {(int)((double)sceenwidth*0.64),1},
            {(int)((double)sceenheight*0.16),2},
            {(int)((double)sceenwidth*0.64),3},
            {(int)((double)sceenheight*0.135),2},
            {(int)((double)sceenwidth*0.64),1}
//            (int)((double)sceenwidth*0.51)
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
