package com.example.lugdu.datastructuresandalgorithms.algo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.lugdu.datastructuresandalgorithms.FragmentDialog;
import com.example.lugdu.datastructuresandalgorithms.Hexagon;
import com.example.lugdu.datastructuresandalgorithms.MiddleFragment;
import com.example.lugdu.datastructuresandalgorithms.R;

import java.util.ArrayList;

public class AlgorithmFragment extends Fragment {
    View view;
    Boolean relesed;
    View touched = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_algorithm, container,false);
        ArrayList<View> hexA = new ArrayList<>();
        hexA.add(view.findViewById(R.id.sort_hex_b));
        hexA.add(view.findViewById(R.id.tree_trav_hex_b));
        hexA.add(view.findViewById(R.id.string_m_hex_b));
        hexA.add(view.findViewById(R.id.hash_func_hex_b));
        hexA.add(view.findViewById(R.id.itr_hex_b));
        hexA.add(view.findViewById(R.id.searching_hex_b));
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
            Fragment fragment = new MiddleFragment();
            Bundle bundle = new Bundle();
            switch (v.getId()){
                case R.id.sort_hex_b:
                    bundle.putInt("inflater", R.layout.fragment_sort);
                    break;
                case R.id.tree_trav_hex_b:
                    bundle.putInt("inflater", R.layout.fragment_tree_traversal);
                    break;
                case R.id.string_m_hex_b:
                    bundle.putInt("inflater", R.layout.fragment_string_manipulation);
                    break;
                case R.id.hash_func_hex_b:
                    bundle.putInt("inflater", R.layout.fragment_hash_function);
                    break;
                case R.id.itr_hex_b:
                    bundle.putInt("inflater", R.layout.fragment_iteration);
                    break;
                case R.id.searching_hex_b:
                    bundle.putInt("inflater", R.layout.fragment_searching);
                    break;
            }
            fragment.setArguments(bundle);
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
        Hexagon hex = (Hexagon)(view.findViewById(id));
        String text = hex.getHexiText();
        int color = hex.getHexiColor();
        bundle.putString("text",text);
        bundle.putInt("color",color);
        fragmentDialog = new FragmentDialog();
        fragmentDialog.setArguments(bundle);
        fragmentDialog.show(getFragmentManager(),"nothing to see here");
    }

}
