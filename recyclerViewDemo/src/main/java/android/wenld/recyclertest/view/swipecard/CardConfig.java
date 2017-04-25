package android.wenld.recyclertest.view.swipecard;

import android.content.Context;

/**
 * Created by wenld on 2017/3/30.
 */

public class CardConfig {
    public static int MAX_SHOW_COUNT;

    public static float SCALE_GAP;
    public static int TRANS_Y_GAP;

    public static void initConfig(Context context) {
        MAX_SHOW_COUNT = 4;
        SCALE_GAP = 0.05f;
        TRANS_Y_GAP = 50;
    }
}
