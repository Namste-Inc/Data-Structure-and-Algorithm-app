package com.example.lugdu.datastructuresandalgorithms.algo.treetraversal;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class PostOrderFragment extends Fragment {
    View view;
    int[] arr;
    TextView tArr[];
    int[] orderedArray;
    RelativeLayout explanationText;
    TextView resultText;

    int circleSize = 120;
    int leftMargin = 20;
    int rightMargin = 0;
    int topMargin = 25;
    int bottomMargin = 0;
    boolean isRunning;
    boolean hasStopped = false;
    int w;
    private ImageView[] dots;
    public static int color = Color.parseColor("#D67521");

    ViewPager viewPager;

    int orderedArrayCounter = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tree_traversal_ani, container, false);
        final Button button1 = view.findViewById(R.id.button1);
        final EditText editText = view.findViewById(R.id.inputText);
        resultText = view.findViewById(R.id.resultText);

        TextView def = view.findViewById(R.id.definitionText);
        GradientDrawable gradientDrawable = new GradientDrawable();
        int[] colors = {Color.GRAY, color};
        gradientDrawable.setColors(colors);
        def.setBackground(gradientDrawable);
        explanationText = view.findViewById(R.id.searchAnimation);
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
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (entryGood(editText.getText().toString())) {
                        if (!editText.getText().toString().equals("")) {
                            parseArray(editText.getText().toString());
                            initArray();
                        } else {
                            editText.setText("");
                        }

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        editText.clearFocus();
                        editText.setSelection(editText.getText().length());
                        return true;
                    } else {
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
                        if (arr == null || arr.length == 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Must enter an array", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else if (arr.length > 7) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Array must be size 7(max)", Toast.LENGTH_LONG).show();
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
                                        //initArray();
                                        editText.setEnabled(false);
                                    }
                                });

                                BinaryTree bt = new BinaryTree();
                                orderedArrayCounter = 0;
                                System.out.println("printing nodes of a btree on in order using recursion");
                                bt.create().postOrder();
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            resultText.setText("Ordered tree: " + arrayToString(orderedArray, true));
                                        }
                                    });
                                }

                                for (int i = 0; i < arr.length; i ++) {
                                    final int finalI = i;
                                    if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((CircleText) tArr[finalI]).sorted();
                                            }
                                        });
                                    }
                                }

                                isRunning = false;
                                hasStopped = false;
                                checkStatus();

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

    public void parseArray(String arr) {
        String[] arr1 = arr.split("-");
        this.arr = new int[arr1.length];
        if (arr.equals("")) {
            this.arr = null;
            return;
        }
        for (int i = 0; i < arr1.length; i++) {
            this.arr[i] = Integer.parseInt(arr1[i]);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void initArray() {
        int testSpace = -110;

        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - 1075;

        int len = arr.length;


        tArr = new TextView[len];
        RelativeLayout insertPoint = view.findViewById(R.id.searchAnimation);
        if (insertPoint.getChildCount() > 0) {
            insertPoint.removeAllViews();
        }
        for (int i = 0; i < len; i++) {
            //if input reaches more than 7 numbers
            //do not initialize the rest
            if (i == 7) {
                break;
            }
            TextView textView = new CircleText(getContext());
            textView.setText(arr[i] + "");

            switch (i) {
                case 0:
                    textView.setX(width / 2);
                    textView.setY((i * 100) + testSpace);
                    break;

                case 1:
                    textView.setX((width / 2) - 150);
                    textView.setY((i * 150) + testSpace);
                    break;

                case 2:
                    textView.setX((width / 2) + 150);
                    textView.setY(((i - 1) * 150) + testSpace);
                    break;

                case 3:
                    textView.setX((width / 2) - 225);
                    textView.setY(((i - 1) * 150) + testSpace);
                    break;

                case 4:
                    textView.setX((width / 2) - 75);
                    textView.setY(((i - 2) * 150) + testSpace);
                    break;

                case 5:
                    textView.setX((width / 2) + 75);
                    textView.setY(((i - 3) * 150) + testSpace);
                    break;

                case 6:
                    textView.setX((width / 2) + 225);
                    textView.setY(((i - 4) * 150) + testSpace);
                    break;
            }
            tArr[i] = textView;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize, circleSize);
            layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            insertPoint.addView(textView, layoutParams);
        }
        orderedArray = new int[arr.length];
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

    public String arrayToString(int[] arr, boolean comma) {
        String separator = comma ? "," : "-";
        int len = 0;
        if (arr != null) {
            len = arr.length;
        }
        String toString = "";
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                toString += arr[0];
            } else {
                toString += separator + arr[i];
            }
        }
        return toString;
    }

    class BinaryTree {

        class TreeNode {
            int data;
            TreeNode left, right;

            TreeNode(int value) {
                this.data = value;
                left = right = null;
            }
        }

        TreeNode root;

        /**
         * Java method to print tree nodes in PreOrder traversal
         */
        public void postOrder() {
            postOrder(root);
        }

        private void postOrder(TreeNode node) {
            if (node == null) {
                return;
            }

            postOrder(node.left);
            postOrder(node.right);

            System.out.printf("%s ", node.data);
            int index = 0;


            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == node.data) {
                    index = i;
                    orderedArray[orderedArrayCounter] = arr[index];
                    orderedArrayCounter++;
                    break;
                }
            }

            if (getActivity() != null) {
                final int finalIndex = index;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((CircleText) tArr[finalIndex]).select(true);
                    }
                });
            }
            pause(Thread.currentThread(),1000);
        }

        /**
         * Java method to create binary tree with test data * * @return a sample binary tree for testing
         */
        public BinaryTree create() {
            BinaryTree tree = new BinaryTree();
            TreeNode root;

            for (int i = 0; i < arr.length; i++) {
                //if input reaches more than 7 numbers
                //do not initialize the rest
                if (i == 7) {
                    break;
                }

                //Set the floors as ints and do "floor*100" instead of "i*100"
                switch (i) {
                    case 0:
                        root = new TreeNode(arr[i]);
                        tree.root = root;
                        break;

                    case 1:
                        tree.root.left = new TreeNode(arr[i]);
                        break;

                    case 2:
                        tree.root.right = new TreeNode(arr[i]);
                        break;

                    case 3:
                        tree.root.left.left = new TreeNode(arr[i]);
                        break;

                    case 4:
                        tree.root.left.right = new TreeNode(arr[i]);
                        break;

                    case 5:
                        tree.root.right.left = new TreeNode(arr[i]);
                        break;

                    case 6:
                        tree.root.right.right = new TreeNode(arr[i]);
                        break;
                }
            }

            return tree;
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
        String[] strArr = getResources().getStringArray(R.array.postOrder);
        Bundle bundle = new Bundle();
        bundle.putStringArray("string array", strArr);
        stepsFragment.setArguments(bundle);

        Steps2Fragment steps2Fragment1 = new Steps2Fragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("step", strArr[1]);
        bundle1.putInt("image", R.drawable.tree_creation_pic);
        steps2Fragment1.setArguments(bundle1);

        Steps2Fragment steps2Fragment2 = new Steps2Fragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("step", strArr[2]);
        bundle2.putInt("image", R.drawable.visiting_postorder);
        steps2Fragment2.setArguments(bundle2);

        TextView textView = view.findViewById(R.id.definitionText);
        textView.setText("Definition: " + strArr[0]);


        steps.put(0, stepsFragment);
        steps.put(1, steps2Fragment1);
        steps.put(2, steps2Fragment2);

        return steps;
    }
}