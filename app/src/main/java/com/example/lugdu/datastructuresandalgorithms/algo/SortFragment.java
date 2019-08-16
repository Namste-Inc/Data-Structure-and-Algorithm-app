package com.example.lugdu.datastructuresandalgorithms.algo;

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
import com.example.lugdu.datastructuresandalgorithms.algo.Sort.BubbleSortFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.Sort.HeapSortFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.Sort.InsertionSortFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.Sort.MergeSortFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.Sort.QuickSortFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.Sort.SelectionSortFragment;

import java.util.HashMap;

public class SortFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sort, container,false);
        TabLayout tabLayout = view.findViewById(R.id.tabview);
        tabLayout.setBackgroundColor(getArguments().getInt("color"));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getArguments().getInt("color"));
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
