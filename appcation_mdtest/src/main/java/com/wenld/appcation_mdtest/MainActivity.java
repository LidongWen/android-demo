package com.wenld.appcation_mdtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(value = {R.id.tv})
    public void onclick() {
//        Intent intent=new Intent(this, wenld.github.md_test.MainActivity.class);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("wenld://wenld.github.md_test/activity_main"));
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("wenld://wenld.github.base_test/testAty"));
        startActivity(intent);
    }
}
