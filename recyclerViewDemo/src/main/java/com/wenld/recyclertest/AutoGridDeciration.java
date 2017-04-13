package com.wenld.recyclertest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * <p/>
 * Author: 温利东 on 2017/3/30 17:26.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */
public class AutoGridDeciration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public AutoGridDeciration(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider_bg02);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizonal(c, parent);
        drwaVerial(c, parent);

    }


    /**
     * 水平方向上面的绘制
     *
     * @param c
     * @param parent
     */
    private void drawHorizonal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = parent.getChildAt(i);
            final RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            final int left = childView.getLeft() - layoutParams.leftMargin;
            final int right = childView.getRight() + layoutParams.rightMargin + mDivider.getIntrinsicWidth();
            final int top = childView.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    /**
     * 竖直方向上面的线断
     *
     * @param c
     * @param recyclerView
     */
    public void drwaVerial(Canvas c, RecyclerView recyclerView) {
        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = recyclerView.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int bottom = child.getBottom() + params.bottomMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    //
    private boolean isLastColum(RecyclerView parent, View view, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            int position = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
            if ((position + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int positon = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
            if ((positon + 1) % childCount == 0) {
                return true;
            } else {
                childCount = childCount - spanCount / childCount;
                if (positon >= childCount) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * \
     * 判断是不是最后一行，竖直方向的走势
     *
     * @param parent     RecyclerView 对象
     * @param view       当前的View的对象
     * @param spanCount  一行或者一列的个数
     * @param childCount adapter的总的个数。
     * @return
     */
    private boolean isLastRaw(RecyclerView parent, View view, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //当前View的position;
        int position = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        if (layoutManager instanceof GridLayoutManager) {
            //取余数
            childCount = childCount - childCount % spanCount;
            //如何判断当前的position是最后一个position>??
            //大于是不可能的但是如果当
            if (position >= childCount) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int oriation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            //如果是水平方向的话
            if (oriation == LinearLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (position >= childCount)
                    return true;
            } else {
                //表示最后一条数据
                if ((position + 1) % childCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent != null) {
//            int position = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int spanCount = getSpanCountByParent(parent);
            int childCount = parent.getAdapter().getItemCount();
            if (isLastColum(parent, view, spanCount, childCount)) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else if (isLastRaw(parent, view, spanCount, childCount)) {
                outRect.set(0, 0, 2, 0);
            } else {
                outRect.set(0, 0, 2, mDivider.getIntrinsicHeight());
            }

        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }


    }

    /**
     * 通过RecyclerView来获取
     *
     * @param parent
     * @return
     */
    private int getSpanCountByParent(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof GridLayoutManager) {
                spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            }
        }
        return spanCount;
    }

}