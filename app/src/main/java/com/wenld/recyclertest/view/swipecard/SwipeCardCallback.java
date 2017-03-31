package com.wenld.recyclertest.view.swipecard;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

/**
 * <p/>
 * Author: 温利东 on 2017/3/31 10:45.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */


public class SwipeCardCallback extends ItemTouchHelper.SimpleCallback {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List mDatas;

    public SwipeCardCallback(RecyclerView recyclerView, RecyclerView.Adapter adapter, List datas) {
        super(0, /*ItemTouchHelper.UP |*/ ItemTouchHelper.LEFT |
               /* ItemTouchHelper.DOWN |*/ ItemTouchHelper.RIGHT);
        this.mRecyclerView = recyclerView;
        this.mAdapter = adapter;
        this.mDatas = datas;

    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //滑动删除
        Object obj = mDatas.remove(viewHolder.getAdapterPosition());
        mDatas.add(0, obj);
        mAdapter.notifyDataSetChanged();
    }

    double getThreshold() {
        return mRecyclerView.getWidth() / 2;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        //监听滑动child变化 --- 控制动画效果
        //零界点：比例系数;
        double fraction = dX / getThreshold();
        if (fraction > 1) {
            fraction = 1;
        }

        viewHolder.itemView.setRotation((float) (fraction * 15));


        int childCount = recyclerView.getChildCount();//得到展示view的数量
        View child;
        int level;
        for (int i = 0; i < childCount; i++ ) {
            child = recyclerView.getChildAt(i);

            level = childCount - i - 1;
            if (level > 0) {
                child.setScaleX((float) (1 - CardConfig.SCALE_GAP * level+ fraction*CardConfig.SCALE_GAP));
                //上面 2 个
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    child.setTranslationY((float) (CardConfig.TRANS_Y_GAP * (level)-fraction*CardConfig.TRANS_Y_GAP));
                    child.setScaleY((float) (1 - CardConfig.SCALE_GAP * (level)+fraction*CardConfig.SCALE_GAP));
                }
            }
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setRotation(0f);
        super.clearView(recyclerView, viewHolder);

    }
}