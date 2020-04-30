package uk.co.alt236.btlescan.services

import android.os.Binder

class LocalBinder(val service: BluetoothLeService) : Binder()