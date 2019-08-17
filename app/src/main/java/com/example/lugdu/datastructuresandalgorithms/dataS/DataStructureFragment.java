package com.example.lugdu.datastructuresandalgorithms.dataS;

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
import com.example.lugdu.datastructuresandalgorithms.dataS.Arrays.ArraysFragment;
import com.example.lugdu.datastructuresandalgorithms.dataS.Graphs.GraphsFragment;
import com.example.lugdu.datastructuresandalgorithms.dataS.HashTables.HashTablesFragment;
import com.example.lugdu.datastructuresandalgorithms.dataS.Linked_List.LinkedListFragment;
import com.example.lugdu.datastructuresandalgorithms.dataS.Queues.QueuesFragment;
import com.example.lugdu.datastructuresandalgorithms.dataS.Stacks.StacksFragment;
import com.example.lugdu.datastructuresandalgorithms.dataS.Trees.TreesFragment;

import java.util.ArrayList;

public class DataStructureFragment extends Fragment {
    View view;
    Boolean released;
    View touched = null;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data_s, container,false);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        ArrayList<View> hexA = new ArrayList<>();
        Hexagon hex_hash=  view.findViewById(R.id.hash_t_hex_b);
        hex_hash.setDef("this def");
        Hexagon hex_stacks = view.findViewById(R.id.stacks_hex_b);
        hex_stacks.setDef("this def stacks");
        Hexagon hex_queues = view.findViewById(R.id.queues_hex_b);
        hex_queues.setDef("this def queues");
        Hexagon hex_ll = view.findViewById(R.id.linked_l_hex_b);
        hex_ll.setDef("this def linked list");
        Hexagon hex_trees = view.findViewById(R.id.trees_hex_b);
        hex_trees.setDef("this def trees");
        Hexagon hex_graphs = view.findViewById(R.id.graphs_hex_b);
        hex_graphs.setDef("this def graphs");
        Hexagon hex_array = view.findViewById(R.id.array_hex_b);
        hex_array.setDef("this def array");
        hexA.add(hex_hash);
        hexA.add(hex_stacks);
        hexA.add(hex_queues);
        hexA.add(hex_ll);
        hexA.add(hex_trees);
        hexA.add(hex_graphs);
        hexA.add(hex_array);
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
        public void onClick(View v) {
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            switch (v.getId()){
                case R.id.array_hex_b:
                    fragment = new ArraysFragment();
                    break;
                case R.id.hash_t_hex_b:
                    fragment = new HashTablesFragment();
                    break;
                case R.id.graphs_hex_b:
                    fragment = new GraphsFragment();
                    break;
                case R.id.linked_l_hex_b:
                    fragment = new LinkedListFragment();
                    break;
                case R.id.queues_hex_b:
                    fragment = new QueuesFragment();
                    break;
                case R.id.stacks_hex_b:
                    fragment = new StacksFragment();
                    break;
                case R.id.trees_hex_b:
                    fragment = new TreesFragment();
                    break;
            }
            bundle.putInt("color", ((Hexagon)v).getHexiColor());
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    };
    public View.OnTouchListener handleTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            touched = v;
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                released = false;
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.hexi_click);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.hexi_release);
                        if(released){
                            touched.startAnimation(animation2);
                            released = false;
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
                    released = false;
                }
                else {
                    released = true;
                }
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
