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

import com.example.lugdu.datastructuresandalgorithms.R;

public class GraphsFragment extends Fragment {
    View view;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.middle_fragment, container,false);
        getActivity().getWindow().setStatusBarColor(getArguments().getInt("color"));
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getArguments().getInt("color"));
        TextView textView = view.findViewById(R.id.textviewtoolbar);
        textView.setText("Graphs");
        return view;
    }
}