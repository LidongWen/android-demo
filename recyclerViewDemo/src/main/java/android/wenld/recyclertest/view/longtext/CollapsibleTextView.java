package android.wenld.recyclertest.view.longtext;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.wenld.recyclertest.R;


/**
 * Created by chenjianbin on 2016/6/14.
 */
public class CollapsibleTextView extends LinearLayout implements
        View.OnClickListener {

    public interface onCollapseListener {
        void onCollapseChanged(boolean collapse);
    }


    /**
     * 显示最大行数開始折疊
     */
    private static final int DEFAULT_MAX_LINE_COUNT = 6;

    private CollapseTextView desc;
    private TextView descOp;

    private String shrinkup;
    private String spread;

    private onCollapseListener mOnCollapseListener;

    public CollapsibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        shrinkup = context.getString(R.string.desc_shrinkup);
        spread = context.getString(R.string.desc_spread);
        View view = inflate(context, R.layout.collapsible_textview, this);
        view.setPadding(0, -1, 0, 0);
        desc = (CollapseTextView) view.findViewById(R.id.desc_tv);
        desc.setMaxLines(DEFAULT_MAX_LINE_COUNT);
        desc.setOnEllipseListener(new CollapseTextView.OnLineCountChangeListener() {
            @Override
            public void onLineCountChanged(final int lineCount) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        descOp.setVisibility(lineCount > DEFAULT_MAX_LINE_COUNT ? VISIBLE : GONE);
                        descOp.setText(desc.getMaxLines() > DEFAULT_MAX_LINE_COUNT ? shrinkup : spread);
                    }
                });

            }
        });
        descOp = (TextView) view.findViewById(R.id.desc_op_tv);
        descOp.setOnClickListener(this);
    }

    public CollapsibleTextView(Context context) {
        this(context, null);
    }

    public void setOnCollapseListener(onCollapseListener listener) {
        this.mOnCollapseListener = listener;
    }

    public final void setDesc(CharSequence charSequence, TextView.BufferType bufferType) {
        desc.setText(charSequence, bufferType);
    }

    /**
     * 设置文字收缩
     *
     * @param expand
     */
    public void setTextExpand(boolean expand) {
        desc.setMaxLines(expand ? Integer.MAX_VALUE : DEFAULT_MAX_LINE_COUNT);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        if (desc.getMaxLines() > DEFAULT_MAX_LINE_COUNT) {
            if (mOnCollapseListener != null)
                mOnCollapseListener.onCollapseChanged(true);
            setTextExpand(false);
        } else {
            if (mOnCollapseListener != null)
                mOnCollapseListener.onCollapseChanged(false);
            setTextExpand(true);
        }
    }
}
