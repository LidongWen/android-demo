package com.wenld.recyclertest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewToViewPageActivity extends AppCompatActivity {
    public RecyclerView rlvAtyFilter;
    CommonAdapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rlv);

//        getActionBar().setTitle("自定义View");
        for (int i = 0; i < 10; i++) {
            list.add("");
        }

        this.rlvAtyFilter = (RecyclerView) findViewById(R.id.rlv_activity_rlv_vp);
        adapter = new CommonAdapter<String>(this, R.layout.list_img, list) {
            @Override
            protected void convert(ViewHolder holder, final String s, final int position) {
            }
        };
        rlvAtyFilter.setLayoutManager(new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, false));
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

        LinearSnapHelper mLinearSnapHelper = new LinearSnapHelper();
        mLinearSnapHelper.attachToRecyclerView(rlvAtyFilter);


        // 实现左边侧滑删除 和 拖动排序
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // 获取触摸响应的方向   包含两个 1.拖动dragFlags 2.侧滑删除swipeFlags
                // 代表只能是向左侧滑删除，当前可以是这样ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
                int swipeFlags = ItemTouchHelper.LEFT;

                // 拖动
                int dragFlags = 0;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    // GridView 样式四个方向都可以
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.LEFT |
                            ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT;
                } else {
                    if (recyclerView.getLayoutManager().canScrollVertically()) {
                        dragFlags = ItemTouchHelper.UP |
                                ItemTouchHelper.DOWN;
                    } else {
                        dragFlags = ItemTouchHelper.LEFT |
                                ItemTouchHelper.RIGHT;
                    }
                }

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            /**
             * 拖动的时候不断的回调方法
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // 获取原来的位置
                int fromPosition = viewHolder.getAdapterPosition();
                // 得到目标的位置
                int targetPosition = target.getAdapterPosition();
                if (fromPosition > targetPosition) {
                    for (int i = fromPosition; i < targetPosition; i++) {
                        Collections.swap(list, i, i + 1);// 改变实际的数据集
                    }
                } else {
                    for (int i = fromPosition; i > targetPosition; i--) {
                        Collections.swap(list, i, i - 1);// 改变实际的数据集
                    }
                }
                adapter.notifyItemMoved(fromPosition, targetPosition);
                return true;
            }

            /**
             * 侧滑删除后会回调的方法
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 获取当前删除的位置
                int position = viewHolder.getAdapterPosition();
                list.remove(position);
                // adapter 更新notify当前位置删除
                adapter.notifyItemRemoved(position);
            }

            /**
             * 拖动选择状态改变回调
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    // ItemTouchHelper.ACTION_STATE_IDLE 看看源码解释就能理解了
                    // 侧滑或者拖动的时候背景设置为灰色
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            }


            /**
             * 回到正常状态的时候回调
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // 正常默认状态下背景恢复默认
                viewHolder.itemView.setBackgroundColor(0);
                ViewCompat.setTranslationX(viewHolder.itemView, 0);
            }
        });
        // 这个就不多解释了，就这么attach
        itemTouchHelper.attachToRecyclerView(rlvAtyFilter);


//        rlvAtyFilter.addItemDecoration(new AutoGridDeciration(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_gridview:
                rlvAtyFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                break;
            case R.id.id_action_listview:
                rlvAtyFilter.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
        return true;
    }
}
