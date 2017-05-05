package com.wenld.largeimageview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wenld.largeimageview.view.MyScaleView;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p/>
 * Author: wenld on 2017/5/5 15:40.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class ScaleViewActivity extends AppCompatActivity {
    private MyScaleView scaleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaleview);
        initView();
        try
        {
            InputStream inputStream = getAssets().open("larger_image.jpg");
            scaleView.setInputStream(inputStream);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
//        scaleView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
    }

    private void initView() {
        scaleView = (MyScaleView) findViewById(R.id.scaleView);
    }
}
