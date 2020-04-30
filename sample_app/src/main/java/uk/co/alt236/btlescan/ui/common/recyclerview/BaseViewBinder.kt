package uk.co.alt236.btlescan.ui.common.recyclerview

import android.content.Context

abstract class BaseViewBinder<T : RecyclerViewItem>(protected val context: Context) {
    abstract fun bind(holder: BaseViewHolder<T>, item: T)
    abstract fun canBind(item: RecyclerViewItem): Boolean
}