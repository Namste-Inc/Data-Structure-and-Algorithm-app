package com.example.lugdu.datastructuresandalgorithms.algo.iteration;

import android.animation.ObjectAnimator;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lugdu.datastructuresandalgorithms.CircleText;
import com.example.lugdu.datastructuresandalgorithms.MainActivity;
import com.example.lugdu.datastructuresandalgorithms.PagerAdapter;
import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.Steps2Fragment;
import com.example.lugdu.datastructuresandalgorithms.StepsFragment;

import org.w3c.dom.Text;

import java.util.HashMap;

public class DoWhileLoopFragment extends Fragment {
    View view;
    private ImageView[] dots;
    boolean hasStopped = false;
    ViewPager viewPager;
    boolean isRunning;
    Thread animationThread;
    int circleSize = 150;
    int leftMargin = 0;
    int topMargin = 0;
    int bottomMargin = 0;
    int rightMargin = 0;
    boolean condition1 = true;
    boolean condition2 = true;
    int w;
    SwitchCompat switchStatus1;
    SwitchCompat switchStatus2;

    RelativeLayout insertionPoint;
    CircleText[] tArr = new CircleText[2];
    public static int color = Color.parseColor("#19B41C");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_do_while_loop, container,false);

        TextView def = view.findViewById(R.id.definitionText);
        GradientDrawable gradientDrawable = new GradientDrawable();
        int[] colors = {Color.GRAY,color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        def.setAnimation(animation);
        w = MainActivity.width;
        insertionPoint = view.findViewById(R.id.animationView);
        initCircles();
        switchStatus1 = view.findViewById(R.id.swStatusCustom1);
        switchStatus2 = view.findViewById(R.id.swStatusCustom2);
        TextView explaination = view.findViewById(R.id.explanationText);

        switchStatus1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && switchStatus2.isChecked()){
                    animateCircles();
                }
            }
        });
        switchStatus2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && switchStatus1.isChecked()){
                    animateCircles();
                }
            }
        });
        setUpViewPager();
        explaination.setText("do{\n     circleAnimation(); \n}while(condition1 && condition2);");
        animateCircles();
        return view;
    }

    public void animateCircles(){
        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                do{
                    if(getActivity()!= null && !hasStopped){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ObjectAnimator animator = ObjectAnimator.ofFloat(tArr[0],"translationX", tArr[1].getX());
                                animator.setDuration(1000);

                                ObjectAnimator animator1 = ObjectAnimator.ofFloat(tArr[1],"translationX", tArr[0].getX());
                                animator1.setDuration(1000);

                                animator.start();
                                animator1.start();
                                System.out.println("runing");
                                //animate
                            }
                        });
                    }
                    pause(Thread.currentThread(), 1000);

                    if(getActivity()!= null && !hasStopped){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ObjectAnimator animator = ObjectAnimator.ofFloat(tArr[0],"translationX", tArr[1].getX());
                                animator.setDuration(1000);

                                ObjectAnimator animator1 = ObjectAnimator.ofFloat(tArr[1],"translationX", tArr[0].getX());
                                animator1.setDuration(1000);

                                animator.start();
                                animator1.start();
                                System.out.println("runing");
                                //animate
                            }
                        });
                    }

                    pause(Thread.currentThread(), 1000);
                }while (switchStatus1.isChecked() && switchStatus2.isChecked());
            }
        });
        animationThread.start();
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

    public void initCircles(){
        tArr[0] = new CircleText(getContext());
        tArr[1] = new CircleText(getContext());
        tArr[1].select(true);
        tArr[0].setX(20);
        tArr[1].setX(w - (circleSize + 20));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize,circleSize);
        layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(circleSize,circleSize);
        layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);

        insertionPoint.addView(tArr[0], layoutParams);
        insertionPoint.addView(tArr[1], layoutParams1);
    }

    public HashMap<Integer, Fragment> getViewFragments(){
        HashMap<Integer, Fragment> steps = new HashMap<>();
        StepsFragment stepsFragment = new StepsFragment();
        String[] strArr = getResources().getStringArray(R.array.do_while_loop);
        Bundle bundle = new Bundle();
        System.out.println(strArr.toString());
        bundle.putStringArray("string array", strArr);
        stepsFragment.setArguments(bundle);

        TextView textView = view.findViewById(R.id.definitionText);
        textView.setText("Definition: " + strArr[0]);

        Steps2Fragment steps2Fragment1 = new Steps2Fragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("step", strArr[1]);
        bundle1.putInt("image", R.drawable.do_while1);
        steps2Fragment1.setArguments(bundle1);

        Steps2Fragment steps2Fragment2 = new Steps2Fragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("step", strArr[2]);
        bundle2.putInt("image", R.drawable.do_while2);
        steps2Fragment2.setArguments(bundle2);

        Steps2Fragment steps2Fragment3 = new Steps2Fragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("step", strArr[3]);
        bundle3.putInt("image", R.drawable.do_while3);
        steps2Fragment3.setArguments(bundle3);

        Steps2Fragment steps2Fragment4 = new Steps2Fragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("step", strArr[4]);
        bundle4.putInt("image", R.drawable.do_while4);
        steps2Fragment4.setArguments(bundle4);

        steps.put(0, stepsFragment);
        steps.put(1, steps2Fragment1);
        steps.put(2, steps2Fragment2);
        steps.put(3, steps2Fragment3);
        steps.put(4, steps2Fragment4);

        return steps;
    }
}
