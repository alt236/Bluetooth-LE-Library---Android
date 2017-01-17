package uk.co.alt236.btlescan.ui.common.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder<? extends RecyclerViewItem>> {

    private final List<RecyclerViewItem> mItemList;
    private final RecyclerViewBinderCore mCore;

    public BaseRecyclerViewAdapter(final RecyclerViewBinderCore core,
                                   final List<RecyclerViewItem> items) {
        mItemList = new ArrayList<>();
        mCore = core;
        mItemList.addAll(items);
    }

    public BaseRecyclerViewAdapter(final RecyclerViewBinderCore core) {
        this(core, new ArrayList<RecyclerViewItem>());
    }

    @Override
    public BaseViewHolder<? extends RecyclerViewItem> onCreateViewHolder(final ViewGroup parent,
                                                                         final int viewType) {
        return mCore.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder<? extends RecyclerViewItem> holder,
                                 final int position) {

        final int viewType = getItemViewType(position);
        final BaseViewBinder<? extends RecyclerViewItem> binder = mCore.getBinder(viewType);
        bind(binder, holder, getItem(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mCore.getViewType(getItem(position));
    }

    public RecyclerViewItem getItem(final int position) {
        return mItemList.get(position);
    }

    public void setData(Collection<? extends RecyclerViewItem> data) {

        mItemList.clear();
        mItemList.addAll(data);
        notifyDataSetChanged();
    }

    private static <T extends RecyclerViewItem> void bind(final BaseViewBinder<T> binder,
                                                          final BaseViewHolder<?> holder,
                                                          final RecyclerViewItem item) {

        //noinspection unchecked
        binder.bind((BaseViewHolder<T>) holder, (T) item);
    }
}
