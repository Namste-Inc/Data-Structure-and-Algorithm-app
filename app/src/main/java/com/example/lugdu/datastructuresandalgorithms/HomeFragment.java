package com.example.lugdu.datastructuresandalgorithms;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    View view;
    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        view = inflater.inflate(R.layout.fragment_home, container,false);
        textView = view.findViewById(R.id.welcome_text);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runWelcomeAni(textView, "Welcome to the party");
            }
        });
        thread.start();


        return view;
    }
    public void runWelcomeAni(final TextView txt, final String message){
        for(int i = 0; i < 5; i ++){
            final int finalI = i;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt.setText("|");
                }
            });
            pause(Thread.currentThread(), 400);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt.setText("");
                }
            });
            pause(Thread.currentThread(), 200);

        }
        for(int i = 0; i < message.length(); i++){
            final int finalI = i;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt.append(message.charAt(finalI) + "");
                }
            });
            pause(Thread.currentThread(), 90);

        }
    }
    public void pause(Thread thread, int time){
        synchronized (thread){
            try {
                Thread.currentThread().wait(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}