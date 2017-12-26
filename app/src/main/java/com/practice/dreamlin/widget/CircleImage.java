package com.practice.dreamlin.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by dreamlin on 2017/12/20.
 */

public class CircleImage extends AppCompatImageView {

    private BitmapShader mBitmapShader;

    private int mRadius;

    private Matrix mMatrix;

    private RectF mRect;

    private Paint mPaint;

    private int mWidth;

    private int mRoundRadius = 10;

    private int mFilterColor = -1;

    private CstImageType mType = CstImageType.Circle;

    private ColorMatrix mColorMatrfix = new ColorMatrix(new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0,
            0, 0, 0, 0, 0

    });

    public CircleImage(Context context) {
        super(context);
        init();
    }

    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMatrix = new Matrix();
    }

    private void setBitmapShader() {
        Drawable drawable = getDrawable();
        if (null == drawable) {
            return;
        }
        Bitmap bitmap = drawable2Bitmap(drawable);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale;
        if (mType == CstImageType.Circle) {
            int bSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
            scale = mWidth * 1.0f / bSize;

        } else{
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(), getHeight() * 1.0f / bitmap.getHeight());
        }
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 如果是绘制圆形，则强制宽高大小一致
        if (mType == CstImageType.Circle) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRect = new RectF(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (getDrawable() == null)
            return;
        setBitmapShader();
        if (mType == CstImageType.Circle) {
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        } else if (mType == CstImageType.Oval) {
            canvas.drawOval(mRect, mPaint);
        } else {
            canvas.drawRoundRect(mRect, mRoundRadius, mRoundRadius, mPaint);
        }
//        super.onDraw(canvas);
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0xFFE4E1);
        drawable.setColorFilter(0xFFE4E1, PorterDuff.Mode.OVERLAY);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public void setBoundsColor(int mBoundsColor) {
        this.mFilterColor = mBoundsColor;
        invalidate();
    }

    public void setType(CstImageType mType) {
        if (this.mType != mType) {
            this.mType = mType;
            invalidate();
        }
    }

    public void setRoundRadius(int mRoundRadius) {
        if (this.mRoundRadius != mRoundRadius) {
            this.mRoundRadius = mRoundRadius;
            invalidate();
        }
    }

}
