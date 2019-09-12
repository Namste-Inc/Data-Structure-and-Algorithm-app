package com.example.lugdu.datastructuresandalgorithms;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class CircleText extends android.support.v7.widget.AppCompatTextView {
    int num;
    int color;

    public CircleText(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        color = Color.RED;
    }

    public CircleText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Paint textPaint = new Paint();
        textPaint.setColor(Color.RED);
        int mid = getWidth() / 2 ;
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(64);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(10);
        canvas.drawCircle(mid, mid, (getHeight()/2) - 12, paint);
        //canvas.drawText("" + num, xPos,yPos,textPaint);
    }
    public void select(boolean i){
        color = i?Color.BLUE:Color.YELLOW;
        invalidate();
    }
    public void deselect(){
        color = Color.RED;
        invalidate();
    }
    public void sorted(){
        color = Color.GREEN;
        invalidate();
    }
}
