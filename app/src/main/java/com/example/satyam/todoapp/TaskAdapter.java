package com.example.satyam.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.satyam.todoapp.data.TaskContract;

/**
 * Created by satyam on 19/3/17.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TodoViewHolder> {


    private Cursor mCursor;
    private Context mContext;

    TaskClickListener mListener;

    public interface TaskClickListener {
        public void OnTaskClick(View itemView);
    }

    public TaskAdapter(Context context, TaskClickListener listener) {
        mListener = listener;
        mContext = context;
    }


    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.task_layout, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        holder.todoView.setText(mCursor.getString(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TODO)));
        holder.itemView.setTag(mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry._ID)));


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView todoView;

        public TodoViewHolder(View itemView) {
            super(itemView);

            todoView = (TextView) itemView.findViewById(R.id.todo_textView);
            todoView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.OnTaskClick(itemView);


        }
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
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
