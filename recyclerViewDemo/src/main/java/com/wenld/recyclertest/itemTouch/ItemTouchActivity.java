package com.wenld.recyclertest.itemTouch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import com.wenld.commontools.AllUtilConfig;
import com.wenld.recyclertest.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ItemTouchActivity extends AppCompatActivity implements ItemTouchMoveListener, StartDragListener {
    public RecyclerView rlvAtyFilter;
    CommonAdapter adapter;
    List<String> list = new ArrayList<>();
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemtouch);
        AllUtilConfig.LogSwitch = true;
//        getActionBar().setTitle("自定义View");
        for (int i = 0; i < 50; i++) {
            list.add("item__" + i);
        }


        this.rlvAtyFilter = (RecyclerView) findViewById(R.id.rlv_activity_itemtouch);

        adapter = new CommonAdapter<String>(this, R.layout.list_items, list) {
            @Override
            protected void convert(ViewHolder holder, final String s, final int position) {
                TextView btn = holder.getView(R.id.btn);
                btn.setText(s);
            }
        };
        rlvAtyFilter.setLayoutManager(new LinearLayoutManager(this));
        rlvAtyFilter.setAdapter(adapter);
//条目触摸帮助类
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rlvAtyFilter);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        // 1.数据交换；2.刷新
        Collections.swap(list, fromPosition, toPosition);
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public boolean onItemRemove(int position) {
        list.remove(position);
        adapter.notifyItemRemoved(position);
        return true;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
