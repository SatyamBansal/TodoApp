package com.example.satyam.todoapp;

/**
 * Created by satyam on 8/4/17.
 */

public interface TaskItemHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(long id);
}
