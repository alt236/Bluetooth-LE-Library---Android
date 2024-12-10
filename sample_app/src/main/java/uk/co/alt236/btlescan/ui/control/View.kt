package uk.co.alt236.btlescan.ui.control

import android.app.Activity
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import dev.alt236.bluetoothlelib.resolvers.GattAttributeResolver
import dev.alt236.bluetoothlelib.util.ByteUtils
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ext.ByteArrayExt.toCharString
import java.nio.charset.Charset

internal class View(
    activity: Activity,
) {
    private val resources = activity.resources
    private val mGattServicesList: ExpandableListView = activity.findViewById(R.id.gatt_services_list)
    private var mConnectionState: TextView = activity.findViewById(R.id.connection_state)
    private var mGattUUID: TextView = activity.findViewById(R.id.uuid)
    private var mGattUUIDDesc: TextView = activity.findViewById(R.id.description)
    private var mDataAsString: TextView = activity.findViewById(R.id.data_as_string)
    private var mDataAsArray: TextView = activity.findViewById(R.id.data_as_array)
    private var mDataAsChars: TextView = activity.findViewById(R.id.data_as_characters)

    fun clearUi() {
        mGattServicesList.setAdapter(null as SimpleExpandableListAdapter?)
        mGattUUID.setText(R.string.no_data)
        mGattUUIDDesc.setText(R.string.no_data)
        mDataAsArray.setText(R.string.no_data)
        mDataAsString.setText(R.string.no_data)
        mDataAsChars.setText(R.string.no_data)
    }

    fun setConnectionState(state: State) {
        val colourId: Int
        val resId: Int

        when (state) {
            State.CONNECTED -> {
                colourId = android.R.color.holo_green_dark
                resId = R.string.connected
            }
            State.DISCONNECTED -> {
                colourId = android.R.color.holo_red_dark
                resId = R.string.disconnected
            }
            State.CONNECTING -> {
                colourId = android.R.color.holo_orange_dark
                resId = R.string.connecting
            }
        }

        mConnectionState.setText(resId)
        mConnectionState.setTextColor(ResourcesCompat.getColor(resources, colourId, null))
    }

    fun setGattUuid(uuid: String?) {
        mGattUUID.text = uuid ?: resources.getString(R.string.no_data)
        mGattUUIDDesc.text = GattAttributeResolver.getAttributeName(uuid, resources.getString(R.string.unknown))
    }

    fun setData(bytes: ByteArray?) {
        val safeBytes = bytes ?: ByteArray(0)

        mDataAsArray.text = quoteString(ByteUtils.byteArrayToHexString(safeBytes))
        mDataAsString.text = quoteString(safeBytes.toString(Charset.forName("UTF-8")))
        mDataAsChars.text = quoteString(safeBytes.toCharString())
    }

    fun setListAdapter(adapter: SimpleExpandableListAdapter) {
        mGattServicesList.setAdapter(adapter)
    }

    fun setListClickListener(listener: ExpandableListView.OnChildClickListener) {
        mGattServicesList.setOnChildClickListener(listener)
    }

    private fun quoteString(string: String): String = resources.getString(R.string.formatter_single_quoted_string, string)
}
