package com.example.lugdu.datastructuresandalgorithms.algo.Sort;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lugdu.datastructuresandalgorithms.CircleText;
import com.example.lugdu.datastructuresandalgorithms.MainActivity;
import com.example.lugdu.datastructuresandalgorithms.R;

public class SelectionSortFragment extends Fragment {
    View view;
    int arr[];
    TextView tArr[];
    TextView explanationText;
    final int circleSize = 120;
    final int leftMargin = 20;
    final int rightMargin = 0;
    final int topMargin = 0;
    final int bottomMargin = 25;
    int w;
    boolean isRunning;
    public static int color = Color.parseColor("#FF0000");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_selection_sort, container,false);
        final Button button1 = view.findViewById(R.id.button1);
        final EditText editText = view.findViewById(R.id.topBox);
        TextView def = view.findViewById(R.id.textView7);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(50);
        int[] colors = {Color.GRAY,color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        explanationText = view.findViewById(R.id.explanationText);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        def.setAnimation(animation);
        w = MainActivity.width;

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(arrayToString(arr,false));
                editText.setSelection(editText.getText().length());
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
                            editText.setText("Original array: [ " + arrayToString(arr,true) + " ]");
                        }
                        else{
                            editText.setText("");
                        }

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
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
                        if(arr == null || arr.length == 0){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),"Must enter an array", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else {
                            if(isRunning){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(),"Animation running, please wait", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else{
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        editText.setEnabled(false);
                                    }
                                });

                                selectionSort(arr);

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        editText.setEnabled(true);
                                    }
                                });
                            }
                        }
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
        return view;
    }

    public void selectionSort(int arr[]) {
        int n = arr.length;
        int min_idx =0;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++) {
            final int finalI = i;
            // Find the minimum element in unsorted array
            min_idx = i;

            for (int j = i+1; j < n; j++) {
                final int finalJ = j;
                if (arr[j] < arr[min_idx]) {
                    min_idx = j;
                }
            }
            final int final_min = min_idx;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((CircleText) tArr[final_min]).sorted();
                }
            });
            pause(Thread.currentThread(), 2000);

            // Swap the found minimum element with the first
            // element
            swapArrInt(min_idx, i);
            swapAni(min_idx, i);
            pause(Thread.currentThread(),1000);
        }
        final int last = n - 1;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((CircleText) tArr[last]).sorted();
            }
        });
        pause(Thread.currentThread(),1000);

    }

    public void swapArrInt(int pos1, int pos2){
        int pos = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = pos;
    }

    public boolean entryGood(String entry){
        if(entry.startsWith("-") || entry.endsWith("-") || entry.contains("--")){
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

    public void initArray(){
        int len = arr.length;
        int totalLength = (len * (circleSize + leftMargin + rightMargin)) - leftMargin;
        int totalSpace = w - totalLength;
        int startSpace  = (totalSpace / 2);
        tArr = new TextView[len];
        RelativeLayout insertPoint = view.findViewById(R.id.lView);
        if(insertPoint.getChildCount() > 0){
            insertPoint.removeAllViews();
        }
        for(int i = 0; i<len; i++){
            TextView textView = new CircleText(getContext());
            textView.setText(arr[i] + "");
            tArr[i] = textView;
            textView.setX((i * 130) + startSpace);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize,circleSize);
            layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            insertPoint.addView(textView, layoutParams);
        }
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

    public String arrayToString(int[] arr, boolean comma){
        String separator = comma?",":"-";
        int len = 0;
        if (arr != null) {
            len = arr.length;
        }
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
}
