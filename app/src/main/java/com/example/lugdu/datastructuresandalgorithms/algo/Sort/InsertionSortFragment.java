package com.example.lugdu.datastructuresandalgorithms.algo.Sort;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lugdu.datastructuresandalgorithms.CircleText;
import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.Square;

import java.util.ArrayList;
import java.util.Arrays;

public class InsertionSortFragment extends Fragment {
    View view;
//    int arr[] = {5,3,4,7,6,10,2,13,8};
    int arr[];
    TextView tArr[];
    TextView explanationText;

    RelativeLayout relativeLayout;
    final int squareSize = 100;
    int h,w;
    int circleSize = 120;
    int leftMargin = 20;
    int rightMargin = 0;
    int topMargin = 25;
    int bottomMargin = 0;
    ArrayList<ArrayList<ArrayList<Square>>> squares = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_insertion_sort, container,false);
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
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("Original array: " + Arrays.toString(arr));

                        insertionSort(arr);
                        System.out.println("Sorted array: " + Arrays.toString(arr));

                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
        return view;
    }

    public void insertionSort(int[] array) {
        int arrayLength = array.length;

        for (int j = 1; j < arrayLength; j++) {
            System.out.println("beginning here: " + Arrays.toString(array));

            int key = array[j];
            int i = j-1;

            final int finalJ = j;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    moveDownAni(finalJ);
                }
            });
            pause(Thread.currentThread(),2000);

            //while i is greater than negative 1 and while the element at position i is greater than what's in front of it
            while ((i> -1) && (array[i] > key)) {
                //make the element in front of i equal to i


                //swap the i and the element that key represents
                swapArrInt(i+1, i);

                final int finalI = i;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swapHorizontally(finalI +1, finalI);
                    }
                });
                pause(Thread.currentThread(),2000);

                i--;
                System.out.println("in the while: " + Arrays.toString(array));
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("We got here " + tArr[finalJ].getY());
                    moveUp(finalJ);
                }
            });
            pause(Thread.currentThread(),1000);
            //make the first element in the array equal to the current key
            array[i+1] = key;
        }
    }
    public void swapArrInt(int pos1, int pos2){
        int pos = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = pos;
    }

    public boolean entryGood(String entry){
        if(entry.startsWith(",") || entry.endsWith(",") || entry.contains(",,")){
            return false;
        }
        return true;
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
            textView.setX(i * 130);
            tArr[i] = textView;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize,circleSize);
            layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            insertPoint.addView(textView, layoutParams);

        }
    }

    public void moveDownAni(int pos) {
        TextView txt1 = tArr[pos];

        System.out.println("initial height: " + txt1.getY());
        float x1 = (txt1.getY() + circleSize + 10);
        System.out.println("initial x1: " + x1);


        ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationY", x1);
        animation.setDuration(1000);


        animation.start();
    }

    public void swapHorizontally(int firstPos, int secondPos) {
        final int pos1 = firstPos;
        final int pos2 = secondPos;
        TextView txt1 = tArr[pos1];
        TextView txt2 = tArr[pos2];
        float x1 = txt1.getX();
        float x2 = txt2.getX();
        System.out.println(x2);
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

    public void moveUp(int pos) {
        TextView txt1 = tArr[pos];

        System.out.println("1 " + txt1.getY());
        float x1 = (txt1.getY() - (circleSize + 10)) - topMargin * 2;
        System.out.println("2 " + x1);

        ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationY", x1);

        animation.setDuration(1000);

        animation.start();
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

    public int getTreeHeight(double num){
        int count = 0;
        while(num > 1){
            double half = (double)num/2;
            num = Math.ceil(half);
            count ++;
        }
        return count + 1;
    }
}