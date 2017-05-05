package com.wenld.largeimageview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public RecyclerView rlvAtyFilter;
    CommonAdapter adapter;
    List<ItemClass> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.rlvAtyFilter = (RecyclerView) findViewById(R.id.rlv_activity_main);

//        getActionBar().setTitle("自定义View");
        list.add(new ItemClass("缩放", ScaleViewActivity.class));
//        list.add(new ItemClass("NavigationBar", NavigationActivity.class));
//        list.add(new ItemClass("Snackbar", SnackbarActivity.class));
//        list.add(new ItemClass("ToolBar", ToolBarActivity.class));
//        list.add(new ItemClass("CoordinatorLayoutActivity", CoordinatorLayoutActivity.class));
//        list.add(new ItemClass("BehaviorActivity_1 自定义", BehaviorActivity_1.class));
//        list.add(new ItemClass("BottomSheetDialogActivity 自定义", BottomSheetDialogActivity.class));
//        list.add(new ItemClass("水波纹效果", AnimatorActivity.class));

        adapter = new CommonAdapter<ItemClass>(this, R.layout.list_items, list) {
            @Override
            protected void convert(ViewHolder holder, final ItemClass s, final int position) {
                TextView btn = holder.getView(R.id.btn);
                btn.setText(s.name);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, s.className);
                        if (intent != null) {
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        rlvAtyFilter.setLayoutManager(new LinearLayoutManager(this));
        rlvAtyFilter.setAdapter(adapter);
        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ItemClass>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, ItemClass o, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, ItemClass o, int position) {
                return false;
            }
        });
    }

    public class ItemClass {
        public String name;
        public Class className;

        public ItemClass(String name, Class className) {
            this.name = name;
            this.className = className;
        }
    }
}
