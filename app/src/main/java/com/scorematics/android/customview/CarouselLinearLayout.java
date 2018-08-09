package com.scorematics.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by sameer.madaan on 11/8/2017.
 */
public class CarouselLinearLayout extends LinearLayout {
    private float mScale = 1.0f;

    public CarouselLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarouselLinearLayout(Context context) {
        super(context);
    }

    public void setScaleBoth(float scale) {
        super.requestLayout();
        this.mScale = scale;
        this.invalidate();    // If you want to see the mScale every time you set
        // mScale you need to have this line here,
        // invalidate() function will call onDraw(Canvas)
        // to redraw the view for you
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // The main mechanism to display mScale animation, you can customize it
        // as your needs
        int w = this.getWidth();
        int h = this.getHeight();
        canvas.scale(mScale, mScale, w / 2, h / 2);

        super.onDraw(canvas);
    }
}
