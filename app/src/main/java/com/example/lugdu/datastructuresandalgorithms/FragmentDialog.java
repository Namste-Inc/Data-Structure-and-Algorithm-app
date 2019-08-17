package com.example.lugdu.datastructuresandalgorithms;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class FragmentDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String text = getArguments().getString("text");
        int color = getArguments().getInt("color");
        String def = getArguments().getString("def");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        View view2 = (Hexagon)view.findViewById(R.id.hexagon);
        View vie = (TextView)view.findViewById(R.id.textView2);
        ((TextView) vie).setText(def);
        ((Hexagon) view2).setHexiText(text);
        ((Hexagon) view2).setHexiColor(color);
        ((Hexagon) view2).toogleShadow();
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(90);
        int[] colors = {Color.WHITE,color};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            drawable.setColors(colors);
            vie.setBackground(drawable);
        }
        view.setBackgroundColor(Color.TRANSPARENT);
        builder.setView(view);
        Dialog d = builder.create();
        d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.hexi);
        view2.setAnimation(animation);
        d.getWindow().getAttributes().windowAnimations = R.style.animation;
        return d;
    }
}
