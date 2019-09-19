package com.example.lugdu.datastructuresandalgorithms.algo.Sort;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lugdu.datastructuresandalgorithms.CircleText;
import com.example.lugdu.datastructuresandalgorithms.MainActivity;
import com.example.lugdu.datastructuresandalgorithms.PagerAdapter;
import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.StepsFragment;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class HeapSortFragment extends Fragment {
    View view;
    int arr[];
    TextView tArr[];
    TextView tArrLinear[];
    TextView explanationText;

    int circleSize = 120;
    int leftMargin = 20;
    int rightMargin = 0;
    int topMargin = 25;
    int bottomMargin = 0;
    boolean isRunning;

    int w;
    private ImageView[] dots;
    public static int color = Color.parseColor("#FF0000");

    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_heap_sort, container,false);
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

        setUpViewPager();

        isRunning = false;
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText(arrayToString(arr,false));
                editText.setSelection(editText.getText().length());
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(entryGood(editText.getText().toString())) {
                        if(!editText.getText().toString().equals("")){
                            parseArray(editText.getText().toString());
                            initArray();
                            linearInitArray();
                            editText.setText("Original array: [ " + arrayToString(arr,true) + " ]");
                        } else {
                            editText.setText("");
                        }

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        editText.clearFocus();
                        editText.setSelection(editText.getText().length());
                        return true;
                    }
                    else{
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
                        } else if(arr.length > 7) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(),"Array must be size 7(max)", Toast.LENGTH_LONG).show();
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

                                heapSort(arr);

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
    public boolean entryGood(String entry){
        String[] dashSplits = entry.split("-");
        boolean containsTripleDigits = false;
        System.out.println(dashSplits[1]);
        for (int i = 0; i <dashSplits.length; i++) {
            if (dashSplits[i].length() > 2) {
                containsTripleDigits = true;
            }
        }

        if(entry.startsWith("-") || entry.endsWith("-") || entry.contains("--")){
            Toast.makeText(getContext(),"Invalid Entry", Toast.LENGTH_LONG).show();

            return false;
        } else if (dashSplits.length > 7) {
            Toast.makeText(getContext(),"Array size must not be larger than 7.", Toast.LENGTH_LONG).show();

            return false;
        } else if (containsTripleDigits) {
            Toast.makeText(getContext(),"Inputs must not be larger than 99.", Toast.LENGTH_LONG).show();

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

        int len = arr.length;



        tArr = new TextView[len];
        RelativeLayout insertPoint = view.findViewById(R.id.lView);
        if(insertPoint.getChildCount() > 0){
            insertPoint.removeAllViews();
        }
        for(int i = 0; i<len; i++){
            //if input reaches more than 7 numbers
            //do not initialize the rest
            if (i == 7) {
                break;
            }
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
            tArr[i] = textView;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize,circleSize);
            layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            insertPoint.addView(textView, layoutParams);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void linearInitArray() {
        int linearLen = arr.length;
        tArrLinear = new TextView[linearLen];
        RelativeLayout insertPointLinear = view.findViewById(R.id.linearAnimation);
        if(insertPointLinear.getChildCount() > 0){
            insertPointLinear.removeAllViews();
        }
        for(int i = 0; i<linearLen; i++){
            //if input reaches more than 7 numbers
            //do not initialize the remaining numbers
            if (i == 7) {
                break;
            }
            TextView textViewLinear = new CircleText(getContext());
            textViewLinear.setText(arr[i] + "");
            textViewLinear.setX(i * 130);
            textViewLinear.setY(-20);
            tArrLinear[i] = textViewLinear;
            RelativeLayout.LayoutParams layoutParamsLinear = new RelativeLayout.LayoutParams(circleSize,circleSize);
            layoutParamsLinear.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            insertPointLinear.addView(textViewLinear, layoutParamsLinear);
        }
    }

    public void heapSort(final int[] arr) {
        isRunning=true;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = view.findViewById(R.id.textView4);
                textView.setText("Sorted Array:");
            }
        });
        int n = arr.length;

        // Build heap (rearrange array)
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                explanationText.setText("Build heap.");
            }
        };
        getActivity().runOnUiThread(runnable);
        for (int i = n / 2 - 1; i >= 0; i--) {

            pause(Thread.currentThread(),1000);

            heapify(arr, n, i);
        }

        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {

            // Move current root to end
            final int finalI  = i;
            final int currentRoot = arr[0];

            runnable = new Runnable() {
                @Override
                public void run() {
                    explanationText.setText("Now that the largest node '" + currentRoot + "' is the root, swap it with the node " +
                            "at the end of the tree/array that is unsorted.");
                }
            };
            pause(Thread.currentThread(),1000);

            getActivity().runOnUiThread(runnable);
            ((CircleText) tArr[0]).select(true);
            tArr[i].invalidate();

            ((CircleText) tArrLinear[0]).select(true);
            tArr[i].invalidate();

            pause(Thread.currentThread(),1000);

            ((CircleText) tArr[i]).select(true);
            tArr[i].invalidate();

            ((CircleText) tArrLinear[i]).select(true);
            tArr[i].invalidate();

            pause(Thread.currentThread(),1000);

            swapAni(0,i);
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            pause(Thread.currentThread(),1000);

            ((CircleText) tArr[0]).deselect();
            tArr[i].invalidate();

            ((CircleText) tArrLinear[0]).deselect();
            tArr[i].invalidate();

            runnable = new Runnable() {
                @Override
                public void run() {
                    explanationText.setText(currentRoot + " is sorted!");
                }
            };
            getActivity().runOnUiThread(runnable);

            ((CircleText) tArr[i]).sorted();
            tArr[i].invalidate();

            ((CircleText) tArrLinear[i]).sorted();
            tArr[i].invalidate();

            pause(Thread.currentThread(),1000);

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }

        runnable = new Runnable() {
            @Override
            public void run() {
                explanationText.setText("Array has been sorted!");
            }
        };
        getActivity().runOnUiThread(runnable);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = view.findViewById(R.id.textView4);
                textView.setText("Sorted Array: [ " + arrayToString(arr,true) + " ]");
            }
        });
        isRunning = false;
    }

    public void heapify(int arr[], int n, int i) {

        int largest = i; // Initialize largest as root
        int l = 2*i + 1; // left = 2*i + 1
        int r = 2*i + 2; // right = 2*i + 2



        final int finalI = i;
        System.out.println("Here is i: " + i);




        // If left child is larger than root
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }


        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        final int finalLargest = largest;
        final int parent = arr[i];
        final int child = arr[largest];
        // If largest is not root
        if (largest != i) {
            pause(Thread.currentThread(),1000);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    explanationText.setText("Parent '" + parent + "' is larger than child '" + child + "'.");
                }
            };
            getActivity().runOnUiThread(runnable);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((CircleText) tArr[finalI]).select(true);
                    tArr[finalI].invalidate();

                    ((CircleText) tArrLinear[finalI]).select(true);
                    tArr[finalI].invalidate();
                }
            });
            pause(Thread.currentThread(),1000);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((CircleText) tArr[finalLargest]).select(true);
                    tArr[finalLargest].invalidate();

                    ((CircleText) tArrLinear[finalLargest]).select(true);
                    tArr[finalLargest].invalidate();
                }
            });
            pause(Thread.currentThread(),1000);

            runnable = new Runnable() {
                @Override
                public void run() {
                    explanationText.setText("Swap parent '" + parent + "' and child '" + child + "'.");
                }
            };
            getActivity().runOnUiThread(runnable);
            pause(Thread.currentThread(),1000);

            swapAni(i,largest);

            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            pause(Thread.currentThread(),1000);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((CircleText) tArr[finalI]).deselect();
                    tArr[finalI].invalidate();

                    ((CircleText) tArrLinear[finalI]).deselect();
                    tArr[finalI].invalidate();

                    ((CircleText) tArr[finalLargest]).deselect();
                    tArr[finalLargest].invalidate();

                    ((CircleText) tArrLinear[finalLargest]).deselect();
                    tArr[finalLargest].invalidate();
                }
            });

            pause(Thread.currentThread(),1000);

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    public void swapAni(int firstPos, int secondPos){
        final int pos1 = firstPos;
        final int pos2 = secondPos;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView txt1 = tArr[pos1];
                TextView txt1Linear = tArrLinear[pos1];

                TextView txt2 = tArr[pos2];
                TextView txt2Linear = tArrLinear[pos2];

                float x1 = txt1.getX() - leftMargin;
                float y1 = txt1.getY() - topMargin;
                float x2 = txt2.getX() - leftMargin;
                float y2 = txt2.getY() - topMargin;

                float x1Linear = txt1Linear.getX() - leftMargin;
                float y1Linear = txt1Linear.getY() - topMargin;
                float x2Linear = txt2Linear.getX() - leftMargin;
                float y2Linear = txt2Linear.getY() - topMargin;

                ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationX", x2);
                ObjectAnimator animationY1 = ObjectAnimator.ofFloat(txt1, "translationY", y2);
                animation.setDuration(1000);
                animationY1.setDuration(1000);

                ObjectAnimator animation2 = ObjectAnimator.ofFloat(txt2, "translationX", x1);
                ObjectAnimator animationY2 = ObjectAnimator.ofFloat(txt2, "translationY", y1);
                animation2.setDuration(1000);
                animationY2.setDuration(1000);

                ObjectAnimator animationX1Linear = ObjectAnimator.ofFloat(txt1Linear, "translationX", x2Linear);
                ObjectAnimator animationY1Linear = ObjectAnimator.ofFloat(txt1Linear, "translationY", y2Linear);
                animationX1Linear.setDuration(1000);
                animationY1Linear.setDuration(1000);

                ObjectAnimator animationX2Linear = ObjectAnimator.ofFloat(txt2Linear, "translationX", x1Linear);
                ObjectAnimator animationY2Linear = ObjectAnimator.ofFloat(txt2Linear, "translationY", y1Linear);
                animationX2Linear.setDuration(1000);
                animationY2Linear.setDuration(1000);

                animation.start();
                animation2.start();
                animationY1.start();
                animationY2.start();

                animationX1Linear.start();
                animationY1Linear.start();
                animationX2Linear.start();
                animationY2Linear.start();

                TextView pos = tArr[pos1];
                tArr[pos1] = tArr[pos2];
                tArr[pos2] = pos;

                TextView posLinear = tArrLinear[pos1];
                tArrLinear[pos1] = tArrLinear[pos2];
                tArrLinear[pos2] = posLinear;

            }
        });
        pause(Thread.currentThread(),1000);
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

    public void setUpViewPager(){
        LinearLayout dotsView = view.findViewById(R.id.sliderDots);
        HashMap<Integer, Fragment> steps = new HashMap<>();
        StepsFragment stepsFragment = new StepsFragment();
        steps.put(0,stepsFragment);
        StepsFragment stepsFragment1 = new StepsFragment();
        steps.put(1,stepsFragment1);
        StepsFragment stepsFragment2 = new StepsFragment();
        steps.put(2,stepsFragment2);
        viewPager = view.findViewById(R.id.viewpager);
        dots = new ImageView[steps.size()];
        for(int i = 0; i < dots.length; i++){
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.inactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            dotsView.addView(dots[i], params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(),steps.size(),steps);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for(int j = 0; j< dots.length; j++){
                    dots[j].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.inactive_dot));
                }
                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}