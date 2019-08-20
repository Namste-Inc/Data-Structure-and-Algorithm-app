package com.example.lugdu.datastructuresandalgorithms.dataS.Queues;

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
import com.example.lugdu.datastructuresandalgorithms.dataS.DataStructureFragment;

import java.util.HashMap;

public class QueuesFragment extends Fragment {
    View view;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.middle_fragment, container,false);
        int color = getArguments().getInt("color");
        int darkenColor = MainActivity.manipulateColor(color, .8f);
        getActivity().getWindow().setStatusBarColor(darkenColor);
        getActivity().getWindow().setStatusBarColor(color);
        TabLayout tabLayout = view.findViewById(R.id.tabview);
        tabLayout.setBackgroundColor(color);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getArguments().getInt("color"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataStructureFragment()).commit();
            }
        });
        TextView textView = view.findViewById(R.id.textviewtoolbar);
        textView.setText("Queues");
        final ViewPager viewPager = view.findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setText("Simple Queue"));
        tabLayout.addTab(tabLayout.newTab().setText("Circular Queue"));
        tabLayout.addTab(tabLayout.newTab().setText("Priority Queue"));
        tabLayout.addTab(tabLayout.newTab().setText("Doubly Ended Queue"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        HashMap<Integer, Fragment> queues = new HashMap<>();
        queues.put(0,new SimpleQueueFragment());
        queues.put(1,new CircularQueueFragment());
        queues.put(2,new PriorityQueueFragment());
        queues.put(3,new DoublyEndedQueueFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(),tabLayout.getTabCount(),queues);
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

