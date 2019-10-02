package com.example.lugdu.datastructuresandalgorithms.algo.searching;

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
import android.support.v7.widget.SwitchCompat;
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
import com.example.lugdu.datastructuresandalgorithms.Steps2Fragment;
import com.example.lugdu.datastructuresandalgorithms.StepsFragment;

import java.util.HashMap;

public class LinearSearchFragment extends Fragment {
    View view;
    int arr[];
    TextView tArr[];
    TextView explanationText;
    RelativeLayout insertPoint;
    RelativeLayout insertPoint2;
    final int circleSize = 150;
    final int leftMargin = 20;
    final int rightMargin = 0;
    final int topMargin = 0;
    final int bottomMargin = 0;
    int w;
    Button pointerButton;
    SwitchCompat switchStatus;
    private ImageView[] dots;
    boolean hasStopped = false;
    ViewPager viewPager;
    boolean isRunning;
    EditText addArray;
    EditText searchFor;
    Button searchButton;
    int searchNum;
    public static int color = Color.parseColor("#1964B4");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_linear_search, container,false);
        TextView def = view.findViewById(R.id.definitionText);
        GradientDrawable gradientDrawable = new GradientDrawable();
        insertPoint = view.findViewById(R.id.animationView);
        insertPoint2 = view.findViewById(R.id.animationViewPointer);
        int[] colors = {Color.GRAY,color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        def.setAnimation(animation);
        w = MainActivity.width;
        setUpViewPager();
        searchFor = view.findViewById(R.id.search_for);
        searchButton = view.findViewById(R.id.search_button);

        addArray = view.findViewById(R.id.arrInputBox);
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
                            initArray();
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
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchFor.isEnabled()) {
                    if(isRunning){
                        Toast.makeText(getContext(), "Already running", Toast.LENGTH_LONG).show();
                    }
                    else{
                        isRunning = true;
                        explanationText.setText("Searching for" + searchNum);
                        isRunning = false;
                    }
                }
            }
        });
        return view;
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
        String[] strArr = getResources().getStringArray(R.array.linear_search);
        Bundle bundle = new Bundle();
        System.out.println(strArr.toString());
        bundle.putStringArray("string array", strArr);
        stepsFragment.setArguments(bundle);

        TextView textView = view.findViewById(R.id.definitionText);
        textView.setText("Definition: " + strArr[0]);

        Steps2Fragment StepsFragment3 = new Steps2Fragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("step", strArr[3]);
        bundle3.putInt("image", R.drawable.l_search1);
        StepsFragment3.setArguments(bundle3);

        Steps2Fragment StepsFragment4 = new Steps2Fragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("step", strArr[4]);
        bundle4.putInt("image", R.drawable.l_search2);
        StepsFragment4.setArguments(bundle4);

        Steps2Fragment StepsFragment5 = new Steps2Fragment();
        Bundle bundle5 = new Bundle();
        bundle5.putString("step", strArr[5]);
        bundle5.putInt("image", R.drawable.l_search3);
        StepsFragment5.setArguments(bundle5);

        steps.put(0, stepsFragment);
        steps.put(1, StepsFragment3);
        steps.put(2, StepsFragment4);
        steps.put(3, StepsFragment5);

        return steps;
    }

    public void initArray(){
        int len = arr.length;
        int totalLength = (len * (circleSize + leftMargin + rightMargin)) - leftMargin;
        int totalSpace = w - totalLength;
        int startSpace  = (totalSpace / 2);
        tArr = new TextView[len];
        if(insertPoint.getChildCount() > 0){
            insertPoint.removeAllViews();
        }
        for(int i = 0; i<len; i++){
            TextView textView = new CircleText(getContext());
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


}
