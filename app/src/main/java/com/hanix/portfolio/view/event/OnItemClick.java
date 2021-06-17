package com.hanix.portfolio.view.event;

import android.view.View;

/**
 * ItemClick -> recyclerView click event
 */
public interface OnItemClick {

    void onClick(View v, int position);

}
