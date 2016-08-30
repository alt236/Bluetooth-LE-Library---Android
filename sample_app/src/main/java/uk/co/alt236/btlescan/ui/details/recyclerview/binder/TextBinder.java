package uk.co.alt236.btlescan.ui.details.recyclerview.binder;

import android.content.Context;

import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.TextHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.TextItem;

public class TextBinder extends BaseViewBinder<TextItem> {

    public TextBinder(Context context) {
        super(context);
    }

    @Override
    public void bind(BaseViewHolder<TextItem> holder, TextItem item) {
        final TextHolder actualHolder = (TextHolder) holder;
        actualHolder.getTextView().setText(item.getText());
    }

    @Override
    public boolean canBind(RecyclerViewItem item) {
        return item instanceof TextItem;
    }
}
