package com.shiliukeji.practice.freedomwrite.drawtype;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * 绘制行为基类
 * Created by XC_LSY on 2017/9/13.
 */

public abstract class BaseAction {
    public int color;

    public BaseAction() {
        this.color = Color.BLACK;
    }

    public BaseAction(int color) {
        this.color = color;
    }

    public abstract void draw(Canvas canvas);

    public abstract void move(float mx, float my);
}
