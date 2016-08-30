package uk.co.alt236.btlescan.ui.details.recyclerview.model;

import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;
import uk.co.alt236.bluetoothlelib.util.AdRecordUtils;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;

public class AdRecordItem implements RecyclerViewItem {

    private final String mTitle;
    private final byte[] mData;
    private final String mDataAsString;

    public AdRecordItem(final String title,
                        final AdRecord record) {
        mTitle = title;
        mData = record.getData();
        mDataAsString = AdRecordUtils.getRecordDataAsString(record);
    }

    public String getTitle() {
        return mTitle;
    }

    public byte[] getData() {
        return mData;
    }

    public String getDataAsString() {
        return mDataAsString;
    }
}
