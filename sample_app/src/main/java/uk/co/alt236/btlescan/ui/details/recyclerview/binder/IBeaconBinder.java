package uk.co.alt236.btlescan.ui.details.recyclerview.binder;

import android.content.Context;

import java.util.Locale;

import uk.co.alt236.bluetoothlelib.resolvers.CompanyIdentifierResolver;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.IBeaconHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.IBeaconItem;
import uk.co.alt236.btlescan.util.TimeFormatter;

public class IBeaconBinder extends BaseViewBinder<IBeaconItem> {
    private static final String STRING_FORMAT = "%s (%s)";

    public IBeaconBinder(Context context) {
        super(context);
    }

    private static String formatTime(final long time) {
        return TimeFormatter.getIsoDateTime(time);
    }

    private static String getWithHexEncode(final String first, final int value) {
        return createLine(first, hexEncode(value));
    }

    private static String getWithHexEncode(final int value) {
        return createLine(String.valueOf(value), hexEncode(value));
    }

    private static String createLine(final String first, final String second) {
        return String.format(Locale.US, STRING_FORMAT, first, second);
    }

    private static String hexEncode(final int integer) {
        return "0x" + Integer.toHexString(integer).toUpperCase(Locale.US);
    }

    @Override
    public void bind(BaseViewHolder<IBeaconItem> holder, IBeaconItem item) {
        final IBeaconHolder actualHolder = (IBeaconHolder) holder;


        final String companyName = CompanyIdentifierResolver.getCompanyName(
                item.getCompanyIdentifier(),
                getContext().getString(R.string.unknown));

        actualHolder.getCompanyId().setText(
                getWithHexEncode(companyName, item.getCompanyIdentifier()));

        actualHolder.getAdvert().setText(getWithHexEncode(item.getIBeaconAdvertisement()));
        actualHolder.getUuid().setText(item.getUuid());
        actualHolder.getMajor().setText(getWithHexEncode(item.getMajor()));
        actualHolder.getMinor().setText(getWithHexEncode(item.getMinor()));
        actualHolder.getTxPower().setText(getWithHexEncode(item.getCalibratedTxPower()));
    }

    @Override
    public boolean canBind(RecyclerViewItem item) {
        return item instanceof IBeaconItem;
    }
}
