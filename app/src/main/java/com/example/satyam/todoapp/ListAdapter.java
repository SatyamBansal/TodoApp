package com.example.satyam.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.satyam.todoapp.data.TaskContract;

/**
 * Created by satyam on 21/3/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {


    private Cursor mCursor;
    private Context mContext;
    private ListClickListener mListener;

    public interface ListClickListener {
        void onListClick(String listName);
    }

    public ListAdapter(Context context, ListClickListener listener) {
        mListener = listener;
        mContext = context;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_layout, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

        if (mCursor != null) {

            mCursor.moveToNext();
//        Toast.makeText(mContext, "" + mCursor.getString(mCursor.getColumnIndex(TaskContract.ListEntry.COLUMN_SORT_ORDER)), Toast.LENGTH_SHORT).show();

            holder.listName.setText(mCursor.getString(mCursor.getColumnIndex(TaskContract.ListEntry.COLUMN_LISTS)));
        }

    }

    @Override
    public int getItemCount() {
        if (mCursor == null)
            return 0;
        return mCursor.getCount();
    }

    public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public EditText changeListName;
        public TextView listName;

        public ListHolder(View itemView) {
            super(itemView);

            listName = (TextView) itemView.findViewById(R.id.list_name_textView);
            changeListName = (EditText) itemView.findViewById(R.id.change_listName_editText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mListener.onListClick(listName.getText().toString());


        }
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }
}
