package com.example.lugdu.datastructuresandalgorithms;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Steps2Fragment extends Fragment {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps2, container,false);
        TextView textView = view.findViewById(R.id.steps);
        textView.setText(getArguments().getString("step"));
        ImageView imageView = view.findViewById(R.id.imageviwer);
        imageView.setImageResource(getArguments().getInt("image"));
        return view;
    }
}