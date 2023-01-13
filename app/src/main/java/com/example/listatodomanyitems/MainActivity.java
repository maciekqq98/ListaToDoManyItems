package com.example.listatodomanyitems;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private ToDoListAdapter mAdapter;
    private EditText mEditTextName;
    private AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToDoListDBHelper dbHelper = new ToDoListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ToDoListAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                builder = new AlertDialog.Builder(viewHolder.itemView.getContext());
                builder.setTitle("Usuń zadanie");
                builder.setMessage("Jesteś pewny, że chcesz usunąć tą pozycje ??");
                builder.setPositiveButton("Tak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeItem((long) viewHolder.itemView.getTag());
                            }
                        });
                builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(getApplicationContext(),"Nie usunieto zadania",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }).attachToRecyclerView(recyclerView);

        mEditTextName = findViewById(R.id.edittext_name);
        Button buttonAdd = findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
           }
        });

    }

    private void addItem() {

        if (mEditTextName.getText().toString().trim().length() == 0 ) {
            return;
        }

        String name = mEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(ToDoListContract.ToDoListEntry.COLUMN_NAME, name);

        mDatabase.insert(ToDoListContract.ToDoListEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
        Toast.makeText(getApplicationContext(),"Dodano do listy "+ name+" !!", Toast.LENGTH_SHORT).show();
        mEditTextName.getText().clear();
        mEditTextName.setHint(R.string.hinit);
        // ukrycie klaw  po dodaniu
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void removeItem(long id) {
        mDatabase.delete(ToDoListContract.ToDoListEntry.TABLE_NAME,
                ToDoListContract.ToDoListEntry._ID + "=" + id, null);
        Toast.makeText(getApplicationContext(),"Usunieto zadanie", Toast.LENGTH_SHORT).show();
        mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                ToDoListContract.ToDoListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ToDoListContract.ToDoListEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}