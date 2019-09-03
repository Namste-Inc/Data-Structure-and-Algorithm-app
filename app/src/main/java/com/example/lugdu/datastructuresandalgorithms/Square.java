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
import android.widget.Toast;

public class Square extends View {
    int color;
    boolean single;
    int numBoxes;
    TextView[] circleTexts;
    int side;

    public Square(Context context) {
        super(context);
        color = Color.BLUE;
    }
    public Square(Context context,int numBoxes, TextView[] circleTexts, int side) {
        super(context);
        color = Color.BLUE;
        this.circleTexts = circleTexts;
        this.numBoxes = numBoxes;
        this.side = side;
    }

    public Square(Context context, AttributeSet attrs) {
        super(context, attrs);
        color = Color.BLUE;
    }

    public Square(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);
    }
    public void select(){
        color = Color.GREEN;
    }
    public void deselect(){
        color = Color.BLUE;
    }
}
