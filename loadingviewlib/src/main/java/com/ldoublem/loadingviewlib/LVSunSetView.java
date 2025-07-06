package com.ldoublem.loadingviewlib.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Animatable;
import android.os.SystemClock;

public class SunSetDrawable extends Drawable implements Runnable, Animatable {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean mRunning = false;

    private static final long FRAME_DELAY = 16;

    private float mAnimatedValue = 0f;

    // Your sunset arc parameters
    private int sun_angle = 12;
    private String SunstartTime = "2023-01-01 05:38:00";
    private String SunendTime = "2023-01-01 18:16:00";

    public SunSetDrawable() {
        mPaint.setColor(Color.WHITE);
    }

    @Override
    public void draw(Canvas canvas) {
        int width = getBounds().width();
        int height = getBounds().height();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);

        RectF oval = new RectF(width / 2 - width / 3, height / 2 - width / 3, width / 2 + width / 3, height / 2 + width / 3);

        canvas.drawArc(oval, 180 + sun_angle, (180 - 2 * sun_angle) * mAnimatedValue, false, mPaint);
    }

    @Override
    public void start() {
        if (mRunning) return;
        mRunning = true;
        scheduleSelf(this, SystemClock.uptimeMillis() + FRAME_DELAY);
    }

    @Override
    public void stop() {
        unscheduleSelf(this);
        mRunning = false;
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    @Override
    public void run() {
        mAnimatedValue += 0.01f;
        if (mAnimatedValue >= 1f) mAnimatedValue = 0f;

        invalidateSelf();
        if (mRunning) {
            scheduleSelf(this, SystemClock.uptimeMillis() + FRAME_DELAY);
        }
    }

    @Override
    public void setAlpha(int alpha) { mPaint.setAlpha(alpha); }

    @Override
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {}

    @Override
    public int getOpacity() { return android.graphics.PixelFormat.TRANSLUCENT; }
}
