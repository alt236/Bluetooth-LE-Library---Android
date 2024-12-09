package uk.co.alt236.btlescan.ui.main

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseRecyclerViewAdapter

class View(
    activity: Activity,
) {
    private val resources = activity.resources
    private val mTvBluetoothLeStatus: TextView = activity.findViewById(R.id.tvBluetoothLe)
    private var mTvBluetoothStatus: TextView = activity.findViewById(R.id.tvBluetoothStatus)
    private var mTvItemCount: TextView = activity.findViewById(R.id.tvItemCount)
    private var mList: RecyclerView = activity.findViewById(android.R.id.list)
    private var mEmpty: View = activity.findViewById(android.R.id.empty)

    init {
        mList.layoutManager = LinearLayoutManager(activity)
    }

    fun setBluetoothEnabled(enabled: Boolean) {
        if (enabled) {
            mTvBluetoothStatus.setText(R.string.on)
        } else {
            mTvBluetoothStatus.setText(R.string.off)
        }
    }

    fun setBluetoothLeSupported(supported: Boolean) {
        if (supported) {
            mTvBluetoothLeStatus.setText(R.string.supported)
        } else {
            mTvBluetoothLeStatus.setText(R.string.not_supported)
        }
    }

    fun updateItemCount(count: Int) {
        val text = resources.getString(R.string.formatter_item_count, count.toString())
        mTvItemCount.text = text
    }

    fun setListAdapter(adapter: BaseRecyclerViewAdapter) {
        mList.adapter = adapter
    }
}
