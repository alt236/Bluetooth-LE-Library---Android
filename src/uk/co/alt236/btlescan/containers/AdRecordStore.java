package uk.co.alt236.btlescan.containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class AdRecordStore implements Parcelable{

	private final SparseArray<AdRecord> mAdRecords;
	private final String mLocalNameComplete;
	private final String mLocalNameShort;

	public static final Parcelable.Creator<AdRecordStore> CREATOR = new Parcelable.Creator<AdRecordStore>() {
		public AdRecordStore createFromParcel(Parcel in) {
			return new AdRecordStore(in);
		}

		public AdRecordStore[] newArray(int size) {
			return new AdRecordStore[size];
		}
	};

	public AdRecordStore(Parcel in) {
		final Bundle b = in.readBundle();
		mAdRecords = b.getSparseParcelableArray("records_array");
		mLocalNameComplete = b.getString("local_name_complete");
		mLocalNameShort = b.getString("local_name_short");
	}

	public AdRecordStore(final SparseArray<AdRecord> adRecords){
		mAdRecords = adRecords;

		mLocalNameComplete = AdRecordUtils.getRecordDataAsString(
				mAdRecords.get(AdRecord.TYPE_LOCAL_NAME_COMPLETE));

		mLocalNameShort = AdRecordUtils.getRecordDataAsString(
				mAdRecords.get(AdRecord.TYPE_LOCAL_NAME_SHORT));

	}

	@Override
	public int describeContents() {
		return 0;
	}

	public String getLocalNameComplete() {
		return mLocalNameComplete;
	}

	public String getLocalNameShort() {
		return mLocalNameShort;
	}

	public AdRecord getRecord(int record){
		return mAdRecords.get(record);
	}

	public String getRecordDataAsString(int record){
		return AdRecordUtils.getRecordDataAsString(
				mAdRecords.get(record));
	}

	public Collection<AdRecord> getRecordsAsCollection() {
		return Collections.unmodifiableCollection(asList(mAdRecords));
	}

	public boolean isRecordPresent(int record){
		return mAdRecords.indexOfKey(record) >= 0;
	}

	@Override
	public String toString() {
		return "AdRecordStore [mLocalNameComplete=" + mLocalNameComplete + ", mLocalNameShort=" + mLocalNameShort + "]";
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		final Bundle b = new Bundle();
		b.putString("local_name_complete", mLocalNameComplete);
		b.putString("local_name_short", mLocalNameShort);
		b.putSparseParcelableArray("records_array", mAdRecords);

		parcel.writeBundle(b);
	}

	public static <C> Collection<C> asList(SparseArray<C> sparseArray) {
	    if (sparseArray == null) return null;

	    final Collection<C> arrayList = new ArrayList<C>(sparseArray.size());
	    for (int i = 0; i < sparseArray.size(); i++){
	        arrayList.add(sparseArray.valueAt(i));
	    }

	    return arrayList;
	}
}
