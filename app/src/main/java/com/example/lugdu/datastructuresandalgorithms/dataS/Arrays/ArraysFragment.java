package com.example.lugdu.datastructuresandalgorithms.dataS.Arrays;


import android.graphics.Color;
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
import android.widget.TextView;

import com.example.lugdu.datastructuresandalgorithms.MainActivity;
import com.example.lugdu.datastructuresandalgorithms.PagerAdapter;
import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.algo.AlgorithmFragment;
import com.example.lugdu.datastructuresandalgorithms.dataS.DataStructureFragment;

import java.util.HashMap;

public class ArraysFragment extends Fragment {
    View view;
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
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(color);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataStructureFragment()).commit();
            }
        });
        TextView textView = view.findViewById(R.id.textviewtoolbar);
        textView.setText("Arrays");
        final ViewPager viewPager = view.findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText("One Dimensional"));
        tabLayout.addTab(tabLayout.newTab().setText("Two Dimensional"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        HashMap<Integer, Fragment> arrays = new HashMap<>();
        arrays.put(0,new OneDimensionalFragment());
        arrays.put(1,new TwoDimensionalFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(),tabLayout.getTabCount(),arrays);
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
