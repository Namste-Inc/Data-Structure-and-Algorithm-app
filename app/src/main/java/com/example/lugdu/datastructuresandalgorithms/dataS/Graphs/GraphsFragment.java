package com.example.lugdu.datastructuresandalgorithms.dataS.Graphs;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lugdu.datastructuresandalgorithms.MainActivity;
import com.example.lugdu.datastructuresandalgorithms.R;
import com.example.lugdu.datastructuresandalgorithms.dataS.DataStructureFragment;

public class GraphsFragment extends Fragment {
    View view;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.middle_fragment, container,false);
        int color = getArguments().getInt("color");
        int darkenColor = MainActivity.manipulateColor(color, .8f);
        getActivity().getWindow().setStatusBarColor(darkenColor);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(color);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataStructureFragment()).commit();
            }
        });
        TextView textView = view.findViewById(R.id.textviewtoolbar);
        textView.setText("Graphs");
        return view;
    }
}