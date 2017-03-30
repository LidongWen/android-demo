package com.wenld.recyclertest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Author: 温利东 on 2017/3/30 16:42.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class RecyclerViewCardDragActivity extends Activity {
    public RecyclerView rlvAtyFilter;
    CommonAdapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rlv);
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        adapter = new CommonAdapter<String>(this, R.layout.list_larger_img, list) {
            @Override
            protected void convert(ViewHolder holder, final String s, final int position) {
            }
        };
        this.rlvAtyFilter = (RecyclerView) findViewById(R.id.rlv_activity_rlv_vp);
        rlvAtyFilter.setLayoutManager(new SwipeCardLayoutManager());
        rlvAtyFilter.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, String o, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, String o, int position) {
                return false;
            }
        });


        CardConfig.initConfig(this);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipCardCallback(rlvAtyFilter,adapter,list));
        // 这个就不多解释了，就这么attach
        itemTouchHelper.attachToRecyclerView(rlvAtyFilter);
    }


    public class SwipCardCallback extends ItemTouchHelper.SimpleCallback {
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        List mDatas;

        public SwipCardCallback( RecyclerView recyclerView, RecyclerView.Adapter adapter, List datas) {
            super(0, ItemTouchHelper.UP | ItemTouchHelper.LEFT |
                    ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT);
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
            Object obj=mDatas.remove(viewHolder.getAdapterPosition());
            mDatas.add(0,obj);
            mAdapter.notifyDataSetChanged();
        }
    }
}
