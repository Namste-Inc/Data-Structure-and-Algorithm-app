package com.example.lugdu.datastructuresandalgorithms.algo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.lugdu.datastructuresandalgorithms.FragmentDialog;
import com.example.lugdu.datastructuresandalgorithms.Hexagon;
import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.algo.Sort.SortFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.hashfunction.HashFunctionFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.iteration.IterationFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.searching.SearchFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.stringmanipulation.StringManipulationFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.treetraversal.TreeTraversalFragment;

import java.util.ArrayList;

public class AlgorithmFragment extends Fragment {
    View view;
    Boolean relesed;
    View touched = null;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_algorithm, container,false);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        ArrayList<View> hexA = new ArrayList<>();
        Hexagon hexSort = view.findViewById(R.id.sort_hex_b);
        hexSort.setDef("An algorithm that puts elements of a list in a certain order");
        Hexagon hexTreeT = view.findViewById(R.id.tree_trav_hex_b);
        hexTreeT.setDef("A form of graph traversal referring to the process of visiting each node in a tree data structure exactly once");
        Hexagon hexStringM = view.findViewById(R.id.string_m_hex_b);
        hexStringM.setDef("A class of problems where a user is asked to process a given string and use/change its data");
        Hexagon hexHashF = view.findViewById(R.id.hash_func_hex_b);
        hexHashF.setDef("Any function that can be used to map data of arbitrary size onto data of a fixed size");
        Hexagon hexIter = view.findViewById(R.id.itr_hex_b);
        hexIter.setDef("Defining a process and repeating it for a set amount of times.");
        Hexagon hexSearching = view.findViewById(R.id.searching_hex_b);
        hexSearching.setDef("Designed to check for an element or retrieve an element from any data structure where it is stored");
        hexA.add(hexSort);
        hexA.add(hexTreeT);
        hexA.add(hexStringM);
        hexA.add(hexHashF);
        hexA.add(hexIter);
        hexA.add(hexSearching);
        setHexListener(hexA);
        return view;
    }
    public void setHexListener(ArrayList<View> hex_b){
        for (View hex: hex_b){
            hex.setOnClickListener(handleClick);
            hex.setOnLongClickListener(handleLongClick);
            hex.setOnTouchListener(handleTouch);
        }
    }
    private View.OnClickListener handleClick = new View.OnClickListener(){
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v) {
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            switch (v.getId()){
                case R.id.sort_hex_b:
                    fragment = new SortFragment();
                    break;
                case R.id.tree_trav_hex_b:
                    fragment = new TreeTraversalFragment();
                    break;
                case R.id.string_m_hex_b:
                    fragment = new StringManipulationFragment();
                    break;
                case R.id.hash_func_hex_b:
                    fragment = new HashFunctionFragment();
                    break;
                case R.id.itr_hex_b:
                    fragment = new IterationFragment();
                    break;
                case R.id.searching_hex_b:
                    fragment = new SearchFragment();
                    break;
            }
            bundle.putInt("color", ((Hexagon)v).getHexiColor());
            fragment.setArguments(bundle);
            //getActivity().getWindow().setStatusBarColor(((Hexagon)v).getHexiColor());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    };
    public View.OnTouchListener handleTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            touched = v;
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                relesed = false;
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.hexi_click);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.hexi_release);
                        if(relesed){
                            touched.startAnimation(animation2);
                            relesed = false;
                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                v.startAnimation(animation);
            }
            else if(event.getAction() == MotionEvent.ACTION_UP){
                Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.hexi_release);
                Animation a = v.getAnimation();
                if(a.hasEnded()){
                    v.startAnimation(animation2);
                    relesed = false;
                }
                else {
                    relesed = true;
                }
                //v.setAnimation(animation);
                //v.startAnimation(animation);
            }
            return false;
        }
    };
    private View.OnLongClickListener handleLongClick = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v) {
            openDialog(v.getId());
            return true;
        }
    };

    public void openDialog(int id){
        Bundle bundle = new Bundle();
        FragmentDialog fragmentDialog;
        Hexagon hex = (view.findViewById(id));
        String text = hex.getHexiText();
        int color = hex.getHexiColor();
        bundle.putString("text",text);
        bundle.putInt("color",color);
        bundle.putString("def", hex.getDef());
        fragmentDialog = new FragmentDialog();
        fragmentDialog.setArguments(bundle);
        fragmentDialog.show(getFragmentManager(),"nothing to see here");
    }

}
