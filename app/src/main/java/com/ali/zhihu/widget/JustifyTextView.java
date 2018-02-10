package com.ali.zhihu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/2/9.
 */

public class JustifyTextView extends TextView {
    private int mLineY;
    private int mViewWidth;

    public JustifyTextView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected void onLayout(boolean changed,int left,int top,int right,int boottom){
        super.onLayout(changed,left,top,right,boottom);
    }

    @Override
    protected void onDraw(Canvas canvas){
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        mViewWidth = getMeasuredWidth() - getPaddingRight();
        String text = getText().toString();
        mLineY = getPaddingTop();
        mLineY += getTextSize();
        Layout layout = getLayout();

        if(layout == null){
            return;
        }

        Paint.FontMetrics fm = paint.getFontMetrics();
        int textHeight = (int)(Math.ceil((fm.descent - fm.ascent)));
        textHeight = (int)(textHeight * layout.getSpacingMultiplier() + layout.getSpacingAdd());
        int textLine = layout.getLineCount();

        for(int i = 0; i < textLine;i++){
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            String lineText = text.substring(lineStart,lineEnd);
            float lineWidth = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, paint);

            if(i == textLine - 1){
                //最后一行则直接画
                canvas.drawText(lineText,0,mLineY,paint);
            }else{
                if(needScale(lineText)){
                    drawScaleText(canvas,lineText,paint,lineWidth);
                }else{
                    canvas.drawText(lineText,0,mLineY,paint);
                }
            }
            mLineY += textHeight;
        }

    }
    private void drawScaleText(Canvas canvas, String lineText, TextPaint paint,float lineWidth){
        int x = 0;
        int gapCount = lineText.length() - 1;
        /*if(lineText.length() > 2 && lineText.charAt(0) == ' ' && lineText.charAt(1) == ' '){
            float blankWidth = StaticLayout.getDesiredWidth("  ",paint);
            x += blankWidth;
            canvas.drawText("  ",0,mLineY,paint);
            i = 2;
        }*/
        float gap = (mViewWidth - lineWidth)/gapCount;
        for(int i = 0;i < lineText.length();i++){
            canvas.drawText(String.valueOf(lineText.charAt(i)),x,mLineY,paint);
            x += gap + StaticLayout.getDesiredWidth(String.valueOf(lineText.charAt(i)),paint);
        }
    }

    private boolean needScale(String lineText){
        if(lineText == null || lineText.length() == 0){
            return false;
        }
        //如果没有遇到原先设置的换行，则自动调整字间的间距
        return lineText.charAt(lineText.length() - 1) != '\n';
    }
}
