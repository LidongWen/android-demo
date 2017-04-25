package wenld.github.md_test;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

/**
 * <p/>
 * Author: wenld on 2017/4/25 16:18.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class AnimatorActivity extends AppCompatActivity {
    private Button bt1;
    private Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createCircularReveal_1();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                createCircularReveal();

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void createCircularReveal() {
        Animator animator = ViewAnimationUtils.createCircularReveal(bt2, 0, 0, 0, (float) Math.hypot(bt2.getWidth(), bt2.getHeight()));
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void createCircularReveal_1() {
        Animator animator = ViewAnimationUtils.createCircularReveal(bt1, bt1.getWidth() / 2, bt1.getHeight() / 2, 0, bt1.getHeight());
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    //圆形水波纹揭露效果
//				ViewAnimationUtils.createCircularReveal(
//						view, //作用在哪个View上面
//						centerX, centerY, //扩散的中心点
//						startRadius, //开始扩散初始半径
//						endRadius)//扩散结束半径


//				Math.hypot(x, y)
}
