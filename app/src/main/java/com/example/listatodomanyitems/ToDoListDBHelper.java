package com.example.listatodomanyitems;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.listatodomanyitems.ToDoListContract.*;


public class ToDoListDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ToDoList.db";
    public static final int DATABASE_VERSION = 1;

    public ToDoListDBHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ToDoLIST_TABLE = "CREATE TABLE " +
                ToDoListEntry.TABLE_NAME + " (" +
                ToDoListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ToDoListEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ToDoListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_ToDoLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ToDoListEntry.TABLE_NAME);
        onCreate(db);
    }
}
