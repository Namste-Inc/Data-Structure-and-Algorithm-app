package com.example.lugdu.datastructuresandalgorithms.algo.Sort;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lugdu.datastructuresandalgorithms.R;

public class BubbleSortFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bubble_sort, container,false);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swap(R.id.textView6, R.id.textView7);
            }
        });
        return view;
    }
    public void swap(int id1, int id2){
        TextView txt1 = view.findViewById(id1);
        TextView txt2 = view.findViewById(id2);
        float x1 = txt1.getX();
        float x2 = txt2.getX();
        View view2 = view.findViewById(R.id.button);
        ObjectAnimator animation = ObjectAnimator.ofFloat(txt1, "translationX", (x2 - x1));
        animation.setDuration(1000);
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(txt2, "translationX", (x1 - x2));
        animation2.setDuration(1000);
        animation.start();
        animation2.start();
    }
    public void ignite(int id){

    }
}
