package uk.co.alt236.btlescan.ui.details.recyclerview.holder

import android.view.View
import android.widget.TextView
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.view.details.model.HeaderItem
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewHolder

class HeaderHolder(
    itemView: View,
) : BaseViewHolder<HeaderItem>(itemView) {
    val textView: TextView = itemView.findViewById<View>(R.id.text) as TextView
}
