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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lugdu.datastructuresandalgorithms.CircleText;
import com.example.lugdu.datastructuresandalgorithms.MainActivity;
import com.example.lugdu.datastructuresandalgorithms.PagerAdapter;
import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.Square;
import com.example.lugdu.datastructuresandalgorithms.Steps2Fragment;
import com.example.lugdu.datastructuresandalgorithms.StepsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InsertionSortFragment extends Fragment {
    View view;
    int arr[];
    TextView tArr[];
    TextView explanationText;
    int w;
    int circleSize = 120;
    int leftMargin = 20;
    int rightMargin = 0;
    int topMargin = 25;
    int bottomMargin = 0;
    boolean isRunning;
    boolean hasStopped = false;

    private ImageView[] dots;
    ViewPager viewPager;
    public static int color = Color.parseColor("#FF0000");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_insertion_sort, container,false);
        final Button button1 = view.findViewById(R.id.button1);
        final EditText editText = view.findViewById(R.id.topBox);
        explanationText = view.findViewById(R.id.explanationText);

        TextView def = view.findViewById(R.id.textView7);
        GradientDrawable gradientDrawable = new GradientDrawable();
        int[] colors = {Color.GRAY,color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        def.setAnimation(animation);

        w = MainActivity.width;
        setUpViewPager();

        isRunning = false;
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                        Toast.makeText(getContext(),"Invalid Entry", Toast.LENGTH_LONG).show();
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
                                        initArray();
                                        editText.setEnabled(false);
                                    }
                                });
                                isRunning = true;
                                hasStopped = false;
                                checkStatus();
                                insertionSort(arr);
                                isRunning = false;

                                if(getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            editText.setEnabled(true);
                                        }
                                    });
                                } else {
                                    return;
                                }
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

    public void checkStatus(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(getActivity() != null){

                }
                hasStopped = true;
            }
        });
        thread.start();
    }

    public void insertionSort(int[] array) {

        isRunning = true;
        int arrayLength = array.length;
        pause(Thread.currentThread(),2000);

        for (int j = 1; j < arrayLength; j++) {
            final int finalJ = j;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    explanationText.setText("Key is at index " + finalJ);
                }
            };
            if(getActivity() != null && !hasStopped) {
                getActivity().runOnUiThread(runnable);
            } else {
                return;
            }

            pause(Thread.currentThread(),2000);

            runnable = new Runnable() {
                @Override
                public void run() {
                    explanationText.setText("Select: " + arr[finalJ]);
                }
            };
            if(getActivity() != null && !hasStopped) {
                getActivity().runOnUiThread(runnable);
            } else {
                return;
            }

            pause(Thread.currentThread(),1000);
            if(getActivity() != null && !hasStopped) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((CircleText) tArr[finalJ]).select(true);
                        tArr[finalJ].invalidate();
                    }
                });
            } else {
                return;
            }

            int key = array[j];
            int i = j-1;

            final int[] updatedKey = new int[1];

            moveDownAni(j);

            pause(Thread.currentThread(),2000);
            boolean elementsSwapped = false;

            //while i is greater than negative 1 and while the element at position i is greater than what's in front of it
            while ((i> -1) && (array[i] > key)) {

                final int finalI = i;
                final int finalKey = key;

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        explanationText.setText("Check: " + arr[finalI]);
                    }
                };
                if (getActivity() != null && !hasStopped) {
                    getActivity().runOnUiThread(runnable);
                }else {
                    return;
                }

                pause(Thread.currentThread(), 1000);

                if(getActivity() != null && !hasStopped) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((CircleText) tArr[finalI]).select(false);
                        }
                    });
                }else{
                    return;
                }

                pause(Thread.currentThread(), 1000);

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        explanationText.setText("Since '" + finalKey + "' is greater than '" + arr[finalI] + "', swap them.");
                    }
                };
                if (getActivity() != null && !hasStopped) {
                    getActivity().runOnUiThread(runnable);
                }else{
                    return;
                }

                pause(Thread.currentThread(),1000);

                elementsSwapped = true;
                //swap the i and the element that key represents
                swapArrInt(i+1, i);
                swapHorizontally(i +1, i);
                updatedKey[0] = i;
                pause(Thread.currentThread(),2000);
                final int finalI1 = i;
                if(getActivity() != null && !hasStopped) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((CircleText) tArr[finalI1 + 1]).deselect();
                        }
                    });
                }
                else{
                    return;
                }

                i--;
            }

            if(elementsSwapped == true) {
                moveUp(updatedKey[0]);


            } else {
                //if the elements weren't swapped, then the key remains in the same position, therefore just move that one up
                moveUp(finalJ);
            }
            pause(Thread.currentThread(), 2000);
            if(getActivity() != null && !hasStopped) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((CircleText) tArr[updatedKey[0]]).deselect();
                        tArr[updatedKey[0]].invalidate();
                    }
                });

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((CircleText) tArr[finalJ]).deselect();
                        tArr[finalJ].invalidate();
                    }
                });
            }else{
                return;
            }

            pause(Thread.currentThread(),1000);
            //make the first element in the array equal to the current key
            array[i+1] = key;
        }
        if(getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = view.findViewById(R.id.textView4);
                    textView.setText("Sorted Array: [ " + arrayToString(arr, true) + " ]");
                }
            });
        } else {
            return;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                explanationText.setText("The array is sorted!");
            }
        };
        if(getActivity() != null && !hasStopped) {
            getActivity().runOnUiThread(runnable);
        } else {
            return;
        }

        pause(Thread.currentThread(),1000);
        setSortedAll();
        isRunning = false;
    }

    public void swapArrInt(int pos1, int pos2){
        int pos = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = pos;
    }

    public boolean entryGood(String entry){
        String[] dashSplits = entry.split("-");
        boolean containsTripleDigits = false;
        if (dashSplits.length < 2) {
            Toast.makeText(getContext(),"Invalid Entry", Toast.LENGTH_LONG).show();
            return false;
        }
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
            textView.setX((i * 130) + startSpace-28);
            tArr[i] = textView;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize,circleSize);
            layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            insertPoint.addView(textView, layoutParams);

        }
    }

    public void moveDownAni(int pos1) {
        final int pos = pos1;
        if(getActivity() != null && !hasStopped){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView txt1 = tArr[pos];


                float x1 = (txt1.getY() + circleSize + 10);
                ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationY", x1);
                animation.setDuration(1000);

                animation.start();
            }
        });}
        else{
            return;
        }
    }

    public void swapHorizontally(int firstPos1, int secondPos1) {
        final int firstPos = firstPos1;
        final int secondPos = secondPos1;
        if(getActivity() != null && !hasStopped){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final int pos1 = firstPos;
                final int pos2 = secondPos;
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
        });}
        else{
            return;
        }
    }

    public void moveUp(int pos1) {
        final int pos = pos1;
        if(getActivity() != null && !hasStopped) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView txt1 = tArr[pos];

                    float x1 = (txt1.getY() - (circleSize + 10)) - topMargin * 2;

                    ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationY", x1);

                    animation.setDuration(1000);

                    animation.start();
                }
            });
        } else {
            return;
        }
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

    public void setSortedAll(){
        for(final TextView T: tArr){
            if(getActivity() != null && !hasStopped) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((CircleText) T).sorted();
                    }
                });
            }
            else{
                return;
            }
        }
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

    public void setUpViewPager(){
        LinearLayout dotsView = view.findViewById(R.id.sliderDots);
        HashMap<Integer, Fragment> steps = getViewFragments();
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

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(),steps.size(),steps);
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

    public HashMap<Integer, Fragment> getViewFragments(){
        HashMap<Integer, Fragment> steps = new HashMap<>();
        StepsFragment stepsFragment = new StepsFragment();
        String[] strArr = getResources().getStringArray(R.array.insertion);
        Bundle bundle = new Bundle();
        System.out.println(strArr.toString());
        bundle.putStringArray("string array", strArr);
        stepsFragment.setArguments(bundle);

        Steps2Fragment steps2Fragment1 = new Steps2Fragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("step", strArr[1]);
        bundle1.putInt("image", R.drawable.insertion_1);
        steps2Fragment1.setArguments(bundle1);


        steps.put(0, stepsFragment);
        steps.put(1, steps2Fragment1);

        return steps;
    }
}