package com.example.lugdu.datastructuresandalgorithms;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.example.lugdu.datastructuresandalgorithms.algo.AlgorithmFragment;
import com.example.lugdu.datastructuresandalgorithms.dataS.DataStructureFragment;

public class MainActivity extends AppCompatActivity  {
    public static int width,height;
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        myDB = new DatabaseHelper(this);

        myDB.insertData("BubbleSort", null, "Iterates Through array and see if succeeding number if less than the current number");
        myDB.insertData("BubbleSort", null, "If the current number is less than the succeeding number the two numbers swap");
        myDB.insertData("BubbleSort", null, "After each iteration the next number starting from the back is sorted");

        myDB.insertData("SelectionSort", null, "Starting from the beginning of the array, sets the next item in the array to be the minimum value in the array");
        myDB.insertData("SelectionSort", null, "Iterates through array to find a lower value then the minimum value and swaps it with the old minimum value making it the new minimum value");
        myDB.insertData("SelectionSort", null, "Each preceding item in the array will be sorted starting from the front after each iteration");

        myDB.insertData("InsertionSort", null, "Starting with the second item in the array for as long as the number to the left is less than the current number swap current number with the number on its left");
        myDB.insertData("InsertionSort", null, "This is done for each preceding number in array until we reach the end of the array");

        myDB.insertData("QuickSort", null, "Select a pivot of your choosing");
        myDB.insertData("QuickSort", null, "Put all items lower than the pivot to its left and all items larger than the pivot to its right, the pivot is now sorted");
        myDB.insertData("QuickSort", null, "Repeat the above step for the right side of the pivot as a sub array and do the same for the right side of the pivot");

        myDB.insertData("MergeSort", null, "The algorithm starts by splitting the array half then split those halves in half until there is until each subarray is size one");
        myDB.insertData("MergeSort", null, "Each pair of halves is sorted then sorts as they merge with each other");
        myDB.insertData("MergeSort", null, "This method can be looked at as similar to divide and conquer but they sort as they conquer");

        myDB.insertData("HeapSort", null, "Puts array in heap");
        myDB.insertData("HeapSort", null, "Change heap to max heap (A max heap states that a parent node is always greater than or equal to its child node)");
        myDB.insertData("HeapSort", null, "Take the root and swap it with the last node in the heap");
        myDB.insertData("HeapSort", null, "Remove or ignore that node as part of the heap because it has just been sorted");
        myDB.insertData("HeapSort", null, "Since the array is already in a heap, repeat the rest of the steps to sorted the rest of the nodes in the heap or the rest of the elements in the array");
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

    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }
}
