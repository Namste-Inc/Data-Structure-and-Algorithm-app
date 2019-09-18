package com.example.lugdu.datastructuresandalgorithms;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "DSADB.db";
    public static final String TABLE_NAME = "HeapSort";
    public static final String COL_1 = "Id";
    public static final String COL_2 = "Definition";
    public static final String COL_3 = "Step";
    public static final String COL_4 = "Image";



    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
         SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public void insertData(String tableName, String definition,String step){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_2, definition);
//        contentValues.put(COL_3, step);
//        db.insert(tableName, null, contentValues);

    }
    public void addTable(){
        //SQLiteDatabase db = this.getWritableDatabase();

    }
}
