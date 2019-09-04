package com.example.lugdu.datastructuresandalgorithms;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class Hexagon extends View {
    private String mHexiString; // TODO: use a default from R.string...
    private int textSize = 42;
    private Paint textPaint;
    private int mHexiColor = Color.RED; // TODO: use a default from R.color...
    private float mHexiDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mHexiDrawable;
    private boolean shadow = true;
    private String def;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    Paint paint = new Paint();

    public Hexagon(Context context) {
        super(context);
        init(null, 0);
    }

    public Hexagon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Hexagon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        textPaint = new Paint();
        if(attrs == null) return;
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.Hexagon, defStyle, 0);

        mHexiString = a.getString(
                R.styleable.Hexagon_hexiString);
        mHexiColor = a.getColor(
                R.styleable.Hexagon_hexiColor,
                mHexiColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mHexiDimension = a.getDimension(
                R.styleable.Hexagon_hexiDimension,
                mHexiDimension);

        if (a.hasValue(R.styleable.Hexagon_hexiDrawable)) {
            mHexiDrawable = a.getDrawable(
                    R.styleable.MyView_exampleDrawable);
            mHexiDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mHexiDimension);
        mTextPaint.setColor(mHexiColor);
        mTextWidth = mTextPaint.measureText(mHexiString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float radius = (getHeight()/2) - 10;
        //canvas.drawCircle(getWidth()/2,getHeight()/2,radius,paint);
        int x = 10;
        int y = (getHeight()/2) - 10;

        Path path = new Path();
        path.moveTo(x, y);
        int point = (int)(Math.sqrt(Math.pow(radius,2) - Math.pow(radius/2,2)));
        path.lineTo(x + (radius/2),y - point);
        path.lineTo(x + radius + (radius/2), y - point);
        path.lineTo(x + (2*radius), y);
        path.lineTo(x + radius + (radius/2), y + point);
        path.lineTo(x + (radius/2), y + point);
        path.lineTo(x,y);
        Paint paintH = new Paint();
        paintH.setColor(mHexiColor);
        paintH.setStyle(Paint.Style.FILL);
        paintH.setAntiAlias(true);
        paintH.setShadowLayer(5, 0, 0, Color.BLACK);
        canvas.drawPath(path, paintH);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        int xPos = getWidth() / 2 ;
        int yPos = (int) ((getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        textPaint.setTextAlign(Paint.Align.CENTER);
        //textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        //draw text
        if(mHexiString.split(" ").length == 1){
            canvas.drawText(mHexiString,xPos,yPos,textPaint);
        }
        else{
            String[] words  = mHexiString.split(" ");
            int size = words.length;
            if(size == 2){
                canvas.drawText(words[0],xPos,yPos - 20,textPaint);
                canvas.drawText(words[1],xPos,yPos + 20 ,textPaint);
            }
        }
    }

    public void toogleShadow(){
        shadow = !shadow;
    }
    public void setShadow(boolean shadow){
       this.shadow = shadow;
    }
    public void setHexiText(String text){
        mHexiString = text;
    }

    public void setHexiColor(int color){
        mHexiColor = color;
    }
    public String getHexiText(){
        return mHexiString;
    }

    public int getHexiColor(){
        return mHexiColor;
    }
    public String getDef(){ return def;}
    public void setDef(String def){ this.def = def; }
}
