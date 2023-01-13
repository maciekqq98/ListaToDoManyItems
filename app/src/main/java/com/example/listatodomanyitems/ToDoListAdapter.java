package com.example.listatodomanyitems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.listatodomanyitems.ToDoListContract.*;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public ToDoListAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class ToDoListViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;

        public ToDoListViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.textview_name_item);

        }
    }

    @Override
    public ToDoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.todoempty_item, parent, false);
        return new ToDoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoListViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        @SuppressLint("Range") String name = mCursor.getString(mCursor.getColumnIndex(ToDoListEntry.COLUMN_NAME));
        @SuppressLint("Range") long id = mCursor.getLong(mCursor.getColumnIndex(ToDoListEntry._ID));

        holder.nameText.setText(name);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
             notifyDataSetChanged();
        }
    }
}

