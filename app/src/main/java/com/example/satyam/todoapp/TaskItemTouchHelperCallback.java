package com.example.satyam.todoapp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by satyam on 8/4/17.
 */

public class TaskItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final TaskItemHelperAdapter mAdapter;

//    @Override
//    public boolean isLongPressDragEnabled() {
//        return true;
//    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    public TaskItemTouchHelperCallback(TaskItemHelperAdapter adapter){
        mAdapter = adapter;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        return makeMovementFlags(0,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {

        mAdapter.onItemDismiss((long)viewHolder.itemView.getTag());

    }
}
