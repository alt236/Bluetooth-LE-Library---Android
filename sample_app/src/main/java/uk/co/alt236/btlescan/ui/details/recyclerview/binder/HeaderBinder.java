package uk.co.alt236.btlescan.ui.details.recyclerview.binder;

import android.content.Context;

import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.HeaderHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.HeaderItem;

public class HeaderBinder extends BaseViewBinder<HeaderItem> {

    public HeaderBinder(Context context) {
        super(context);
    }

    @Override
    public void bind(BaseViewHolder<HeaderItem> holder, HeaderItem item) {
        final HeaderHolder actualHolder = (HeaderHolder) holder;
        actualHolder.getTextView().setText(item.getText());
    }

    @Override
    public boolean canBind(RecyclerViewItem item) {
        return item instanceof HeaderItem;
    }
}
