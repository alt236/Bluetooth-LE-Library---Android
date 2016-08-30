package uk.co.alt236.btlescan.ui.details.recyclerview.binder;

import android.content.Context;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.RssiInfoHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.RssiItem;
import uk.co.alt236.btlescan.util.TimeFormatter;

public class RssiBinder extends BaseViewBinder<RssiItem> {

    public RssiBinder(Context context) {
        super(context);
    }

    private static String formatTime(final long time) {
        return TimeFormatter.getIsoDateTime(time);
    }

    @Override
    public void bind(BaseViewHolder<RssiItem> holder, RssiItem item) {
        final RssiInfoHolder actualHolder = (RssiInfoHolder) holder;

        actualHolder.getFirstTimestamp().setText(formatTime(item.getFirstTimestamp()));
        actualHolder.getFirstRssi().setText(formatRssi(item.getFirstRssi()));
        actualHolder.getLastTimestamp().setText(formatTime(item.getTimestamp()));
        actualHolder.getLastRssi().setText(formatRssi(item.getRssi()));
        actualHolder.getRunningAverageRssi().setText(formatRssi(item.getRunningAverageRssi()));
    }

    @Override
    public boolean canBind(RecyclerViewItem item) {
        return item instanceof RssiItem;
    }

    private String formatRssi(final double rssi) {
        return getContext().getString(R.string.formatter_db, String.valueOf(rssi));
    }

    private String formatRssi(final int rssi) {
        return getContext().getString(R.string.formatter_db, String.valueOf(rssi));
    }
}
