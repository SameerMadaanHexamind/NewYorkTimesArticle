package com.scorematics.android.interfaces;

/**
 * Created by sameer.madaan on 12/1/2017.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
