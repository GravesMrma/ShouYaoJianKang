package com.wuhanzihai.rbk.ruibeikang.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wuhanzihai.rbk.ruibeikang.R;

public class MineBgView extends View {

    private Paint mPaint;
    private int centerX, centerY;
    private PointF start, end, control;

    public MineBgView(Context context) {
        super(context);
    }

    public MineBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.red));
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        start = new PointF(0,0);
        end = new PointF(0,0);
        control = new PointF(0,0);
    }

    public MineBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w;
        centerY = (int) (h*(3f/4f));
        start.x = 0;
        start.y = centerY;
        end.x = w;
        end.y = centerY; //
        control.x = w/2;
        control.y = h*(5/4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBG(canvas);
    }
    // 绘制头部的背景图片 贝塞尔曲线的下边
    private void drawBG(Canvas canvas){
        Path path = new Path();
        path.moveTo(start.x,start.y);
        path.quadTo(control.x,control.y,end.x,end.y);
        path.lineTo(centerX,0);
        path.lineTo(0,0);
        canvas.drawPath(path, mPaint);
    }
}
