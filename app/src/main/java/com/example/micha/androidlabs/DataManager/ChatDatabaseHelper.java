package com.example.micha.androidlabs.DataManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Messages_Management";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "Messages";
    public static final String KEY_ID = "ID";
    public static final String KEY_MESSAGE = "message";
    public static final String SQL = "CREATE TABLE "+TABLE_NAME+"("+KEY_ID+
            " integer primary key autoincrement, "+KEY_MESSAGE+" text );";

    //Write a constructor that opens a database file “Messages.db”
    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    //Write the onCreate(SQLiteDatabase db)  function to create a table with two columns
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL);
        Log.i("ChatDatabaseHelper", "Calling onCtreate");
    }

    //Write the onUpgrade(SQLiteDatabase db) function so that it executes the SQL statement
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion="+i+"newVersion="+i1);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion="+oldVer+"newVersion="+newVer);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}