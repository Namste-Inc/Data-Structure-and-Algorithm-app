package com.example.lugdu.datastructuresandalgorithms.algo.searching;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lugdu.datastructuresandalgorithms.PagerAdapter;
import com.example.lugdu.datastructuresandalgorithms.R;

import java.util.HashMap;

public class SearchFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.middle_fragment, container,false);
        TabLayout tabLayout = view.findViewById(R.id.tabview);
        tabLayout.setBackgroundColor(getArguments().getInt("color"));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getArguments().getInt("color"));
        TextView textView = view.findViewById(R.id.textviewtoolbar);
        textView.setText("Search");
        final ViewPager viewPager = view.findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText("Linear Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Binary Search"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        HashMap<Integer, Fragment> search = new HashMap<>();
        search.put(0,new LinearSearchFragment());
        search.put(1,new BinarySearchFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(),tabLayout.getTabCount(),search);
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
