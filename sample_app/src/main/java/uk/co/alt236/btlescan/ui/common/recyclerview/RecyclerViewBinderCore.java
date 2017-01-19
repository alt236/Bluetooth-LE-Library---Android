package uk.co.alt236.btlescan.ui.common.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewBinderCore {
    public static final int INVALID_VIEWTYPE = -1;

    private static final String TAG = RecyclerViewBinderCore.class.getSimpleName();
    private final List<Class<? extends BaseViewHolder<? extends RecyclerViewItem>>> mViewHolderClasses;
    private final List<BaseViewBinder<? extends RecyclerViewItem>> mViewBinders;
    private final List<Integer> mLayoutIds;

    public RecyclerViewBinderCore() {
        mViewBinders = new ArrayList<>();
        mViewHolderClasses = new ArrayList<>();
        mLayoutIds = new ArrayList<>();
    }

    public void clear() {
        mViewBinders.clear();
        mViewHolderClasses.clear();
        mLayoutIds.clear();
    }

    public <T extends RecyclerViewItem> void add(
            final BaseViewBinder<T> binder,
            final Class<? extends BaseViewHolder<T>> viewHolder,
            final int layoutId) {

        mViewBinders.add(binder);
        mViewHolderClasses.add(viewHolder);
        mLayoutIds.add(layoutId);
    }

    public BaseViewHolder<? extends RecyclerViewItem> create(ViewGroup parent, final int viewType) {
        if (viewType == INVALID_VIEWTYPE) {
            throw new IllegalArgumentException("Invalid viewType: " + viewType);
        }

        final Class<?> clazz = mViewHolderClasses.get(viewType);
        final int layoutId = mLayoutIds.get(viewType);
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        return (BaseViewHolder<? extends RecyclerViewItem>) instantiate(clazz, itemView);
    }

    public <T extends RecyclerViewItem> int getViewType(final T item) {
        int result = INVALID_VIEWTYPE;
        int count = 0;

        for (final BaseViewBinder<? extends RecyclerViewItem> binder : mViewBinders) {

            if (binder.canBind(item)) {
                result = count;
                break;
            }

            count++;
        }

        if (result == INVALID_VIEWTYPE) {
            Log.w(TAG, "Could not get viewType for " + item);
        }

        return result;
    }

    public BaseViewBinder<? extends RecyclerViewItem> getBinder(int viewType) {
        if (viewType == INVALID_VIEWTYPE) {
            throw new IllegalArgumentException("Invalid viewType: " + viewType);
        }

        return mViewBinders.get(viewType);
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    private static Object instantiate(
            final Class<?> clazz, View parentView) {
        try {
            final Constructor<?> constructor
                    = clazz.getDeclaredConstructors()[0];
            return constructor.newInstance(parentView);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }
}
