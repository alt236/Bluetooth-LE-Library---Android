package uk.co.alt236.btlescan.ui.common.recyclerview;

import android.content.Context;

public abstract class BaseViewBinder<T extends RecyclerViewItem> {

    private final Context mContext;

    public BaseViewBinder(final Context context) {
        mContext = context;
    }

    public abstract void bind(BaseViewHolder<T> holder, T item);

    public abstract boolean canBind(RecyclerViewItem item);

    protected Context getContext() {
        return mContext;
    }
}
