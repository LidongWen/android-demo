package wenld.github.md_test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Author: wenld on 2017/4/25 15:31.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class BottomSheetDialogActivity extends AppCompatActivity {
    private BottomSheetDialog mBottomSheetDialog;
    private List<String> list;
    Button buttona, button;
    BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheetdialog);
        buttona = (Button) findViewById(R.id.btn_bottom_sheet_control);
        button = (Button) findViewById(R.id.btn_aty_bsd);


        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_layout));

        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("");
        }
        createBottomSheetDialog();
        buttona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetDialog.isShowing()) {
                    mBottomSheetDialog.dismiss();

                } else {
                    mBottomSheetDialog.show();
                }
            }
        });

    }

    private void createBottomSheetDialog() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null, false);
        mBottomSheetDialog.setContentView(view);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.list_items, list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        });
        setBehaviorCallback();
    }

    private void setBehaviorCallback() {
        View view = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }
}
