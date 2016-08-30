package uk.co.alt236.btlescan.ui.details.recyclerview.binder;

import android.content.Context;

import uk.co.alt236.bluetoothlelib.util.ByteUtils;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.AdRecordHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.AdRecordItem;

public class AdRecordBinder extends BaseViewBinder<AdRecordItem> {

    public AdRecordBinder(Context context) {
        super(context);
    }

    @Override
    public void bind(BaseViewHolder<AdRecordItem> holder, AdRecordItem item) {
        final AdRecordHolder actualHolder = (AdRecordHolder) holder;

        actualHolder.getTitleTextView().setText(item.getTitle());

        actualHolder.getStringTextView().setText(
                getContext().getString(R.string.formatter_single_quoted_string,
                        item.getDataAsString()));

        actualHolder.getArrayTextView().setText(
                getContext().getString(R.string.formatter_single_quoted_string,
                        ByteUtils.byteArrayToHexString(item.getData())));
    }

    @Override
    public boolean canBind(RecyclerViewItem item) {
        return item instanceof AdRecordItem;
    }
}
