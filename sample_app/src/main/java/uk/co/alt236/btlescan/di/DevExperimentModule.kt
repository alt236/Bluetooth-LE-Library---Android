package uk.co.alt236.btlescan.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import uk.co.alt236.btlescan.di.markers.DevExperiment
import uk.co.alt236.btlescan.ui.details.usecases.IsComposeEnabledForDetailsUseCase

@Module
@InstallIn(SingletonComponent::class)
internal interface DevExperimentModule {
    @Binds
    @IntoSet
    fun isComposeEnabledForDetailsUseCase(useCase: IsComposeEnabledForDetailsUseCase): DevExperiment
}
