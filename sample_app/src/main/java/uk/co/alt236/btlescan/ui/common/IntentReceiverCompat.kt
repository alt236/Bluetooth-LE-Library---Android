package uk.co.alt236.btlescan.ui.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Build

object IntentReceiverCompat {
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @JvmStatic
    fun registerExportedReceiver(
        activity: Activity,
        receiver: BroadcastReceiver,
        filter: IntentFilter,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.registerReceiver(
                receiver,
                filter,
                Context.RECEIVER_EXPORTED
            )
        } else {
            activity.registerExportUnawareReceiver(receiver, filter)
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun Activity.registerExportUnawareReceiver(
        receiver: BroadcastReceiver,
        filter: IntentFilter,
    ) {
        this.registerReceiver(
            receiver,
            filter
        )
    }
}