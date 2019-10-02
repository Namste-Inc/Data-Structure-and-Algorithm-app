package com.example.lugdu.datastructuresandalgorithms.algo.stringmanipulation;

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
import com.example.lugdu.datastructuresandalgorithms.Steps2Fragment;
import com.example.lugdu.datastructuresandalgorithms.StepsFragment;

import org.w3c.dom.Text;

import java.util.HashMap;

public class GetLengthFragment extends Fragment {
    View view;
    String arr[];
    TextView tArr[];
    RelativeLayout explanationText;
    int w;
    int circleSize = 80;
    int leftMargin = 0;
    int rightMargin = 0;
    int topMargin = 25;
    int bottomMargin = 0;
    int lengthCount = 0;

    boolean hasStopped = false;
    boolean isRunning = false;
    boolean inputIsValid = true;

    public static int color = Color.parseColor("#FFEB3B");

    ViewPager viewPager;
    private ImageView[] dots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_string_manipulation, container, false);

        final Button button1 = view.findViewById(R.id.button1);
        final EditText editText = view.findViewById(R.id.inputBox);
        TextView def = view.findViewById(R.id.definitionText);
        GradientDrawable gradientDrawable = new GradientDrawable();
        int[] colors = {Color.GRAY, color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        explanationText = view.findViewById(R.id.explanationText);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        def.setAnimation(animation);
        w = MainActivity.width;

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
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (inputIsValid/*entryGood(editText.getText().toString())*/) {
                        if (!editText.getText().toString().equals("")) {
                            parseArray(editText.getText().toString());
                            //init array text
                            initArray();
                        } else {

                            editText.setText("");
                        }

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        editText.setSelection(editText.getText().length());
                        return true;
                    } else {
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
                        if (!inputIsValid) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Must enter an array", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            if (isRunning) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "Animation running, please wait", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //init arrayText
                                        initArray();
                                        editText.setEnabled(false);
                                    }
                                });
                                isRunning = true;
                                hasStopped = false;
                                checkStatus();

                                //do action
                                getLength();
                                System.out.println("length count: " + lengthCount);
                                isRunning = false;

                                if (getActivity() != null) {
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

    public void getLength() {

        for (int i = 1; i < arr.length; i++) {
            final int finalI = i;

            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((CircleText) tArr[finalI]).select(true);
                    }
                });
            }
            lengthCount++;
            if (arr.length > 7) {
                pause(Thread.currentThread(),300);
            } else {
                pause(Thread.currentThread(), 1000);
            }
        }

        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = view.findViewById(R.id.outputNumber);
                    textView.setText("String length: " + lengthCount);
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void initArray() {
        int len = arr.length;
        int nextLineLen = len;
        if (len > 13) {
            nextLineLen = 13;
        }
        int totalLength = (nextLineLen * (circleSize + leftMargin + rightMargin)) - leftMargin;
        int totalSpace = w - totalLength;
        int startSpace = (totalSpace / 2);
        float yPosition = 0;
        tArr = new TextView[len];
        RelativeLayout insertPoint = view.findViewById(R.id.resultText);
        if (insertPoint.getChildCount() > 0) {
            insertPoint.removeAllViews();
        }
        int j = 1;
        for (int i = 1; i < len; i++) {
            if (((i - 1) % 12 == 0) && i - 1 != 0) {
                yPosition += 100;
                j = 1;
            }
            TextView textView = new CircleText(getContext());
            textView.setText(arr[i] + "");
            textView.setX(((j - 1) * 80) + startSpace - 0);
            textView.setY(yPosition);
            tArr[i] = textView;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize, circleSize);
            layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            insertPoint.addView(textView, layoutParams);
            j++;
        }
    }

    public void parseArray(String arr) {
        String[] arr1 = arr.split("");
        this.arr = new String[arr1.length];
        if (arr.equals("")) {
            this.arr = null;
            return;
        }
        for (int i = 0; i < arr1.length; i++) {
            this.arr[i] = arr1[i];
        }
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

    public HashMap<Integer, Fragment> getViewFragments(){
        HashMap<Integer, Fragment> steps = new HashMap<>();
        StepsFragment stepsFragment = new StepsFragment();
        String[] strArr = getResources().getStringArray(R.array.get_length);
        Bundle bundle = new Bundle();
        bundle.putStringArray("string array", strArr);
        stepsFragment.setArguments(bundle);

        TextView textView = view.findViewById(R.id.definitionText);
        textView.setText("Definition: " + strArr[0]);


        steps.put(0, stepsFragment);

        return steps;
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
}