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
import android.text.Editable;
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
import com.example.lugdu.datastructuresandalgorithms.StepsFragment;

import java.util.HashMap;

public class ReplaceFragment extends Fragment {
    View view;
    String arr[];

    RelativeLayout explanationText;
    int w;
    int lengthCount = 0;

    boolean hasStopped = false;
    boolean isRunning = false;
    boolean inputIsValid = true;

    public static int color = Color.parseColor("#FFEB3B");

    ViewPager viewPager;
    private ImageView[] dots;

    EditText editText;
    EditText secondInputBox;
    EditText thirdInputBox;

    TextView theText;
    String replaceString;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_replace, container, false);


        final Button button1 = view.findViewById(R.id.button1);
        /*final EditText*/ editText = view.findViewById(R.id.inputBox);
        /*final EditText*/ secondInputBox = view.findViewById(R.id.replacementBox);
        /*final EditText*/ thirdInputBox = view.findViewById(R.id.replacerBox);

        TextView def = view.findViewById(R.id.definitionText);
        theText = view.findViewById(R.id.resultText);
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
                            theText.setText(editText.getText());
                            //init array text
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

        secondInputBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondInputBox.setSelection(secondInputBox.getText().length());

            }
        });
        secondInputBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (inputIsValid/*entryGood(editText.getText().toString())*/) {
                        if (!secondInputBox.getText().toString().equals("")) {

                        } else {
                            secondInputBox.setText("");
                        }

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(secondInputBox.getWindowToken(), 0);
                        secondInputBox.setSelection(secondInputBox.getText().length());
                        return true;
                    } else {
//                        Toast.makeText(getContext(),"Invalid Entry", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
                return false;
            }
        });

        thirdInputBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thirdInputBox.setSelection(thirdInputBox.getText().length());
            }
        });
        thirdInputBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (inputIsValid/*entryGood(editText.getText().toString())*/) {
                        if (!thirdInputBox.getText().toString().equals("")) {

                        } else {
                            thirdInputBox.setText("");
                        }

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(thirdInputBox.getWindowToken(), 0);
                        thirdInputBox.setSelection(thirdInputBox.getText().length());
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
                                        replace();

                                        editText.setEnabled(false);
                                    }
                                });
                                isRunning = true;
                                hasStopped = false;
                                checkStatus();

                                //do action
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        theText.setText(replaceString);
                                    }
                                });
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

    public void replace() {
        String originalText = editText.getText().toString();
        String target = secondInputBox.getText().toString();
        Editable replacement = thirdInputBox.getText();
        replaceString = originalText.replace(target, replacement);

        System.out.println(replaceString);
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
        String[] strArr = getResources().getStringArray(R.array.string_replace);
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
