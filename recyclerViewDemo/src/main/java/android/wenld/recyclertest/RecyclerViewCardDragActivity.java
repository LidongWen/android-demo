package android.wenld.recyclertest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import android.wenld.recyclertest.view.swipecard.CardConfig;
import android.wenld.recyclertest.view.swipecard.SwipeCardCallback;
import android.wenld.recyclertest.view.swipecard.SwipeCardLayoutManager;
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
    public RecyclerView mRv;
    CommonAdapter adapter;
    List<String> list = new ArrayList<>();


    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipecard);

        textView= (TextView) findViewById(R.id.tvMoreText);

        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        adapter = new CommonAdapter<String>(this, R.layout.list_larger_img, list) {
            @Override
            protected void convert(ViewHolder holder, final String s, final int position) {
            }
        };
        this.mRv = (RecyclerView) findViewById(R.id.rlv_activity_swipeCard);
        mRv.setLayoutManager(new SwipeCardLayoutManager());
        mRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, String o, int position) {
                textView.setText(position+" ");
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, String o, int position) {
                return false;
            }
        });


        CardConfig.initConfig(this);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeCardCallback(mRv,adapter,list));
        // 这个就不多解释了，就这么attach
        itemTouchHelper.attachToRecyclerView(mRv);
    }

}
