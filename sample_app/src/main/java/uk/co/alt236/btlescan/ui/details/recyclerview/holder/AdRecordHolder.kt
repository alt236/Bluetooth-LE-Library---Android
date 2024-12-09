package uk.co.alt236.btlescan.ui.details.recyclerview.holder

import android.view.View
import android.widget.TextView
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.AdRecordItem

class AdRecordHolder(
    itemView: View,
) : BaseViewHolder<AdRecordItem>(itemView) {
    val stringTextView: TextView = itemView.findViewById<View>(R.id.data_as_string) as TextView
    val lengthTextView: TextView = itemView.findViewById<View>(R.id.length) as TextView
    val arrayTextView: TextView = itemView.findViewById<View>(R.id.data_as_array) as TextView
    val charactersTextView: TextView = itemView.findViewById<View>(R.id.data_as_characters) as TextView
    val titleTextView: TextView = itemView.findViewById<View>(R.id.title) as TextView
}
