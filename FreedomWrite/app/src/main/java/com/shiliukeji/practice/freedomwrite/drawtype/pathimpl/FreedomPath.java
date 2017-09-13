package com.shiliukeji.practice.freedomwrite.drawtype.pathimpl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.shiliukeji.practice.freedomwrite.drawtype.BaseAction;

/**
 * 手指自由绘制类
 * Created by XC_LSY on 2017/9/13.
 */

public class FreedomPath extends BaseAction {
    private Path path; // 绘制路径
    private int size; // 绘制路径大小

    public FreedomPath() {
        path = new Path();
        size = 1;
    }


    public FreedomPath(float x, float y, int size, int color) {
        super(color);
        path = new Path();
        this.size = size;
        path.moveTo(x, y);
        path.lineTo(x, y);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true); // 抗锯齿
        paint.setDither(true); // 设置防抖动
        paint.setStrokeWidth(size); // 设置画笔线条粗细
        paint.setColor(color); // 设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPath(path, paint);
    }

    @Override
    public void move(float mx, float my) {
        path.lineTo(mx, my);
    }
}
