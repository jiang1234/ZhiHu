package com.ali.zhihu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ali.zhihu.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2018/2/9.
 */

public class ExpandableTextView extends LinearLayout{
    private int maxCollapseLines;
    private TextView expandableText;
    private ImageButton expandCollapseButton;
    private boolean collapsed;
    private Drawable expendDrawable;
    private Drawable collapsedDrawable;
    private boolean isChange;
    private OnExpandStateChangeListener listener;

    public ExpandableTextView(Context context) {
        super(context,null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        setOrientation(VERTICAL);
        isChange = false;
        collapsed = true;//表示折叠
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        maxCollapseLines = array.getInteger(R.styleable.ExpandableTextView_maxCollapseLines,2);
        expendDrawable = array.getDrawable(R.styleable.ExpandableTextView_expandDrawable);
        collapsedDrawable = array.getDrawable(R.styleable.ExpandableTextView_collapseDrawable);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        expandableText = (TextView)findViewById(R.id.expandable_text);
        expandCollapseButton = (ImageButton)findViewById(R.id.expand_collapse);
        expandCollapseButton.setImageDrawable(collapsed?collapsedDrawable:expendDrawable);
        expandCollapseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                isChange = true;
                collapsed = !collapsed;
                if(listener != null){
                    listener.onExpandStateChanged(collapsed);
                }
                expandCollapseButton.setImageDrawable(collapsed?collapsedDrawable:expendDrawable);
                requestLayout();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!isChange || expandCollapseButton.getVisibility() == GONE){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        isChange = false;

        expandCollapseButton.setVisibility(GONE);
        expandableText.setMaxLines(Integer.MAX_VALUE);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        if(expandableText.getLineCount() <= maxCollapseLines){
            return;
        }
        if(collapsed){
            expandableText.setMaxLines(maxCollapseLines);
        }
        expandCollapseButton.setVisibility(VISIBLE);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    public void setText(String text){
        isChange = true;
        expandableText.setText(text);
    }

    public void setText(String text,boolean collapse){
        this.collapsed = collapse;
        expandCollapseButton.setImageDrawable(collapsed?collapsedDrawable:expendDrawable);
        setText(text);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        requestLayout();
    }

    public interface OnExpandStateChangeListener{
        void onExpandStateChanged(boolean collapse);
    }

    public void setExpandStateChangeListener(OnExpandStateChangeListener listener){
        this.listener = listener;
    }
}
