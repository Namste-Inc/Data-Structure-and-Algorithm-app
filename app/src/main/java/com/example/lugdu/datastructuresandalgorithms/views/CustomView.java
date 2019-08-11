package com.example.lugdu.datastructuresandalgorithms.views;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Color;

public class CustomView extends View {

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }
    public void init (@Nullable AttributeSet set){

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect = new Rect();
        rect.left = 10;
        rect.top = 10;
        rect.right = rect.left + 10;
        rect.bottom = rect.top + 10;
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect, paint);
    }
}
