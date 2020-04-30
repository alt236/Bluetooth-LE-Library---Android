package uk.co.alt236.btlescan.ui.common.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter @JvmOverloads constructor(private val core: RecyclerViewBinderCore,
                                                                 items: List<RecyclerViewItem> = ArrayList()) : RecyclerView.Adapter<BaseViewHolder<out RecyclerViewItem>>() {

    private val list = ArrayList<RecyclerViewItem>()

    init {
        list.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): BaseViewHolder<out RecyclerViewItem> {
        return core.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out RecyclerViewItem>,
                                  position: Int) {
        val viewType = getItemViewType(position)
        val binder = core.getBinder(viewType)

        bind(binder, holder, getItem(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return core.getViewType(getItem(position))
    }

    fun getItem(position: Int): RecyclerViewItem? {
        return list[position]
    }

    fun setData(data: Collection<RecyclerViewItem>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    companion object {
        private fun <T : RecyclerViewItem> bind(binder: BaseViewBinder<T>,
                                                holder: BaseViewHolder<*>,
                                                item: RecyclerViewItem?) {
            @Suppress("UNCHECKED_CAST")
            binder.bind((holder as BaseViewHolder<T>), item as T)
        }
    }
}