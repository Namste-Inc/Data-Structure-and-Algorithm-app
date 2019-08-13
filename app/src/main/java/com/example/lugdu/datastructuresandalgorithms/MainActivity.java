package com.example.lugdu.datastructuresandalgorithms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lugdu.datastructuresandalgorithms.algo.AlgorithmFragment;
import com.example.lugdu.datastructuresandalgorithms.algo.SortFragment;
import com.example.lugdu.datastructuresandalgorithms.dataS.DataStructureFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            fragment = new HomeFragment();
                            break;
                        case R.id.navigation_data:
                            fragment = new DataStructureFragment();
                            break;
                        case R.id.navigation_algo:
                            fragment = new AlgorithmFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    return true;
                }
    };
    public void showM(View v){
        Toast toast = Toast.makeText(getApplicationContext(), " hii there", Toast.LENGTH_LONG);
        toast.show();
    }
    public void holdHexiListeners(View v){

    }
    public void touchHexiListener(View v){
        int id = v.getId();
        Toast toast = Toast.makeText(getApplicationContext(), id + "h", Toast.LENGTH_LONG);
        toast.show();
        Fragment frag = null;
        switch (id){
            case (R.id.sort_hex_b):
                frag = new SortFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag).commit();
    }
}
