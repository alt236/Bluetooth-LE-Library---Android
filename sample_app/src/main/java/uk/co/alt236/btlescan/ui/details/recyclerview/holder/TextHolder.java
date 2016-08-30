package uk.co.alt236.btlescan.ui.details.recyclerview.holder;

import android.view.View;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.TextItem;

public class TextHolder extends BaseViewHolder<TextItem> {

    private final TextView mText;

    public TextHolder(View itemView) {
        super(itemView);

        mText = (TextView) itemView.findViewById(R.id.text);
    }

    public TextView getTextView() {
        return mText;
    }
}
