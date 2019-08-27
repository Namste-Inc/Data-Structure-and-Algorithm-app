package com.example.lugdu.datastructuresandalgorithms.algo.Sort;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lugdu.datastructuresandalgorithms.CircleText;
import com.example.lugdu.datastructuresandalgorithms.R;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class BubbleSortFragment extends Fragment {
    View view;
    int arr[];
    TextView tArr[];
    TextView explanationText;
    Queue<ObjectAnimator> animations;
    boolean animating;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bubble_sort, container,false);
        final Button button1 = view.findViewById(R.id.button1);
        final EditText editText = view.findViewById(R.id.topBox);
        explanationText = view.findViewById(R.id.explanationText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText.isInEditMode()){
                    String[] afterC = editText.getText().toString().split(": ");
                    if(afterC.length > 1){
                        editText.setText(afterC[1]);
                    }
                    editText.setSelection(editText.getText().length());
                }
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(entryGood(editText.getText().toString())) {
                        if(!editText.getText().toString().equals("")){
                            parseArray(editText.getText().toString());
                            initArray();
                        }
                        editText.setText(editText.getText());
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        editText.clearFocus();
                        editText.setSelection(editText.getText().length());
                        return true;
                    }
                    else{
                        Toast.makeText(getContext(),"Invalid Entry", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
                return false;
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(arr == null || arr.length == 0){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),"Must enter an array", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else {
                            bubbleSort();
                        }
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
        return view;
    }
    int smallNumber = 0;
    int bigNumber = 0;
    public void bubbleSort() {
        TextView textView = view.findViewById(R.id.textView4);
        textView.setText("Sorted Array:");
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                ((CircleText) tArr[j]).select();
                tArr[j].invalidate();
                pause(Thread.currentThread(),1000);
                if (arr[j] > arr[j + 1]) {
                    bigNumber = arr[j];
                    smallNumber = arr[j+1];
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            explanationText.setText(bigNumber + " is bigger than " + smallNumber + ". Switch their positions.");
                        }
                    };

                    getActivity().runOnUiThread(runnable);

                    swapArrInt(j, j + 1);
                    swapArrTView1(j, j+1);
                    ((CircleText)tArr[j+1]).deselect();
                    tArr[j + 1].invalidate();
                    pause(Thread.currentThread(),1000);
                }

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        explanationText.setText("No changes to be made.");
                    }
                };

                getActivity().runOnUiThread(runnable);

                ((CircleText)tArr[j]).deselect();
                tArr[j].invalidate();
            }
        }
        textView.setText("Sorted Array: " + arrayToString(arr));
    }
    public boolean entryGood(String entry){
        if(entry.startsWith(",") || entry.endsWith(",") || entry.contains(",,")){
            return false;
        }
        return true;
    }
    public void pause(Thread thread, int time){
        synchronized (thread){
            try {
                Thread.currentThread().wait(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void swapAni(int id1, int id2){
        TextView txt1 = view.findViewById(id1);
        TextView txt2 = view.findViewById(id2);
        float x1 = txt1.getX();
        float x2 = txt2.getX();
        ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationX", x2);
        animation.setDuration(1000);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(txt2, "translationX", x1);
        animation2.setDuration(1000);
        animation.start();
        animation2.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ignite(int pos){
        ObjectAnimator objectAnimator = ObjectAnimator.ofArgb(tArr[pos], "backgroundColor", Color.BLUE);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void extinguish(int id){
        TextView txt = view.findViewById(id);
        ObjectAnimator objectAnimator = ObjectAnimator.ofArgb(txt, "backgroundColor", Color.TRANSPARENT);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void initArray(){
        int len = arr.length;
        tArr = new TextView[len];
        RelativeLayout insertPoint = view.findViewById(R.id.lView);
        if(insertPoint.getChildCount() > 0){
            insertPoint.removeAllViews();
        }
        for(int i = 0; i<len; i++){
            TextView textView = new CircleText(getContext());
            textView.setText(arr[i] + "");
            tArr[i] = textView;
            textView.setX(i * 130);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(120,120);
            layoutParams.setMargins(20,0,0,25);
            insertPoint.addView(textView, layoutParams);
        }
    }
    public void swapArrTView(final int pos1, final int pos2, Button b){
        while (animating){

        }
        float x1 = tArr[pos1].getX();
        float x2 = tArr[pos2].getX();
        final ObjectAnimator animation = ObjectAnimator.ofFloat(tArr[pos1], "translationX", x2);
        animation.setDuration(200);
        final ObjectAnimator animation2 = ObjectAnimator.ofFloat(tArr[pos2], "translationX", x1);
        animation2.setDuration(200);
        animation.start();
        animation2.start();
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
                System.out.println("animation Ended");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animating = true;
        TextView pos = tArr[pos1];
        tArr[pos1] = tArr[pos2];
        tArr[pos2] = pos;
    }
    public void swapArrTView1(int pos1, int pos2){
        float x1 = tArr[pos1].getX();
        float x2 = tArr[pos2].getX();
        float foward = x1;
        float backwards = x2;
        for(float i = x1; i<x2; i+=.009){
            tArr[pos1].setX(foward);
            tArr[pos2].setX(backwards);
            foward += .009;
            backwards-=.009;
        }
        TextView pos = tArr[pos1];
        tArr[pos1] = tArr[pos2];
        tArr[pos2] = pos;
    }
    public void swapArrInt(int pos1, int pos2){
        int pos = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = pos;
    }


    public void parseArray(String arr){
        String[] arr1 = arr.split(",");
        this.arr = new int[arr1.length];
        if(arr.equals("")){
            this.arr = null;
            return;
        }
        for(int i = 0; i<arr1.length; i++){
            this.arr[i] = Integer.parseInt(arr1[i]);
        }
    }
    public String arrayToString(int[] arr){
        int len = arr.length;
        String toString = "";
        for(int i = 0; i<len; i++){
            if(i == 0){
                toString += arr[0];
            }else{
                toString += "," + arr[i];
            }
        }
        return toString;
    }
    final class Animation1 {

        final Thread animator;

        public Animation1(final int pos1, final int pos2)
        {
            animator = new Thread(new Runnable() {
                @Override
                public void run() {
                    //swapArrTView(pos1, pos2);
                }
            });

        }

        public void startAnimation()
        {
            animator.start();
        }

        public void awaitCompletion() throws InterruptedException
        {
            animator.join();
        }
    }
}
