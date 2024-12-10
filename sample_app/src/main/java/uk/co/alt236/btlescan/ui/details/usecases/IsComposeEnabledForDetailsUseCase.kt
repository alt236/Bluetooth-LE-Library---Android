package uk.co.alt236.btlescan.ui.details.usecases

import uk.co.alt236.btlescan.BuildConfig
import uk.co.alt236.btlescan.di.markers.DevExperiment
import javax.inject.Inject

internal class IsComposeEnabledForDetailsUseCase
    @Inject
    constructor() : DevExperiment {
        override operator fun invoke() = BuildConfig.DEVICE_DETAILS_COMPOSE
    }
