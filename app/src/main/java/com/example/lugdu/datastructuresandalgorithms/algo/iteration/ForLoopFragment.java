package com.example.lugdu.datastructuresandalgorithms.algo.iteration;

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
import android.view.Gravity;
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

public class ForLoopFragment extends Fragment {
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
    private ImageView[] dots;
    boolean hasStopped = false;
    ViewPager viewPager;
    boolean isRunning;
    public static int color = Color.parseColor("#19B41C");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_for_loop, container,false);
        TextView def = view.findViewById(R.id.definitionText);
        GradientDrawable gradientDrawable = new GradientDrawable();
        int[] colors = {Color.GRAY,color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        def.setAnimation(animation);
        w = MainActivity.width;
        final EditText editText = view.findViewById(R.id.arrInputBox);
        final EditText startPosText = view.findViewById(R.id.starterInputBox);
        final EditText endPosText = view.findViewById(R.id.maxIterationBox);

        insertPoint = view.findViewById(R.id.animationView);
        insertPoint2 = view.findViewById(R.id.animationViewPointer);

        setUpViewPager();
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
                            startPosText.setEnabled(true);
                            Button button = initPointer(tArr[0].getX());
                        }
                        else{

                            editText.setText("");
                        }
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        editText.setSelection(editText.getText().length());
                        editText.clearFocus();
                        return true;
                    }
                    else{
                        return true;
                    }
                }
                return false;
            }
        });

        startPosText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosText.setSelection(startPosText.getText().length());
            }
        });
        startPosText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    endPosText.setEnabled(true);
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(startPosText.getWindowToken(), 0);
                    startPosText.setSelection(startPosText.getText().length());
                    startPosText.clearFocus();
                }
                return false;
            }
        });

        endPosText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endPosText.setSelection(endPosText.getText().length());
            }
        });
        endPosText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(endPosText.getWindowToken(), 0);
                    endPosText.setSelection(endPosText.getText().length());
                    endPosText.clearFocus();
                }
                return false;
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
        String[] strArr = getResources().getStringArray(R.array.for_loop);
        Bundle bundle = new Bundle();
        System.out.println(strArr.toString());
        bundle.putStringArray("string array", strArr);
        stepsFragment.setArguments(bundle);

        TextView textView = view.findViewById(R.id.definitionText);
        textView.setText("Definition: " + strArr[0]);

        Steps2Fragment steps2Fragment1 = new Steps2Fragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("step", strArr[1]);
        bundle1.putInt("image", R.drawable.for1);
        steps2Fragment1.setArguments(bundle1);

        Steps2Fragment steps2Fragment2 = new Steps2Fragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("step", strArr[2]);
        bundle2.putInt("image", R.drawable.for2);
        steps2Fragment2.setArguments(bundle2);

        Steps2Fragment steps2Fragment3 = new Steps2Fragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("step", strArr[3]);
        bundle3.putInt("image", R.drawable.for3);
        steps2Fragment3.setArguments(bundle3);

        Steps2Fragment steps2Fragment4 = new Steps2Fragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("step", strArr[4]);
        bundle4.putInt("image", R.drawable.for4);
        steps2Fragment4.setArguments(bundle4);

        Steps2Fragment steps2Fragment5 = new Steps2Fragment();
        Bundle bundle5 = new Bundle();
        bundle5.putString("step", strArr[5]);
        bundle5.putInt("image", R.drawable.for5);
        steps2Fragment5.setArguments(bundle5);

        steps.put(0, stepsFragment);
        steps.put(1, steps2Fragment1);
        steps.put(2, steps2Fragment2);
        steps.put(3, steps2Fragment3);
        steps.put(4, steps2Fragment4);
        steps.put(5, steps2Fragment5);

        return steps;
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

    public Button initPointer(float start){
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
        button.setText("arr[i]");
        button.setX(start);
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

}
