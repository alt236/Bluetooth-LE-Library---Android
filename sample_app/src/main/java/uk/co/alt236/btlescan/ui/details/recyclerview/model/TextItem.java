package uk.co.alt236.btlescan.ui.details.recyclerview.model;

import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;

public class TextItem implements RecyclerViewItem {
    private final CharSequence mText;

    public TextItem(CharSequence text) {
        mText = text;
    }

    public CharSequence getText() {
        return mText;
    }
}
