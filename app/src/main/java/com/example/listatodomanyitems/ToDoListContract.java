package com.example.listatodomanyitems;

import android.provider.BaseColumns;

public class ToDoListContract {

    private ToDoListContract() {
    }

    public static final class ToDoListEntry implements BaseColumns {
        //BaseColumns  _ID
        public static final String TABLE_NAME = "ToDoList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
