package com.example.lugdu.datastructuresandalgorithms.algo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lugdu.datastructuresandalgorithms.FragmentDialog;
import com.example.lugdu.datastructuresandalgorithms.Hexagon;
import com.example.lugdu.datastructuresandalgorithms.R;

import java.util.ArrayList;

public class AlgorithmFragment extends Fragment {
    View view;
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
        }
    }
    private View.OnClickListener handleClick = new View.OnClickListener(){
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sort_hex_b:
                    break;
                case R.id.tree_trav_hex_b:
                    break;
                case R.id.string_m_hex_b:
                    break;
                case R.id.hash_func_hex_b:
                    break;
                case R.id.itr_hex_b:
                    break;
                case R.id.searching_hex_b:
                    break;
                case R.id.hash_t_hex_b:
                    break;
                case R.id.stacks_hex_b:
                    break;
                case R.id.queues_hex_b:
                    break;
                case R.id.linked_l_hex_b:
                    break;
                case R.id.trees_hex_b:
                    break;
                case R.id.graphs_hex_b:
                    break;
                case R.id.array_hex_b:
                    break;
            }
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
