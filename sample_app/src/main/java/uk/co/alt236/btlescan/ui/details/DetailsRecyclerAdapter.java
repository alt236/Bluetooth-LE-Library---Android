package uk.co.alt236.btlescan.ui.details;

import java.util.List;

import uk.co.alt236.btlescan.ui.common.recyclerview.BaseRecyclerViewAdapter;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewBinderCore;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;

/*package*/ class DetailsRecyclerAdapter extends BaseRecyclerViewAdapter {
    public DetailsRecyclerAdapter(RecyclerViewBinderCore core, List<RecyclerViewItem> items) {
        super(core, items);
    }

//    private static List<RecyclerViewItem> validate(RecyclerViewBinderCore core, List<RecyclerViewItem> items) {
//        final List<RecyclerViewItem> retVal = new ArrayList<>();
//
//        for (final RecyclerViewItem item : items) {
//            if (core.getViewType(item) != RecyclerViewBinderCore.INVALID_VIEWTYPE) {
//                retVal.add(item);
//            }
//        }
//
//        return retVal;
//    }
}
