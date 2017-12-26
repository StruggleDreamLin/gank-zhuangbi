package com.practice.dreamlin.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by dreamlin on 2017/12/20.
 */

public class RadioImageView extends AppCompatImageView {

    private int originalWidth;
    private int originalHeight;

    private int measureWidth;
    private int measureHeight;

    public RadioImageView(Context context) {
        super(context);
    }

    public RadioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOriginalSize(int originalWidth, int originalHeight){
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (originalHeight >0 && originalWidth > 0) {
            float radio = (originalWidth) / (float) originalHeight;

            measureWidth = MeasureSpec.getSize(widthMeasureSpec);
            measureHeight = MeasureSpec.getSize(heightMeasureSpec);

            if (measureWidth > 0){
                measureHeight = (int) ((float) measureWidth / radio);
            }
            setMeasuredDimension(measureWidth, measureHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
