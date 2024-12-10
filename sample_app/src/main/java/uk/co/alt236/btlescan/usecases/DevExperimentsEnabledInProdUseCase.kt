package uk.co.alt236.btlescan.usecases

import uk.co.alt236.btlescan.di.markers.DevExperiment
import javax.inject.Inject

class DevExperimentsEnabledInProdUseCase
    @Inject
    constructor(
        private val experiments: Set<@JvmSuppressWildcards DevExperiment>,
    ) {
        operator fun invoke() = experiments.filter { it.invoke() }
    }
