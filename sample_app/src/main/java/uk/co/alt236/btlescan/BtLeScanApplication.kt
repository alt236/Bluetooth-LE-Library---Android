package uk.co.alt236.btlescan

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import uk.co.alt236.btlescan.usecases.DevExperimentsEnabledInProdUseCase
import javax.inject.Inject

@HiltAndroidApp
class BtLeScanApplication : Application() {
    @Inject
    internal lateinit var devExperimentsEnabled: DevExperimentsEnabledInProdUseCase

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            validateNoDevExperimentsInProd()
        }
    }

    private fun validateNoDevExperimentsInProd() {
        val enabled = devExperimentsEnabled()
        if (enabled.isNotEmpty()) {
            error("The following experiments are should not be enabled in prod: $enabled")
        }
    }
}
