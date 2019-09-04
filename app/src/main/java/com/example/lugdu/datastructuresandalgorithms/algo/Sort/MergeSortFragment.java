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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    RelativeLayout relativeLayout;
    final int squareSize = 100;

    ArrayList<ArrayList<ArrayList<Square>>> squares = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        position();
                        splitAni(0);
                        splitAni(1);
                        //mergeSort(arr, arr.length);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
        return view;
    }

    public void mergeSort(int[] array, int sizeOfArray) {
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
        mergeSort(firstHalf, mid);

        mergeSort(secondHalf, sizeOfArray - mid);

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

    public boolean entryGood(String entry){
        if(entry.startsWith(",") || entry.endsWith(",") || entry.contains(",,")){
            return false;
        }
        return true;
    }

    public void initArray(){
        int len = arr.length;
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
            textView.setX(i * 105);
            Square square = new Square(getContext());
            square.setX(i * 105);
            squareArr.add(square);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(squareSize,squareSize);
            layoutParams.setMargins(20,25,0,0);
            relativeLayout.addView(square, layoutParams);
            relativeLayout.addView(textView, layoutParams);
            Toast.makeText(getContext(),relativeLayout.getHeight() + "",Toast.LENGTH_LONG).show();
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArrayList<Square>> temp = copyLayer(layer);
                int len = temp.size();
                int half = len /2;
                ArrayList<Square> temp2 = new ArrayList<>();
                ArrayList<ArrayList<Square>> temp3 = new ArrayList<>();
                for(int i = 0; i<len; i++){
                    for(int j = 0; j<temp.get(i).size(); j++) {
                        Square square = new Square(getContext());
                        square.setX(temp.get(i).get(j).getX());
                        square.setY(temp.get(i).get(j).getY() + 100);
                        temp2.add(square);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(squareSize, squareSize);
                        layoutParams.setMargins(0, 5, 0, 0);
                        relativeLayout.addView(square, layoutParams);
                    }
                }
                temp3.add(temp2);
                squares.add(temp3);
                ArrayList<ArrayList<Square>> tempAni = squares.get(layer + 1);
                for(int i = 0; i<tempAni.size(); i++){
                    ArrayList<Square>tempAni2 = tempAni.get(i);
                    int low = 0;
                    int high = tempAni2.size() -1;
                    while(low<=high){
                        tempAni2.get(high).setX(tempAni2.get(high).getX() + 25);
                        if(low == high){
                            break;
                        }else{
                            System.out.println("low " + low + "high " + high);
                            tempAni2.get(low).setX(tempAni2.get(low).getX() - 25);
                        }
                        low++;
                        high--;
                    }

                }

            }
        });


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
        int treeHeight = tArr.length;
        ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
        double layers = getTreeHeight((double)treeHeight);
        params.height = (int)layers * (130) + 40;
        int totalLength = treeHeight * (5 + squareSize);
        int totalSpace = w - totalLength;
        int space  = totalSpace / 2;
        int toGo = (w - space) - (int)squares.get(0).get(0).get(tArr.length-1).getX() - squareSize - 20;
        System.out.println(toGo + " Space");
        for(int i = tArr.length - 1; i>-1; i--){
            for(int j = 0; j<toGo; j+=10){
                squares.get(0).get(0).get(i).setX(squares.get(0).get(0).get(i).getX() + 10);
                tArr[i].setX(tArr[i].getX() + 10);
                try {
                    Thread.currentThread().sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
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
}