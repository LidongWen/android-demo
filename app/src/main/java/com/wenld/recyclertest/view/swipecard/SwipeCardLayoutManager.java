package com.wenld.recyclertest.view.swipecard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wenld on 2017/3/30.
 */

public class SwipeCardLayoutManager extends RecyclerView.LayoutManager {
    int itemCount;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        super.onLayoutChildren(recycler, state);
        /**
         *   Recycler类  布局复用
         *   ViewHolder重用 缓冲： Scrap(废料 List)  detach、attach
         */
        //在布局之前，将所有的字view全部detach ,放入Scrap集合中缓存
        detachAndScrapAttachedViews(recycler);
        itemCount = getItemCount();
        int bottomPostion;
        if (itemCount < 1)
            return;
        if (itemCount < CardConfig.MAX_SHOW_COUNT) {
            bottomPostion = 0;
        } else {
            bottomPostion = itemCount - CardConfig.MAX_SHOW_COUNT;
        }

        //让上面4个view 添加到RecyclerView 容器内
        for (int postion = bottomPostion; postion < itemCount; postion++) {
            View view = recycler.getViewForPosition(postion);
            addView(view);
            measureChildWithMargins(view, 0, 0);

            int widthSpce = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            //摆放位置
            layoutDecorated(view,
                    widthSpce / 2,
                    heightSpace / 2,
                    widthSpce / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));


            //层叠错开  代表页面上展示   0 为第一层
            int level = itemCount - postion - 1;
//            Log.e("aaa:","postion:"+postion+"  level:"+level);
            if (level > 0) {
                view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                //上面三个
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level));
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level));
                } else {
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level-1));
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level-1));
                }
            }
        }
    }
}
