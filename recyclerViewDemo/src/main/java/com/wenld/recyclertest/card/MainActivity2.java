package com.wenld.recyclertest.card;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wenld.commontools.ScreenUtils;
import com.wenld.recyclertest.R;
import com.wenld.recyclertest.card.util.RecyclerScaleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by yuantongqin on 2016/11/16.
 */

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recycler;
    int mDistances = 0;
    private int itemWidth;
    boolean mNoNeedToScroll = false;
    boolean mDragging = false;
    boolean mIdle = false;
    private int itemCount;
    int padding = 15;
    int left_right = 10;
    int mCurrentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recycler.setLayoutManager(manager);
//        manager.findViewByPosition(1);
//        manager.findFirstVisibleItemPosition();
        List<String> mlist = new ArrayList<>();
        String[] images = {"http://desk.fd.zol-img.com.cn/t_s960x600c5/g4/M01/0D/04/Cg-4WVP_npmIY6GRAKcKYPPMR3wAAQ8LgNIuTMApwp4015.jpg",
        "http://img2.3lian.com/2014/f2/164/d/16.jpg",
        "http://www.86ps.com/uploadfiles/jpg/2011-11/2011110210441978760.jpg",
        "http://img3.3lian.com/2013/s1/36/d/106.jpg",
        "http://img2.3lian.com/2014/f2/164/d/22.jpg",
                "http://pic.58pic.com/58pic/10/96/57/16x58PICs3h.jpg",
                "http://pic28.nipic.com/20130413/9903614_120926192107_2.jpg",
                "http://img1.3lian.com/2015/a1/116/d/155.jpg",
                "http://pic18.nipic.com/20111230/9040884_102858425327_2.jpg",
        };
        mlist.addAll(Arrays.asList(images));
        MyRecyclerAdaper adaper = new MyRecyclerAdaper(mlist,this);
        recycler.setAdapter(adaper);
        RecyclerScaleUtils utils = new RecyclerScaleUtils();
        utils.attachToRecyclerView(recycler, ScreenUtils.dip2px(30f));

//        itemCount = recycler.getAdapter().getItemCount();
//        initWidth();
//        addRecyclerListener();
//        //设置卡片居中
//        LinearSnapHelper linearSnapHelper = new LinearSnapHelper(){
//            @Override
//            public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
//
//                if(mNoNeedToScroll){
//                    return new int[]{0,0};
//                } else {
//                    return super.calculateDistanceToFinalSnap(layoutManager, targetView);
//                }
//            }
//        };
//        linearSnapHelper.attachToRecyclerView(recycler);

    }

    public void addRecyclerListener(){

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("tests","==停止状态=="+newState);
                if(newState == recyclerView.SCROLL_STATE_IDLE){
                    if(mDistances == 0 || mDistances == itemCount - 1*itemWidth ){
                        mNoNeedToScroll = true;
                        Log.e("","===进入了吗1111");
                    }else{
                        mNoNeedToScroll = false;
                    }
//                    if(mCurrentPosition == 0 || mCurrentPosition == recycler.getAdapter().getItemCount()-1){
//                        mNoNeedToScroll = true;
//                        Log.e("","===进入了吗2222");
//                    }else{
//                        mNoNeedToScroll = false;
//                    }
                    mDragging = false;
                }else if(newState == recyclerView.SCROLL_STATE_DRAGGING){//dragging
                    mDragging = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动的距离，dx与dy dx>0是朝左边
                if(recyclerView.getLayoutManager().getLayoutDirection() == LinearLayoutManager.HORIZONTAL){
                    mDistances += dx;
                    computeCurrentItemPos();
                    scaleItemView();

                }

            }
        });
    }

    public float scaleItemView(){
        //得到当前item和左右的view
        View leftView = null;
        View rightView = null;
        if(mCurrentPosition > 0){//不是第一个
             leftView = recycler.getLayoutManager().findViewByPosition(mCurrentPosition - 1);
        }
        View currentView = recycler.getLayoutManager().findViewByPosition(mCurrentPosition);
        currentView.setScaleY(itemWidth);
        if(mCurrentPosition < (itemCount - 1)){
            rightView = recycler.getLayoutManager().findViewByPosition(mCurrentPosition + 1);
        }

        //滑动百分比，左右的都是放大，中间缩小
        float percent = Math.abs((mDistances - mCurrentPosition * itemWidth*1.0f)/itemWidth);
        Log.e("tests",itemCount+"==滑动的百分比=="+percent);
        if(leftView != null){
            //这里是缩小原来大小的0.8-1.0 左右0.8，中间1.0   0.8+(percent*0.2)
            leftView.setScaleY(0.8f+(percent*0.2f));
        }
        if(currentView != null){
            currentView.setScaleY(1-0.2f*percent);
        }
        if(rightView != null){
            rightView.setScaleY(0.8f+(percent*0.2f));
        }
        return percent;

    }

    private void initWidth() {
        recycler.post(new Runnable() {
            @Override
            public void run() {
                int Width = recycler.getWidth();
                //实际item的宽度
                itemWidth = Width - 2*(ScreenUtils.dip2px(padding)+ ScreenUtils.dip2px(left_right));
                recycler.smoothScrollToPosition(mCurrentPosition);
                scaleItemView();
            }
        });
    }

    private void computeCurrentItemPos() {
        if (itemWidth <= 0) return;
        boolean pageChanged = false;
        // 滑动超过一页说明已翻页
        if (Math.abs(mDistances - mCurrentPosition * itemWidth) >= itemWidth) {
            pageChanged = true;
            Log.e("tests",Math.abs(mDistances - mCurrentPosition * itemWidth)+"==pageChanged=="+itemWidth);
        }
        Log.e("tests",mDistances+"==mDistances==itemWidth=="+itemWidth);
        if (pageChanged) {
            //以为这里是从0开始的
            mCurrentPosition = mDistances / itemWidth;
        }
        Log.e("tests","==position==111=="+mCurrentPosition);
    }



}
