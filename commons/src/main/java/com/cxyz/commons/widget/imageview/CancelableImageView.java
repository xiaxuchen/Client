package com.cxyz.commons.widget.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cxyz.commons.R;
import com.cxyz.commons.widget.imageview.listener.OnCancelClickListener;

/**
 * Created by Administrator on 2019/2/14.
 * 注意:padding无效
 */

public class CancelableImageView extends android.support.v7.widget.AppCompatImageView{

    private Drawable drawable;

    private boolean isInit = false;

    private OnCancelClickListener onCancelClickListener;

    public void setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    public CancelableImageView(Context context) {
        this(context,null,0);
    }

    public CancelableImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CancelableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        drawable = getResources().getDrawable(R.mipmap.delete_selector);
        setScaleType(ScaleType.FIT_CENTER);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        initDrawableSize();
    }

    private void initDrawableSize() {
        ViewGroup.LayoutParams params =getLayoutParams();
        drawable.setBounds(params.width- drawable.getIntrinsicWidth(),0,params.width,drawable.getIntrinsicHeight());
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect bounds = drawable.getBounds();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:{
                int x = (int) event.getX();
                int y = (int) event.getY();
                if(bounds.contains(x,y)&&onCancelClickListener != null)
                    onCancelClickListener.onCancelClick(this);
                break;
            }
        }
        return super.onTouchEvent(event);
    }


}
