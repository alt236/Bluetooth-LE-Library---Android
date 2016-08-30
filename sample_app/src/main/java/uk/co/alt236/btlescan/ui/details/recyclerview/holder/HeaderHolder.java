package uk.co.alt236.btlescan.ui.details.recyclerview.holder;

import android.view.View;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.HeaderItem;

public class HeaderHolder extends BaseViewHolder<HeaderItem> {

    private final TextView mText;

    public HeaderHolder(View itemView) {
        super(itemView);

        mText = (TextView) itemView.findViewById(R.id.text);
    }

    public TextView getTextView() {
        return mText;
    }
}
