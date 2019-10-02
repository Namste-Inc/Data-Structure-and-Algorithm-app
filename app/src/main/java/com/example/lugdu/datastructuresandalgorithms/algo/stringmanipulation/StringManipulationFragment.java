package com.example.lugdu.datastructuresandalgorithms.algo.stringmanipulation;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lugdu.datastructuresandalgorithms.MainActivity;
import com.example.lugdu.datastructuresandalgorithms.PagerAdapter;
import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.algo.AlgorithmFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.Sort.BubbleSortFragment;

import org.w3c.dom.Text;

import java.util.HashMap;

public class StringManipulationFragment extends Fragment {
    View view;
    int w;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.middle_fragment, container,false);
        int color = getArguments().getInt("color");
        int darkenColor = MainActivity.manipulateColor(color, .8f);
        getActivity().getWindow().setStatusBarColor(darkenColor);
        TabLayout tabLayout = view.findViewById(R.id.tabview);
        tabLayout.setBackgroundColor(color);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(color);

        Bundle bundle = new Bundle();
        bundle.putInt("Color", color);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlgorithmFragment()).commit();
            }
        });
        toolbar.setBackgroundColor(color);
        TextView textView = view.findViewById(R.id.textviewtoolbar);
        textView.setText("String Manipulation");

        final ViewPager viewPager = view.findViewById(R.id.pager);
        tabLayout.addTab(tabLayout.newTab().setText("Get Length"));
        tabLayout.addTab(tabLayout.newTab().setText("Get Index"));
        tabLayout.addTab(tabLayout.newTab().setText("Contains"));
        tabLayout.addTab(tabLayout.newTab().setText("Replace"));
        tabLayout.addTab(tabLayout.newTab().setText("Casing"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        HashMap<Integer, Fragment> stringmanipulation = new HashMap<>();
        stringmanipulation.put(0,new GetLengthFragment());
        stringmanipulation.put(1,new GetLengthFragment());
        stringmanipulation.put(2,new GetLengthFragment());
        stringmanipulation.put(3,new GetLengthFragment());
        stringmanipulation.put(4,new GetLengthFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(),tabLayout.getTabCount(),stringmanipulation);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}
