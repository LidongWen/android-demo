package com.wenld.recyclertest.view.longtext;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by chenjianbin on 2016/6/14.
 */
public class CollapseTextView extends TextView {

    public interface OnLineCountChangeListener{
        void onLineCountChanged(int lineCount);
    }

    int mMaxLines;

    OnLineCountChangeListener mOnEllipseListener;

    public CollapseTextView(Context context) {
        super(context);
    }

    public CollapseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CollapseTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnEllipseListener(OnLineCountChangeListener mOnEllipseListener) {
        this.mOnEllipseListener = mOnEllipseListener;
    }

    @Override
    public void setMaxLines(int maxlines) {
        super.setMaxLines(maxlines);
        mMaxLines = maxlines;
    }

    public int getMaxLines() {
        return Build.VERSION.SDK_INT >= 16 ? super.getMaxLines() : mMaxLines;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Layout l = getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if(mOnEllipseListener != null)
                mOnEllipseListener.onLineCountChanged(lines);
        }
    }

}
