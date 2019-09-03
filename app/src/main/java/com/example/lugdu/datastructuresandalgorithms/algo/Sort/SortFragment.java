package com.example.lugdu.datastructuresandalgorithms.algo.Sort;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lugdu.datastructuresandalgorithms.MainActivity;
import com.example.lugdu.datastructuresandalgorithms.PagerAdapter;
import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.algo.AlgorithmFragment;

import java.util.HashMap;

public class SortFragment extends Fragment {
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
        textView.setText("Sort");

        final ViewPager viewPager = view.findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText("Bubble Sort"));
        tabLayout.addTab(tabLayout.newTab().setText("Heap Sort"));
        tabLayout.addTab(tabLayout.newTab().setText("Insertion Sort"));
        tabLayout.addTab(tabLayout.newTab().setText("Merge Sort"));
        tabLayout.addTab(tabLayout.newTab().setText("Quick Sort"));
        tabLayout.addTab(tabLayout.newTab().setText("Selection Sort"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        HashMap<Integer, Fragment> sort = new HashMap<>();
        sort.put(0,new BubbleSortFragment());
        sort.put(1,new HeapSortFragment());
        sort.put(2,new InsertionSortFragment());
        sort.put(3,new MergeSortFragment());
        sort.put(4,new QuickSortFragment());
        sort.put(5,new SelectionSortFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(),tabLayout.getTabCount(),sort);
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
