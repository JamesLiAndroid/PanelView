package com.shiliukeji.practice.freedomwrite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.shiliukeji.practice.freedomwrite.panel.PanelBoardView;

public class MainActivity extends AppCompatActivity {

    PanelBoardView panelBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panelBoardView = (PanelBoardView) findViewById(R.id.panel_board_view);
        panelBoardView.setPaintSize(dip2px(5));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return panelBoardView.onTouchEvent(event);
    }

    private int dip2px(float dpValue){
        final float scale = getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
}
