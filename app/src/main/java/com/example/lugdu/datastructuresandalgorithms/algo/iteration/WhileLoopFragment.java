package com.example.lugdu.datastructuresandalgorithms.algo.iteration;

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
import android.widget.CompoundButton;
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

public class WhileLoopFragment extends Fragment {
    View view;
    TextView textView;
    int arr[];
    TextView tArr[];
    TextView explanationText;
    RelativeLayout insertPoint;
    final int circleSize = 150;
    final int leftMargin = 0;
    final int rightMargin = 0;
    final int topMargin = 0;
    final int bottomMargin = 0;

    boolean isTrue = false;
    boolean hasStopped = false;
    int w;
    Thread animationThread;
    SwitchCompat switchStatus;
    private ImageView[] dots;
    ViewPager viewPager;
    public static int color = Color.parseColor("#19B41C");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_while_loop, container, false);
        TextView def = view.findViewById(R.id.definitionText);
        GradientDrawable gradientDrawable = new GradientDrawable();
        int[] colors = {Color.GRAY, color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        final Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        def.setAnimation(animation);
        w = MainActivity.width;
        explanationText = view.findViewById(R.id.explanationText);
        switchStatus = view.findViewById(R.id.switchButton);


        setUpViewPager();

        insertPoint = view.findViewById((R.id.animationView));

        initView();



        switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hasStopped = false;
                    checkStatus();
                    animate();
                }
            }
        });

        explanationText.setText("while(condition){\n    runAnimation();\n}");
        return view;
    }

    public void animate() {
        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (switchStatus.isChecked()) {
                    if (getActivity() != null && !hasStopped) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                float x1 = (w - (circleSize + 20));
                                ObjectAnimator animation1 = ObjectAnimator.ofFloat(textView, "translationX", x1);
                                animation1.setDuration(1000);
                                System.out.println(x1);

                                animation1.start();
                            }
                        });
                    }
                    pause(Thread.currentThread(),1000);
                    if (getActivity() != null && !hasStopped) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                float x2 = 20;
                                ObjectAnimator animation2 = ObjectAnimator.ofFloat(textView, "translationX", x2);
                                animation2.setDuration(1000);
                                System.out.println(x2);
                                animation2.start();
                            }
                        });
                    }
                    pause(Thread.currentThread(),1000);
                }
            }
        });
        animationThread.start();
    }

    public void checkStatus() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (getActivity() != null) {

                }
                hasStopped = true;
            }
        });
        thread.start();
    }

    public void pause(Thread thread, int time) {
        synchronized (thread) {
            try {
                Thread.currentThread().wait(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUpViewPager() {
        LinearLayout dotsView = view.findViewById(R.id.sliderDots);
        HashMap<Integer, Fragment> steps = getViewFragments();
        viewPager = view.findViewById(R.id.viewpager);
        dots = new ImageView[steps.size()];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.inactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            dotsView.addView(dots[i], params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), steps.size(), steps);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int j = 0; j < dots.length; j++) {
                    dots[j].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.inactive_dot));
                }
                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public HashMap<Integer, Fragment> getViewFragments() {
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

    public void initView() {
        textView = new CircleText(getContext());
        textView.setX(20);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize, circleSize);
        layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        insertPoint.addView(textView, layoutParams);
    }
}
