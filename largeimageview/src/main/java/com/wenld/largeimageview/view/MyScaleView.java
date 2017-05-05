package com.wenld.largeimageview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p/>
 * Author: wenld on 2017/5/5 14:58.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class MyScaleView extends CustomView implements View.OnTouchListener {
    String TAG = MyScaleView.class.getSimpleName();
    public static final float SCALE_MAX = 4.0f;
    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;

    private int mDrawableWidth = 0;
    private int mDrawableHeight = 0;
    //    private Drawable mDrawable;
    private Bitmap mBitmap;
    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];
    private final Matrix mScaleMatrix = new Matrix();

    private BitmapRegionDecoder mDecoder;

    public void setInputStream(InputStream is) {
        try {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            // Grab the bounds for the scene dimensions
            tmpOptions.inJustDecodeBounds = true;
            tmpOptions.inScaled = true;
//            tmpOptions.inSampleSize = 2;
            BitmapFactory.decodeStream(is, null, tmpOptions);
            mDrawableWidth = /*1233;//*/mDecoder.getWidth();
            mDrawableHeight = /*7248;*/mDecoder.getHeight();

            mBitmap = mDecoder.decodeRegion(new Rect(0, 0, mDrawableWidth, mDrawableHeight), tmpOptions);

            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (is != null) is.close();
            } catch (Exception e) {
            }
        }
    }

    public MyScaleView(Context context) {
        super(context);
    }

    public MyScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initAttrs(AttributeSet attrs) {

    }

    Paint paintTest;

    @Override
    public void initValue() {

        paintTest = new Paint();

        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            public boolean onScale(ScaleGestureDetector detector) {
                if (mBitmap == null)
                    return true;
                float scale = getScale();
                float scaleFactor = detector.getScaleFactor();
                /**
                 * 缩放的范围控制
                 */
                if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                        || (scale > initScale && scaleFactor < 1.0f)) {
                    /**
                     * 最大值最小值判断
                     */
                    if (scaleFactor * scale < initScale) {
                        scaleFactor = initScale / scale;
                    }
                    if (scaleFactor * scale > SCALE_MAX) {
                        scaleFactor = SCALE_MAX / scale;
                    }
                    /**
                     * 设置缩放比例
                     */
                    mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                    scale = getScale();
                    Log.e(TAG, "scale:" + scale + "\n detector.getFocusX():" + detector.getFocusX() + " \ndetector.getFocusY():" + detector.getFocusY());
                    invalidate();
                }
                return true;
            }
        });
        this.setOnTouchListener(this);
    }

    @Override
    public void reset() {

    }

    /**
     * 获得当前的缩放比例
     *
     * @return
     */
    public final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawCircle(mCenterX, mCenterY, 100, paintTest);
        if (mBitmap != null) {
            canvas.save();
//            canvas.translate(mCenterX, mCenterY);
            canvas.drawBitmap(mBitmap, mScaleMatrix, null);
            canvas.restore();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        requestDisallowInterceptTouchEvent(true);
        mScaleGestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }
}
