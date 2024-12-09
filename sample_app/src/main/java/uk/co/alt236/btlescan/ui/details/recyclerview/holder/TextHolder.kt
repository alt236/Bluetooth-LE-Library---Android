package uk.co.alt236.btlescan.ui.details.recyclerview.holder

import android.view.View
import android.widget.TextView
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.TextItem

class TextHolder(
    itemView: View,
) : BaseViewHolder<TextItem>(itemView) {
    val textView: TextView = itemView.findViewById<View>(R.id.text) as TextView
}
