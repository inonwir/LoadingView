package com.ldoublem.loadingviewlib.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Animatable;
import android.os.SystemClock;

public class WifiSignalDrawable extends Drawable implements Runnable, Animatable {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mAnimatedValue = 0.9f;
    private boolean mRunning = false;
    private int signalSize = 4;

    private static final long FRAME_DELAY = 16;

    public WifiSignalDrawable() {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        float width = getBounds().width();
        canvas.save();
        canvas.translate(0, width / signalSize);
        mPaint.setStrokeWidth(width / signalSize / 8);

        int scale = (int) ((mAnimatedValue * signalSize - (int) (mAnimatedValue * signalSize)) * signalSize) + 1;
        float signalRadius = width / 2 / signalSize;
        for (int i = 0; i < signalSize; i++) {
            if (i >= signalSize - scale) {
                float radius = signalRadius * i;
                RectF rect = new RectF(radius, radius, width - radius, width - radius);
                if (i < signalSize - 1) {
                    mPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawArc(rect, -135, 90, false, mPaint);
                } else {
                    mPaint.setStyle(Paint.Style.FILL);
                    canvas.drawArc(rect, -135, 90, true, mPaint);
                }
            }
        }

        canvas.restore();
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
        mAnimatedValue += 0.02f;
        if (mAnimatedValue > 1f) mAnimatedValue = 0.0f;
        invalidateSelf();
        if (mRunning) {
            scheduleSelf(this, SystemClock.uptimeMillis() + FRAME_DELAY);
        }
    }

    @Override
    public void setAlpha(int alpha) { mPaint.setAlpha(alpha); }

    @Override
    public void setColorFilter(android.graphics.ColorFilter colorFilter) { mPaint.setColorFilter(colorFilter); }

    @Override
    public int getOpacity() { return android.graphics.PixelFormat.TRANSLUCENT; }
}
