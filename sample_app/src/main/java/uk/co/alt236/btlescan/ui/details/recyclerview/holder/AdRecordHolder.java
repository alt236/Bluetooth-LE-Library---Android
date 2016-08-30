package uk.co.alt236.btlescan.ui.details.recyclerview.holder;

import android.view.View;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.AdRecordItem;

public class AdRecordHolder extends BaseViewHolder<AdRecordItem> {

    private final TextView mStringTextView;
    private final TextView mArrayTextView;
    private final TextView mTitleTextView;

    public AdRecordHolder(View itemView) {
        super(itemView);

        mStringTextView = (TextView) itemView.findViewById(R.id.data_as_string);
        mArrayTextView = (TextView) itemView.findViewById(R.id.data_as_array);
        mTitleTextView = (TextView) itemView.findViewById(R.id.title);
    }

    public TextView getStringTextView() {
        return mStringTextView;
    }

    public TextView getArrayTextView() {
        return mArrayTextView;
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }
}
