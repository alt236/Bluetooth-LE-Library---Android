package uk.co.alt236.btlescan.app.ui.view.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseViewHolder<T : RecyclerViewItem>(
    val view: View,
) : ViewHolder(view)
