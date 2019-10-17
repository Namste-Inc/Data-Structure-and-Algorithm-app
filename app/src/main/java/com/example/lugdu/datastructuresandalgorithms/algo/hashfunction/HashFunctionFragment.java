package com.example.lugdu.datastructuresandalgorithms.algo.hashfunction;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.example.lugdu.datastructuresandalgorithms.algo.AlgorithmFragment;

import java.util.HashMap;

public class HashFunctionFragment extends Fragment {
    View view;
    LinearLayout insertPoint;
    final int circleSize = 90;
    final int leftMargin = 20;
    final int rightMargin = 0;
    final int topMargin = 20;
    final int bottomMargin = 0;
    EditText stringInput;
    TextView explanationText;
    private ImageView[] dots;
    boolean hasStopped = false;
    ViewPager viewPager;

    Button hashButton;
    boolean running = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hash_function, container,false);
        //System.out.println(hashingStr("mane"));
        int color = getArguments().getInt("color");
        int darkenColor = MainActivity.manipulateColor(color, .8f);
        getActivity().getWindow().setStatusBarColor(darkenColor);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(color);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlgorithmFragment()).commit();
            }
        });
        setUpViewPager();
        TextView def = view.findViewById(R.id.definitionText);
        GradientDrawable gradientDrawable = new GradientDrawable();
        insertPoint = view.findViewById(R.id.animationView);
        hashButton = view.findViewById(R.id.hash_button);
        stringInput = view.findViewById(R.id.string_input);
        explanationText = view.findViewById(R.id.explanationText);
        int[] colors = {Color.GRAY,color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        def.setAnimation(animation);
        TextView textView = view.findViewById(R.id.textviewtoolbar);
        textView.setText("Hash Function");
        hashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(!running){
                            hashingStrAni(stringInput.getText().toString(),11);
                        }
                    }
                });
                thread.start();
            }
        });

        //initBoxes();
        return view;
    }

    public void initBoxes(){

        for(int i = 0; i < 10; i++){
            TextView txV = new TextView(getContext());
            txV.setBackgroundColor(Color.GRAY);
            txV.setText(i + "");
            txV.setHeight(circleSize);
            txV.setWidth(circleSize);
            txV.setGravity(Gravity.CENTER);
            txV.setY((circleSize + 10)*i);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize,circleSize);
            layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
            insertPoint.addView(txV, layoutParams);
        }

    }

//    public void parseArray(String arr){
//        String[] arr1 = arr.split("-");
//        this.arr = new int[arr1.length];
//        if(arr.equals("")){
//            this.arr = null;
//            return;
//        }
//        for(int i = 0; i<arr1.length; i++){
//            this.arr[i] = Integer.parseInt(arr1[i]);
//        }
//    }

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
        String[] strArr = getResources().getStringArray(R.array.hash_function);
        Bundle bundle = new Bundle();
        System.out.println(strArr.toString());
        bundle.putStringArray("string array", strArr);
        stepsFragment.setArguments(bundle);

        TextView textView = view.findViewById(R.id.definitionText);
        textView.setText(strArr[0]);

        Steps2Fragment StepsFragment2 = new Steps2Fragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("step", strArr[3]);
        bundle2.putInt("image", R.drawable.hash_f_collision);
        StepsFragment2.setArguments(bundle2);

        Steps2Fragment StepsFragment3 = new Steps2Fragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("step", strArr[4]);
        bundle3.putInt("image", R.drawable.hash_f_collision1);
        StepsFragment3.setArguments(bundle3);

        Steps2Fragment StepsFragment4 = new Steps2Fragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("step", strArr[5]);
        bundle4.putInt("image", R.drawable.hash_f_collision_fixed);
        StepsFragment4.setArguments(bundle4);

        /*Steps2Fragment StepsFragment5 = new Steps2Fragment();
        Bundle bundle5 = new Bundle();
        bundle5.putString("step", strArr[5]);
        bundle5.putInt("image", R.drawable.b_search4);
        StepsFragment5.setArguments(bundle5);

        Steps2Fragment StepsFragment7 = new Steps2Fragment();
        Bundle bundle7 = new Bundle();
        bundle7.putString("step", strArr[7]);
        bundle7.putInt("image", R.drawable.b_search5);
        StepsFragment7.setArguments(bundle7);
        */

        steps.put(0, stepsFragment);
        steps.put(1, StepsFragment2);
        steps.put(2, StepsFragment3);
        steps.put(3, StepsFragment4);
//        steps.put(4, StepsFragment5);
//        steps.put(5, StepsFragment7);
        return steps;
    }

    public boolean entryGood(String entry) {
        String[] dashSplits = entry.split("-");
        boolean containsTripleDigits = false;
        if (dashSplits.length < 2) {
            Toast.makeText(getContext(), "Invalid Entry", Toast.LENGTH_LONG).show();
            return false;
        }
        for (int i = 0; i < dashSplits.length; i++) {
            if (dashSplits[i].length() > 2) {
                containsTripleDigits = true;
            }
        }

        if (entry.startsWith("-") || entry.endsWith("-") || entry.contains("--")) {
            Toast.makeText(getContext(), "Invalid Entry", Toast.LENGTH_LONG).show();

            return false;
        } else if (dashSplits.length > 7) {
            Toast.makeText(getContext(), "Array size must not be larger than 7.", Toast.LENGTH_LONG).show();

            return false;
        } else if (containsTripleDigits) {
            Toast.makeText(getContext(), "Inputs must not be larger than 99.", Toast.LENGTH_LONG).show();

            return false;
        }
        return true;
    }

    public int hashingStrAni(String str, int arraySize){
        Handler handler = new Handler(Looper.getMainLooper());
        int hash = 0;
        for(int i = 0; i < str.length(); i++) {
            final int constantInt = 3;
            final char charAtI = str.charAt(i);
            final int charIntAtI = str.charAt(i);
            final int subHash = (int)(charIntAtI * Math.pow(constantInt,i));
            hash += subHash;
            final int finalI = i;
            if(i == 0){
                final TextView textView = new TextView(getContext());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        textView.setText(charAtI + "");
                        textView.setPadding(20,0,0,0);
                        //textView.setBackgroundColor(Color.GRAY);
                        //textView.setY((finalI * 100));
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(700,80);
                        layoutParams.setMargins(leftMargin,topMargin,rightMargin,bottomMargin);
                        insertPoint.addView(textView);
                        explanationText.setText("We first get the unicode value of '" + charAtI + "'" );
                    }
                });
                pause(Thread.currentThread(), 3000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.append(" = " + charIntAtI);
                    }
                });
                pause(Thread.currentThread(),1000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        explanationText.setText("multiply a constant to the power of the characters current position in the string");
                    }
                });
                pause(Thread.currentThread(), 2000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.append(" x " + constantInt + "^" + finalI );
                    }
                });
                pause(Thread.currentThread(), 1000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        explanationText.setText("The hash value for character '"+ charAtI + "' is " + subHash);
                    }
                });
                pause(Thread.currentThread(), 2000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.append(" = " + subHash);
                    }
                });
                if(str.length() > 1){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            explanationText.setText("Do this for the rest of the characters");
                        }
                    });
                }
            }
            else{
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(getActivity() != null){
                            TextView textView = new TextView(getContext());
                            textView.setText(charAtI + " = " + charIntAtI + " x " + constantInt + "^" + finalI + " = " + subHash);
                            textView.setPadding(20,0,0,0);
                            insertPoint.addView(textView);
                        }
                    }
                });}
                pause(Thread.currentThread(), 1000);
        }
        final int finalHash = hash;
        handler.post(new Runnable() {
            @Override
            public void run() {
                explanationText.setText("The summation of these values gives us the hash for the string: " + finalHash);
                if(getActivity() != null){
                    TextView textView = new TextView(getContext());
                    textView.setPadding(20,30,0,0);
                    textView.setText("hash: " + finalHash);
                    insertPoint.addView(textView);
                }
            }
        });

        return hash % arraySize;
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
}