package uk.co.alt236.btlescan.ui.details.recyclerview.holder;

import android.view.View;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.IBeaconItem;

public class IBeaconHolder extends BaseViewHolder<IBeaconItem> {

    private final TextView mCompanyId;
    private final TextView mAdvert;
    private final TextView mUuid;
    private final TextView mMajor;
    private final TextView mMinor;
    private final TextView mTxPower;

    public IBeaconHolder(View itemView) {
        super(itemView);

        mCompanyId = (TextView) itemView.findViewById(R.id.companyId);
        mAdvert = (TextView) itemView.findViewById(R.id.advertisement);
        mUuid = (TextView) itemView.findViewById(R.id.uuid);
        mMajor = (TextView) itemView.findViewById(R.id.major);
        mMinor = (TextView) itemView.findViewById(R.id.minor);
        mTxPower = (TextView) itemView.findViewById(R.id.txpower);
    }

    public TextView getCompanyId() {
        return mCompanyId;
    }

    public TextView getAdvert() {
        return mAdvert;
    }

    public TextView getUuid() {
        return mUuid;
    }

    public TextView getMajor() {
        return mMajor;
    }

    public TextView getMinor() {
        return mMinor;
    }

    public TextView getTxPower() {
        return mTxPower;
    }
}
