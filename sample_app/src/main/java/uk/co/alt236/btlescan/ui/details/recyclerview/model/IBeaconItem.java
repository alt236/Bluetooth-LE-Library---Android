package uk.co.alt236.btlescan.ui.details.recyclerview.model;

import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconManufacturerData;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;

public class IBeaconItem implements RecyclerViewItem {

    private final int mMajor;
    private final int mMinor;
    private final String mUuid;
    private final int mCompanyIdentifier;
    private final int mIBeaconAdvertisement;
    private final int mCalibratedTxPower;

    public IBeaconItem(final IBeaconManufacturerData iBeaconData) {
        mMajor = iBeaconData.getMajor();
        mMinor = iBeaconData.getMinor();
        mUuid = iBeaconData.getUUID();
        mCompanyIdentifier = iBeaconData.getCompanyIdentifier();
        mIBeaconAdvertisement = iBeaconData.getIBeaconAdvertisement();
        mCalibratedTxPower = iBeaconData.getCalibratedTxPower();
    }

    public int getCompanyIdentifier() {
        return mCompanyIdentifier;
    }

    public int getMajor() {
        return mMajor;
    }

    public int getMinor() {
        return mMinor;
    }

    public String getUuid() {
        return mUuid;
    }

    public int getIBeaconAdvertisement() {
        return mIBeaconAdvertisement;
    }

    public int getCalibratedTxPower() {
        return mCalibratedTxPower;
    }
}
