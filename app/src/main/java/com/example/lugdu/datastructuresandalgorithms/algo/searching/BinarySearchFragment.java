package com.example.lugdu.datastructuresandalgorithms.algo.searching;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    TextView tArr[];
    TextView explanationText;
    RelativeLayout insertPoint;
    RelativeLayout insertPoint2;
    final int circleSize = 150;
    final int leftMargin = 20;
    final int rightMargin = 0;
    final int topMargin = 0;
    final int bottomMargin = 0;
    int startNum;
    int endNum;
    int w;
    Button pointerButton;
    SwitchCompat switchStatus;
    private ImageView[] dots;
    boolean hasStopped = false;
    ViewPager viewPager;
    boolean isRunning;
    public static int color = Color.parseColor("#1964B4");
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
        setUpViewPager();
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

}
