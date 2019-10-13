package com.example.lugdu.datastructuresandalgorithms.algo.searching;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
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
import com.example.lugdu.datastructuresandalgorithms.Steps2Fragment;
import com.example.lugdu.datastructuresandalgorithms.StepsFragment;
import com.example.lugdu.datastructuresandalgorithms.StepsFragment;

import java.util.HashMap;

public class BinarySearchFragment extends Fragment {
    View view;
    int arr[];
    CircleText tArr[];
    TextView explanationText;
    RelativeLayout insertPoint;
    RelativeLayout insertPoint2;
    final int circleSize = 150;
    final int leftMargin = 0;
    final int rightMargin = 0;
    final int topMargin = 0;
    final int bottomMargin = 0;
    int w;
    Button pointerButton;
    SwitchCompat switchStatus;
    private ImageView[] dots;
    boolean hasStopped = false;
    ViewPager viewPager;
    boolean isRunning = false;
    EditText addArray;
    EditText searchFor;
    Button searchButton;
    int searchNum;
    public static int color = Color.parseColor("#1964B4");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_binary_search, container,false);
        TextView def = view.findViewById(R.id.definitionText);
        GradientDrawable gradientDrawable = new GradientDrawable();
        int[] colors = {Color.GRAY,color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        def.setAnimation(animation);
        w = MainActivity.width;
        setUpViewPager();

        explanationText = view.findViewById(R.id.explanationText);
        insertPoint = view.findViewById(R.id.animationView);
        insertPoint2 = view.findViewById(R.id.animationViewPointer);
        addArray = view.findViewById(R.id.arrInputBox);
        searchFor = view.findViewById(R.id.search_for);
        searchButton = view.findViewById(R.id.search_button);
        addArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArray.setSelection(addArray.getText().length());
            }
        });
        addArray.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(entryGood(addArray.getText().toString())) {
                        if(!addArray.getText().toString().equals("")){
                            parseArray(addArray.getText().toString());
                            initArraySorted();
                            searchFor.setEnabled(true);
                        }
                        else{
                            addArray.setText("");
                        }
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(addArray.getWindowToken(), 0);
                        addArray.setSelection(addArray.getText().length());
                        addArray.clearFocus();
                        //explanationText.setText("");
                        Toast.makeText(getContext(), "Input the start index", Toast.LENGTH_LONG).show();
                        return true;
                    }
                    else{
                        return true;
                    }
                }
                return false;
            }
        });

        searchFor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    searchNum = Integer.parseInt(searchFor.getText().toString());
                    pointerButton = initPointer(arr.length / 2);
                    explanationText.setText("Search will start at the half");
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(searchFor.isEnabled()) {
                            if(isRunning){
                                Toast.makeText(getContext(), "Already running", Toast.LENGTH_LONG).show();
                            }
                            else{
                                isRunning = true;
                                if(getActivity() != null && !hasStopped){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            explanationText.setText("Searching for " + searchNum);
                                        }
                                    });
                                }
                                binarySearch(arr, searchNum);
                                if(getActivity() != null && !hasStopped){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            for(int i = 0; i < tArr.length; i ++){
                                                tArr[i].deselect();
                                                Animation fadeOut = new AlphaAnimation(0, 1);
                                                fadeOut.setInterpolator(new DecelerateInterpolator());
                                                fadeOut.setDuration(1000);
                                                fadeOut.setFillAfter(true);
                                                tArr[i].clearAnimation();
                                                tArr[i].startAnimation(fadeOut);
                                            }
                                        }
                                    });

                                }
                                isRunning = false;
                            }
                        }
                    }
                });
                thread.start();
            }
        });

        return view;
    }

    public int binarySearch(int arr[], int x)
    {
        final int [] arr1 = arr;
        final int num = x;
        int l = 0, r = arr.length - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;
            final int left = l, right = r;
            final int mid = m;
            if(getActivity() != null && !hasStopped){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(pointerButton, "translationX", tArr[mid].getX());
                        objectAnimator.setDuration(1000);
                        objectAnimator.start();
                        tArr[mid].select(false);
                        pointerButton.setText("arr["+ mid +"]");
                    }
                });
            }
            pause(Thread.currentThread(), 1000);
            if(getActivity() != null && !hasStopped){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        explanationText.setText("is '" + arr1[mid] + "' = " + num);
                    }
                });
            }
            pause(Thread.currentThread(), 2000);
            // Check if x is present at mid
            if (arr[m] == x) {
                if(getActivity() != null && !hasStopped){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            explanationText.setText("Yes");
                            tArr[mid].sorted();
                        }
                    });
                }
                pause(Thread.currentThread(), 1500);
                if(getActivity() != null && !hasStopped){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            explanationText.setText(num + " found at index " + mid);
                            tArr[mid].sorted();
                        }
                    });
                }
                pause(Thread.currentThread(), 1500);
                return m;
            }
            if(getActivity() != null && !hasStopped){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        explanationText.setText("No");
                    }
                });
            }
            pause(Thread.currentThread(), 1500);
            if(getActivity() != null && !hasStopped){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        explanationText.setText("is '" + num + "' greater than " + arr1[mid] +" Or less than " + arr1[mid]);
                    }

                });
            }
            pause(Thread.currentThread(), 3000);
            // If x greater, ignore left half
            if (arr[m] < x) {
                if(getActivity() != null && !hasStopped){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            explanationText.setText("Greater than");
                        }
                    });
                }
                pause(Thread.currentThread(), 2000);
                if(getActivity() != null && !hasStopped){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = left; i <= mid; i ++){
                                Animation fadeOut = new AlphaAnimation(1, 0);
                                fadeOut.setInterpolator(new DecelerateInterpolator());
                                fadeOut.setDuration(1000);
                                fadeOut.setFillAfter(true);
                                tArr[i].clearAnimation();
                                tArr[i].startAnimation(fadeOut);
                            }
                        }
                    });
                }
                l = m + 1;
            }
                // If x is smaller, ignore right half
            else {
                if(getActivity() != null && !hasStopped){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            explanationText.setText("Less than");
                        }
                    });
                }
                pause(Thread.currentThread(), 1000);
                if(getActivity() != null && !hasStopped){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = right; i >= mid; i --){
                                Animation fadeOut = new AlphaAnimation(1, 0);
                                fadeOut.setInterpolator(new DecelerateInterpolator());
                                fadeOut.setDuration(1000);
                                fadeOut.setFillAfter(true);
                                tArr[i].clearAnimation();
                                tArr[i].startAnimation(fadeOut);
                            }

                        }
                    });
                }

                r = m - 1;
            }
            pause(Thread.currentThread(), 1000);
        }
        if(getActivity() != null && !hasStopped){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    explanationText.setText(num +" is not in the list");
                }
            });
        }
        pause(Thread.currentThread(), 1500);
        // if we reach here, then element was
        // not present
        return -1;
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

    public Button initPointer(int start){
        if(insertPoint2.getChildCount() > 0){
            insertPoint2.removeAllViews();
        }
        Button button = new Button(getContext());
        button.setAllCaps(false);
        button.setPadding(0, 0, 0, 0);
        Drawable drawableArrow = ContextCompat.getDrawable(
                getContext(),
                R.drawable.ic_arrow_down_black_24dp
        );
        button.setText("arr[" + start + "]");
        button.setX(tArr[start].getX());
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setCompoundDrawablesWithIntrinsicBounds(
                null, // Drawable left
                null, // Drawable top
                null, // Drawable right
                drawableArrow // Drawable bottom
        );
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize,180);
        layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
        insertPoint2.addView(button, layoutParams);
        return button;
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

    public void setUpViewPager(){
        LinearLayout dotsView = view.findViewById(R.id.sliderDots);
        HashMap<Integer, Fragment> steps =  getViewFragments();
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
        String[] strArr = getResources().getStringArray(R.array.binary_search);
        Bundle bundle = new Bundle();
        System.out.println(strArr.toString());
        bundle.putStringArray("string array", strArr);
        stepsFragment.setArguments(bundle);

        TextView textView = view.findViewById(R.id.definitionText);
        textView.setText("Definition: " + strArr[0]);

        Steps2Fragment StepsFragment2 = new Steps2Fragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("step", strArr[2]);
        //bundle2.putInt("image", R.drawable.for2);
        StepsFragment2.setArguments(bundle2);

        Steps2Fragment StepsFragment3 = new Steps2Fragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("step", strArr[3]);
        bundle3.putInt("image", R.drawable.b_search2);
        StepsFragment3.setArguments(bundle3);

        Steps2Fragment StepsFragment4 = new Steps2Fragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("step", strArr[4]);
        bundle4.putInt("image", R.drawable.b_search3);
        StepsFragment4.setArguments(bundle4);

        Steps2Fragment StepsFragment5 = new Steps2Fragment();
        Bundle bundle5 = new Bundle();
        bundle5.putString("step", strArr[5]);
        bundle5.putInt("image", R.drawable.b_search4);
        StepsFragment5.setArguments(bundle5);

        Steps2Fragment StepsFragment7 = new Steps2Fragment();
        Bundle bundle7 = new Bundle();
        bundle7.putString("step", strArr[7]);
        bundle7.putInt("image", R.drawable.b_search5);
        StepsFragment7.setArguments(bundle7);

        steps.put(0, stepsFragment);
        steps.put(1, StepsFragment2);
        steps.put(2, StepsFragment3);
        steps.put(3, StepsFragment4);
        steps.put(4, StepsFragment5);
        steps.put(5, StepsFragment7);

        return steps;
    }

    public void initArraySorted(){
        bubbleSort(arr);
        int len = arr.length;
        int totalLength = (len * (circleSize + leftMargin + rightMargin)) - leftMargin;
        int totalSpace = w - totalLength;
        int startSpace  = (totalSpace / 2);
        tArr = new CircleText[len];
        if(insertPoint.getChildCount() > 0){
            insertPoint.removeAllViews();
        }
        for(int i = 0; i<len; i++){
            CircleText textView = new CircleText(getContext());
            textView.setText(arr[i] + "");
            tArr[i] = textView;
            textView.setX((i * 160) + startSpace);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize,circleSize);
            layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            insertPoint.addView(textView, layoutParams);
        }
    }

    public boolean entryGood(String entry){
        String[] dashSplits = entry.split("-");
        boolean containsTripleDigits = false;
        if (dashSplits.length < 2) {
            Toast.makeText(getContext(),"Invalid Entry", Toast.LENGTH_LONG).show();
            return false;
        }
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

    public void bubbleSort(int arr[]){
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j] > arr[j+1])
                {
                    // swap arr[j+1] and arr[i]
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
    }

}
