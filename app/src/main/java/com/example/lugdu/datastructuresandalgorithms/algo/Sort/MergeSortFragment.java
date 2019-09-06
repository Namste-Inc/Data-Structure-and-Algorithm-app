package com.example.lugdu.datastructuresandalgorithms.algo.Sort;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
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
import com.example.lugdu.datastructuresandalgorithms.MainActivity;
import com.example.lugdu.datastructuresandalgorithms.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.Square;

public class MergeSortFragment extends Fragment {
    View view;
    int arr[];
    TextView tArr[];
    TextView explanationText;
    int h,w;
    int leftMargin = 0;
    int rightMargin = 0;
    int topMargin = 20;
    int bottomMargin = 0;
    RelativeLayout relativeLayout;
    final int squareSize = 80;

    ArrayList<ArrayList<ArrayList<Square>>> squares = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)  {
        view = inflater.inflate(R.layout.fragment_merge_sort, container,false);
        final Button button1 = view.findViewById(R.id.button1);
        final EditText editText = view.findViewById(R.id.topBox);
        explanationText = view.findViewById(R.id.explanationText);
        relativeLayout = view.findViewById(R.id.lView);
        h = relativeLayout.getLayoutParams().height;
        w = MainActivity.width;
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText.isInEditMode()){
                    String[] afterC = editText.getText().toString().split(": ");
                    if(afterC.length > 1){
                        editText.setText(arrayToString(arr, false));
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
                        editText.setText("Original array: [ " + arrayToString(arr,true) + " ]");
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
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        pause(Thread.currentThread(), 500);
                        position();
                        mergeSort(arr,arr.length,0);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
        return view;
    }

    public void mergeSort(int[] array, int sizeOfArray, int layer) {
        TextView resultText = view.findViewById(R.id.resultText);
        if (sizeOfArray < 2) {
            return;
        }
        int mid = sizeOfArray / 2;
        int[] firstHalf = new int[mid];
        int[] secondHalf = new int[sizeOfArray - mid];

        for (int i = 0; i < mid; i++) {
            firstHalf[i] = array[i];
        }
        for (int i = mid; i < sizeOfArray; i++) {
            secondHalf[i - mid] = array[i];
        }
        splitAni(layer);
        mergeSort(firstHalf, mid, layer ++);

        mergeSort(secondHalf, sizeOfArray - mid, layer ++);

        merge(array, firstHalf, secondHalf, mid, sizeOfArray - mid);
        //System.out.println(Arrays.toString(a));
        resultText.setText("Here's the sorted array: " + Arrays.toString(array));
    }

    public void merge(
            int[] array, int[] firstHalf, int[] secondHalf, int left, int right) {

        System.out.println("Entered Merge for: " + Arrays.toString(firstHalf) + ", " + Arrays.toString(secondHalf));
        System.out.println("Here's the current array: " + Arrays.toString(array));

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            System.out.println("Entered first while loop");
            if (firstHalf[i] <= secondHalf[j]) {
                System.out.println("Entered first if");
                array[k++] = firstHalf[i++];
            }
            else {
                System.out.println("Entered first else");
                array[k++] = secondHalf[j++];
            }
        }
        System.out.println(i + ", " + left);
        System.out.println("Left mergesort first while loop");
        while (i < left) {
            System.out.println("entered second while loop");
            array[k++] = firstHalf[i++];


        }
        while (j < right) {
            System.out.println("Entered third while loop");
            array[k++] = secondHalf[j++];
        }
        System.out.println("Here's the current array: " + Arrays.toString(array));
    }
    public void parseArray(String arr){
        String[] arr1 = arr.split("-");
        this.arr = new int[arr1.length];
        if(arr.equals("")){
            this.arr = null;
            return;
        }
        for(int i = 0; i<arr1.length; i++){
            this.arr[i] = Integer.parseInt(arr1[i]);
        }
    }

    public boolean entryGood(String entry){
        if(entry.startsWith(",") || entry.endsWith(",") || entry.contains(",,")){
            return false;
        }
        return true;
    }

    public void initArray(){
        int len = arr.length;
        int totalLength = (len * (squareSize + leftMargin + rightMargin + 5)) - -5;
        int totalSpace = w - totalLength;
        int startSpace  = (totalSpace / 2);
        tArr = new TextView[len];
        ArrayList<Square> squareArr = new ArrayList<>();
        squares.clear();
        if(relativeLayout.getChildCount() > 0){
            relativeLayout.removeAllViews();
        }
        for(int i = 0; i<len; i++){
            TextView textView = new CircleText(getContext());
            textView.setText(arr[i] + "");
            tArr[i] = textView;
            textView.setX((i * 85) + startSpace);
            Square square = new Square(getContext());
            square.setX((i * 85) + startSpace);
            squareArr.add(square);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(squareSize,squareSize);
            layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            relativeLayout.addView(square, layoutParams);
            relativeLayout.addView(textView, layoutParams);
        }
        ArrayList<ArrayList<Square>> tempArr = new ArrayList<>();
        tempArr.add(squareArr);
        squares.add(tempArr);
    }

    public void mergeAni() {

    }

    public ArrayList<ArrayList<Square>> copyLayer(int layer){
        ArrayList<ArrayList<Square>> temp = new ArrayList<>();
        for(ArrayList<Square> square: squares.get(layer)){
            temp.add(square);
        }
        return temp;
    }

    public void splitAni(int layer1){
        final int layer = layer1;
        //drop number down
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < tArr.length; i++){
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tArr[i], "translationY", (layer + 1) * (squareSize + 5));
                    objectAnimator.setDuration(1000);
                    objectAnimator.start();
                }
            }
        });

        pause(Thread.currentThread(), 1000);
        //make new boxes
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArrayList<Square>> temp = copyLayer(layer);
                int len = temp.size();

                ArrayList<ArrayList<Square>> temp3 = new ArrayList<>();
                //for all sublayers
                for (int i = 0; i < len; i++) {
                    int half = temp.get(i).size() / 2;
                    int endPoint = half - 1;
                    ArrayList<Square> temp2 = new ArrayList<>();
                    ArrayList<Square> temp2o = new ArrayList<>();
                    //for all boxes in sublayers
                    for (int j = 0; j < half; j++) {
                        Square square = new Square(getContext());
                        square.setX(temp.get(i).get(j).getX());
                        square.setY(temp.get(i).get(j).getY() + squareSize + 5);
                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setInterpolator(new DecelerateInterpolator());
                        fadeIn.setDuration(1000);
                        square.setAnimation(fadeIn);
                        temp2.add(square);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(squareSize, squareSize);
                        layoutParams.setMargins(0, 0, 0, 0);
                        relativeLayout.addView(square, layoutParams);
                    }
                    temp3.add(temp2);
                    for(int k = half; k < temp.get(i).size(); k ++){
                        Square square = new Square(getContext());
                        square.setX(temp.get(i).get(k).getX());
                        square.setY(temp.get(i).get(k).getY() + squareSize + 5);
                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setInterpolator(new DecelerateInterpolator());
                        fadeIn.setDuration(1000);
                        square.setAnimation(fadeIn);
                        temp2o.add(square);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(squareSize, squareSize);
                        layoutParams.setMargins(0, 0, 0, 0);
                        relativeLayout.addView(square, layoutParams);
                    }
                    temp3.add(temp2o);
                }
                squares.add(temp3);
            }
        });
        //split boxes
        pause(Thread.currentThread(), 1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArrayList<Square>> layerArr = squares.get(layer + 1);
                boolean moveRight = true;
                int circleIndex = 0;
                for(int i = 0; i<layerArr.size(); i++){
                    ArrayList<Square> pairs = layerArr.get(i);

                    for(int j = 0; j < pairs.size(); j++){
                        String sign;
                        if(moveRight){
                            sign = "-";
                        }
                        else{
                            sign = "+";
                        }
                        int space = (layer == 0)?80:(80 / (layer * 2));
                        int parse = Integer.parseInt(sign + space);
                        Float toMove = pairs.get(j).getX() + parse;
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(pairs.get(j), "translationX", toMove);
                        objectAnimator.setDuration(1000);
                        ObjectAnimator objectAnimatorC = ObjectAnimator.ofFloat(tArr[circleIndex], "translationX", toMove);
                        objectAnimatorC.setDuration(1000);

                        objectAnimator.start();

                        objectAnimatorC.start();

                        circleIndex ++;
                    }
                    moveRight = !moveRight;


                }

            }
        });
        pause(Thread.currentThread(), 1000);

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

    public int getTreeHeight(double num){
        int count = 0;
        while(num > 1){
            double half = (double)num/2;
            num = Math.ceil(half);
            count ++;
        }
        return count + 1;
    }

    public void position(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int treeHeight = tArr.length;
                ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
                double layers = getTreeHeight((double)treeHeight);
                params.height = (int)layers * (130) + 40;
                relativeLayout.setLayoutParams(params);
            }
        });
    }

    public void swapArrTView1(int pos1, int pos2){
        float x1 = tArr[pos1].getX();
        float x2 = tArr[pos2].getX();
        float forward = x1;
        float backwards = x2;
        for(float i = x1; i<x2; i+=.009){
            tArr[pos1].setX(forward);
            tArr[pos2].setX(backwards);
            forward += .009;
            backwards-=.009;
        }
        TextView pos = tArr[pos1];
        tArr[pos1] = tArr[pos2];
        tArr[pos2] = pos;
    }

    public String arrayToString(int[] arr, boolean comma){
        String separator = comma?",":"-";
        int len = arr.length;
        String toString = "";
        for(int i = 0; i<len; i++){
            if(i == 0){
                toString += arr[0];
            }else{
                toString += separator + arr[i];
            }
        }
        return toString;
    }

    public void swapAni(int firstPos, int secondPos){
        final int pos1 = firstPos;
        final int pos2 = secondPos;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView txt1 = tArr[pos1];
                TextView txt2 = tArr[pos2];
                float x1 = txt1.getX() - leftMargin;
                float x2 = txt2.getX() - leftMargin;
                ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationX", x2);
                animation.setDuration(1000);
                ObjectAnimator animation2 = ObjectAnimator.ofFloat(txt2, "translationX", x1);
                animation2.setDuration(1000);
                animation.start();
                animation2.start();
                TextView pos = tArr[pos1];
                tArr[pos1] = tArr[pos2];
                tArr[pos2] = pos;
            }
        });
    }
}