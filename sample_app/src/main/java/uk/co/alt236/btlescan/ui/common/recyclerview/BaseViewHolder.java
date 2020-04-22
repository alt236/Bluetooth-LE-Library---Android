package uk.co.alt236.btlescan.ui.common.recyclerview;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T extends RecyclerViewItem> extends RecyclerView.ViewHolder {

    private final View mItemView;

    public BaseViewHolder(final View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    public View getView() {
        return mItemView;
    }
}
