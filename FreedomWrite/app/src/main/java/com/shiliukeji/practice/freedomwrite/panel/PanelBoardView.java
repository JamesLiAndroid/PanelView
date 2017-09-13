package com.shiliukeji.practice.freedomwrite.panel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.shiliukeji.practice.freedomwrite.drawtype.BaseAction;
import com.shiliukeji.practice.freedomwrite.drawtype.pathimpl.FreedomPath;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by XC_LSY on 2017/9/13.
 */

public class PanelBoardView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder = null;
    // 当前画笔形状
    private BaseAction mBaseAction;
    // 当前画笔颜色
    private int currentColor = Color.BLACK;
    // 画笔粗细
    private int currentPaintSize = 5;

    private Paint mPaint;
    private List<BaseAction> mBaseActions;
    private Bitmap mBitmap;

    private ActionType actionType = ActionType.Path;

    public PanelBoardView(Context context) {
        super(context);
        init();
    }

    public PanelBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PanelBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 初始化SurfaceHolder
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);

        // 初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(currentPaintSize);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mSurfaceHolder.unlockCanvasAndPost(canvas);

        mBaseActions = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_CANCEL) {
            return false;
        }

        float touchX = event.getX();
        float touchY = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setCurAction(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                Canvas canvas = mSurfaceHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                for (BaseAction baseAction: mBaseActions) {
                    baseAction.draw(canvas);
                }
                mBaseAction.move(touchX, touchY);
                mBaseAction.draw(canvas);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                break;

            case MotionEvent.ACTION_UP:
                mBaseActions.add(mBaseAction);
                mBaseAction = null;
                break;

            default:
                break;
        }

        return true;
    }

    /**
     * 获取画笔类型，并实例化
     * @param x
     * @param y
     */
    private void setCurAction(int x, int y) {
        switch (actionType) {
            case Path:
                mBaseAction = new FreedomPath(x, y, currentPaintSize, currentColor);
                break;
            case Line:
                // TODO: 暂时不处理
                break;
            default:
                break;
        }
    }

    /**
     * 设置画笔的颜色
     *
     * @param color 颜色
     */
    public void setColor(String color) {
        this.currentColor = Color.parseColor(color);
    }

    /**
     * 设置画笔的粗细
     *
     * @param size 画笔的粗细
     */
    public void setPaintSize(int size) {
        this.currentPaintSize = size;
    }

    /**
     * 设置画笔的形状
     *
     * @param type 画笔的形状
     */
    public void setType(ActionType type) {
        this.actionType = type;
    }

    /**
     * 将当前画布拆成Bitmap
     * @return
     */
    public Bitmap getBitmap() {
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        doDraw(canvas);
        return mBitmap;
    }

    /**
     * 绘制图片内容
     * @param canvas
     */
    private void doDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        for (BaseAction action : mBaseActions) {
            action.draw(canvas);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    /**
     * 回退绘制内容
     * @return
     */
    public boolean backAction() {
        if (mBaseActions != null && mBaseActions.size() > 0) {
            mBaseActions.remove(mBaseActions.size() - 1);
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for (BaseAction action: mBaseActions) {
                action.draw(canvas);
            }
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            return true;
        }
        return false;
    }

    /**
     * 重置画板
     */
    public void reset() {
        if (mBaseActions != null && mBaseActions.size() > 0) {
            mBaseActions.clear();
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for (BaseAction action: mBaseActions) {
                action.draw(canvas);
            }
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
