package com.example.lugdu.datastructuresandalgorithms.algo.Sort;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lugdu.datastructuresandalgorithms.CircleText;
import com.example.lugdu.datastructuresandalgorithms.R;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HeapSortFragment extends Fragment {
    View view;
    int arr[];
    TextView tArr[];
    TextView tArrTest[];
    TextView explanationText;

    RelativeLayout relativeLayout;
    int h,w;
    int circleSize = 120;
    int leftMargin = 20;
    int rightMargin = 0;
    int topMargin = 25;
    int bottomMargin = 0;
    boolean isRunning;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_heap_sort, container,false);
        final Button button1 = view.findViewById(R.id.button1);
        final EditText editText = view.findViewById(R.id.topBox);
        explanationText = view.findViewById(R.id.explanationText);
        isRunning = false;
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
                            //createTree();
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
//                        if(arr == null || arr.length == 0){
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(getContext(),"Must enter an array", Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        }
//                        else {
//                            if(isRunning){
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(getContext(),"Animation running, please wait", Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                            }
//                            else{
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        editText.setEnabled(false);
//                                    }
//                                });
//                                int arr[] = {3, 44, 38, 5, 7};
//                                quickSort(arr, 3, 7);
//                                System.out.println(arr);
//
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        editText.setEnabled(true);
//                                    }
//                                });
//                            }
//                        }
                        //int array[] = {4,10,3,5,1};
                        heapSort(arr);


                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
        return view;
    }
    public boolean entryGood(String entry){
        if(entry.startsWith("-") || entry.endsWith("-") || entry.contains("--")){
            return false;
        }
        return true;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void initArray(){
        int testSpace = 50;

        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - 200;
        int height = size.y;

        int len = arr.length;
        tArr = new TextView[len];
        RelativeLayout insertPoint = view.findViewById(R.id.lView);
        if(insertPoint.getChildCount() > 0){
            insertPoint.removeAllViews();
        }
        for(int i = 0; i<len; i++){
            TextView textView = new CircleText(getContext());
            textView.setText(arr[i] + "");

            //Set the floors as ints and do "floor*100" instead of "i*100"
            switch (i) {
                case 0:
                    textView.setX(width/2);
                    textView.setY((i*100) + testSpace);
                    break;

                case 1:
                    textView.setX((width/2) - 150);
                    textView.setY((i*150) + testSpace);
                    break;

                case 2:
                    textView.setX((width/2) + 150);
                    textView.setY(((i-1)*150) + testSpace);
                    break;

                case 3:
                    textView.setX((width/2) - 225);
                    textView.setY(((i-1)*150) + testSpace);
                    break;

                case 4:
                    textView.setX((width/2) - 75);
                    textView.setY(((i-2)*150) + testSpace);
                    break;

                case 5:
                    textView.setX((width/2) + 75);
                    textView.setY(((i-3)*150) + testSpace);
                    break;

                case 6:
                    textView.setX((width/2) + 225);
                    textView.setY(((i-4)*150) + testSpace);
                    break;
            }
            //textView.setX((i * 130) + testSpace);

            System.out.println("Here it is: " + textView.getY());

            tArr[i] = textView;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize,circleSize);
            layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            insertPoint.addView(textView, layoutParams);
        }
    }

    public void heapSort(int[] arr) {
        int n = arr.length;
        System.out.println("Here is n: " + n);

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            System.out.println("Here is i: " + i);
            heapify(arr, n, i);
        }

        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end

            swapAni(0,i);
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            pause(Thread.currentThread(),1000);

            moveDownAni(i);

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    public void heapify(int arr[], int n, int i) {
        System.out.println("Before heapify: " + Arrays.toString(arr));

        int largest = i; // Initialize largest as root
        int l = 2*i + 1; // left = 2*i + 1
        int r = 2*i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }


        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }


        // If largest is not root
        if (largest != i) {
            pause(Thread.currentThread(),1000);
            swapAni(i,largest);

            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            System.out.println("After first animation: " + Arrays.toString(arr));
            pause(Thread.currentThread(),1000);


            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);


        }
        System.out.println("After heapify: " + Arrays.toString(arr));
    }

    public void moveDownAni(int pos1) {
        final int pos = pos1;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView txt1 = tArr[pos];

                System.out.println("initial height: " + txt1.getY());
                float x1 = (txt1.getY() + circleSize + 10);
                System.out.println("initial x1: " + x1);

                ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationY", x1);
                animation.setDuration(1000);

                animation.start();
            }
        });
    }

    public void swapAni(int firstPos, int secondPos){
        final int pos1 = firstPos;
        final int pos2 = secondPos;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView txt1 = tArr[pos1];
                TextView txt2 = tArr[pos2];
                float x1 = txt1.getX();
                float y1 = txt1.getY();
                float x2 = txt2.getX();
                float y2 = txt2.getY();
                ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationX", x2);
                ObjectAnimator animationY1 = ObjectAnimator.ofFloat(txt1, "translationY", y2);
                animation.setDuration(1000);
                animationY1.setDuration(1000);

                ObjectAnimator animation2 = ObjectAnimator.ofFloat(txt2, "translationX", x1);
                ObjectAnimator animationY2 = ObjectAnimator.ofFloat(txt2, "translationY", y1);
                animation2.setDuration(1000);
                animationY2.setDuration(1000);

                animation.start();
                animation2.start();
                animationY1.start();
                animationY2.start();
                TextView pos = tArr[pos1];
                tArr[pos1] = tArr[pos2];
                tArr[pos2] = pos;
            }
        });
    }

    public void removeCircle(int pos1) {
        final int pos = pos1;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView txt1 = tArr[pos];

                System.out.println("initial height: " + txt1.getY());
                float x1 = (txt1.getY() + circleSize + 10000);
                System.out.println("initial x1: " + x1);

                ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationY", x1);
                animation.setDuration(1000);

                animation.start();
            }
        });
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
}