package uk.co.alt236.bluetoothlelib.device.adrecord;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import uk.co.alt236.bluetoothlelib.util.AdRecordUtils;

/**
 * The Class AdRecordStore.
 */
public class AdRecordStore implements Parcelable {
    public static final Parcelable.Creator<AdRecordStore> CREATOR = new Parcelable.Creator<AdRecordStore>() {
        public AdRecordStore createFromParcel(final Parcel in) {
            return new AdRecordStore(in);
        }

        public AdRecordStore[] newArray(final int size) {
            return new AdRecordStore[size];
        }
    };
    private final SparseArray<AdRecord> mAdRecords;
    private final String mLocalNameComplete;
    private final String mLocalNameShort;

    public AdRecordStore(final Parcel in) {
        final Bundle b = in.readBundle(getClass().getClassLoader());
        mAdRecords = b.getSparseParcelableArray("records_array");
        mLocalNameComplete = b.getString("local_name_complete");
        mLocalNameShort = b.getString("local_name_short");
    }

    /**
     * Instantiates a new Bluetooth LE device Ad Record Store.
     *
     * @param adRecords the ad records
     */
    public AdRecordStore(final SparseArray<AdRecord> adRecords) {
        mAdRecords = adRecords;

        mLocalNameComplete = AdRecordUtils.getRecordDataAsString(
                mAdRecords.get(AdRecord.TYPE_LOCAL_NAME_COMPLETE));

        mLocalNameShort = AdRecordUtils.getRecordDataAsString(
                mAdRecords.get(AdRecord.TYPE_LOCAL_NAME_SHORT));

    }

    /* (non-Javadoc)
     * @see android.os.Parcelable#describeContents()
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Gets the short local device name.
     *
     * @return the local name complete
     */
    public String getLocalNameComplete() {
        return mLocalNameComplete;
    }

    /**
     * Gets the complete local device name.
     *
     * @return the local name short
     */
    public String getLocalNameShort() {
        return mLocalNameShort;
    }

    /**
     * retrieves an individual record.
     *
     * @param record the record
     * @return the record
     */
    public AdRecord getRecord(final int record) {
        return mAdRecords.get(record);
    }

    /**
     * Gets the record data as string.
     *
     * @param record the record
     * @return the record data as string
     */
    public String getRecordDataAsString(final int record) {
        return AdRecordUtils.getRecordDataAsString(
                mAdRecords.get(record));
    }

    /**
     * Gets the record as collection.
     *
     * @return the records as collection
     */
    public Collection<AdRecord> getRecordsAsCollection() {
        return Collections.unmodifiableCollection(asList(mAdRecords));
    }

    /**
     * Checks if is record present.
     *
     * @param record the record
     * @return true, if is record present
     */
    public boolean isRecordPresent(final int record) {
        return mAdRecords.indexOfKey(record) >= 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AdRecordStore [mLocalNameComplete=" + mLocalNameComplete + ", mLocalNameShort=" + mLocalNameShort + "]";
    }

    /* (non-Javadoc)
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    @Override
    public void writeToParcel(final Parcel parcel, final int arg1) {
        final Bundle b = new Bundle();
        b.putString("local_name_complete", mLocalNameComplete);
        b.putString("local_name_short", mLocalNameShort);
        b.putSparseParcelableArray("records_array", mAdRecords);

        parcel.writeBundle(b);
    }

    /**
     * As list.
     *
     * @param <C>         the generic type
     * @param sparseArray the sparse array
     * @return the collection
     */
    public static <C> Collection<C> asList(final SparseArray<C> sparseArray) {
        if (sparseArray == null) return null;

        final Collection<C> arrayList = new ArrayList<>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++) {
            arrayList.add(sparseArray.valueAt(i));
        }

        return arrayList;
    }
}
