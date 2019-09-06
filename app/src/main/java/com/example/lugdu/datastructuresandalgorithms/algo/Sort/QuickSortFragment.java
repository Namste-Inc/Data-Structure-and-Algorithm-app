package com.example.lugdu.datastructuresandalgorithms.algo.Sort;

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

import java.lang.reflect.Array;
import java.util.Arrays;

public class QuickSortFragment extends Fragment {
    View view;
    int arr[];
    TextView tArr[];
    TextView explanationText;
    boolean isRunning;

    int circleSize = 120;
    int leftMargin = 20;
    int rightMargin = 0;
    int topMargin = 25;
    int bottomMargin = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quick_sort, container,false);
        final Button button1 = view.findViewById(R.id.button1);
        final EditText editText = view.findViewById(R.id.topBox);
        explanationText = view.findViewById(R.id.explanationText);
        isRunning = false;
//        editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(!editText.isInEditMode()){
//                    String[] afterC = editText.getText().toString().split(": ");
//                    if(afterC.length > 1){
//                        editText.setText(afterC[1]);
//                    }
//                    editText.setSelection(editText.getText().length());
//                }
//            }
//        });
//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId == EditorInfo.IME_ACTION_DONE){
//                    if(entryGood(editText.getText().toString())) {
//                        if(!editText.getText().toString().equals("")){
//                            parseArray(editText.getText().toString());
//                            initArray();
//                        }
//                        editText.setText(editText.getText());
//                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
//                        editText.clearFocus();
//                        editText.setSelection(editText.getText().length());
//                        return true;
//                    }
//                    else{
//                        Toast.makeText(getContext(),"Invalid Entry", Toast.LENGTH_LONG).show();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
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
                        int arr[] = {3, 6,4,8,1,5};
                        quickSort(arr, 0, 5);

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

    public void quickSort(int arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
        System.out.println("From here: " + Arrays.toString(arr));
    }

    private int partition(int arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);
        System.out.println("Here's the pivot: " + pivot);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
                System.out.println("In if statement: " + Arrays.toString(arr));
            }
        }
        System.out.println("After if: " + Arrays.toString(arr));

        int swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;

        System.out.println("After one partition: " + Arrays.toString(arr));

        return i+1;
    }
}