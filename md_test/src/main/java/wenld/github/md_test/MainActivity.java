package wenld.github.md_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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
    private DrawerLayout drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        this.rlvAtyFilter = (RecyclerView) findViewById(R.id.rlv_activity_main);

//        getActionBar().setTitle("自定义View");
        list.add(new ItemClass("TextInputLayout效果", TextInputLayoutActivity.class));
        list.add(new ItemClass("NavigationBar", NavigationActivity.class));
        list.add(new ItemClass("Snackbar", SnackbarActivity.class));
        list.add(new ItemClass("ToolBar", ToolBarActivity.class));
        list.add(new ItemClass("CoordinatorLayoutActivity", CoordinatorLayoutActivity.class));
        list.add(new ItemClass("BehaviorActivity_1 自定义",BehaviorActivity_1.class));

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
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, String o, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, String o, int position) {
                return false;
            }
        });
        drawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerStateChanged(int newState) {
                // 状态发生改变

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // 滑动的过程当中不断地回调 slideOffset：0~1
//                View content = drawerlayout.getChildAt(0);
//                View menu = drawerView;
//                float scale = 1-slideOffset;//1~0
//                float leftScale = (float) (1-0.3*scale);
//                float rightScale = (float) (0.7f+0.3*scale);//0.7~1
//                menu.setScaleX(leftScale);//1~0.7
//                menu.setScaleY(leftScale);//1~0.7
//
//                content.setScaleX(rightScale);
//                content.setScaleY(rightScale);
//                content.setTranslationX(menu.getMeasuredWidth()*(1-scale));//0~width
                float scale = 1 - slideOffset;//1~0
                rlvAtyFilter.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));

//                    float scale = 1 - slideOffset;//1~0
//                    rlvAtyFilter.setTranslationX(drawerView.getMeasuredWidth() * (-1 + scale));
//                }*/
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // 打开

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // 关闭

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
