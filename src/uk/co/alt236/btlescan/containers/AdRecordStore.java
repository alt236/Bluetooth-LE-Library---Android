package uk.co.alt236.btlescan.containers;

import java.util.Map;

public class AdRecordStore {
	private final Map<Integer, AdRecord> mAdRecords;
	private final int mServiceDataUUId;
	private final String mLocalNameComplete;
	private final String mLocalNameShort;
	
	public AdRecordStore(Map<Integer, AdRecord> adRecords){
		mAdRecords = adRecords;
		mServiceDataUUId = AdRecordUtils.getServiceDataUuid(
				mAdRecords.get(AdRecord.TYPE_SERVICE_DATA));
		
		mLocalNameComplete = AdRecordUtils.getRecordDataAsString(
				mAdRecords.get(AdRecord.TYPE_LOCAL_NAME_COMPLETE));
		
		mLocalNameShort = AdRecordUtils.getRecordDataAsString(
				mAdRecords.get(AdRecord.TYPE_LOCAL_NAME_SHORT));
		
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

	public int getServiceDataUUID(){
		return mServiceDataUUId;
	}

	public boolean isRecordPresent(int record){
		return mAdRecords.containsKey(record);
	}

	@Override
	public String toString() {
		return "AdRecordStore [mServiceDataUUId=" + mServiceDataUUId + ", mLocalNameComplete=" + mLocalNameComplete + ", mLocalNameShort=" + mLocalNameShort + "]";
	}
}
