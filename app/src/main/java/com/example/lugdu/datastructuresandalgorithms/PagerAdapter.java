package com.example.lugdu.datastructuresandalgorithms;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int nTabs;
    HashMap<Integer, Fragment> fragmentHashMap;
    public PagerAdapter(FragmentManager fm, int tabs, HashMap<Integer, Fragment> map){
        super(fm);
        nTabs = tabs;
        fragmentHashMap = map;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentHashMap.get(position);
    }

    @Override
    public int getCount() {
        return nTabs;
    }

}
