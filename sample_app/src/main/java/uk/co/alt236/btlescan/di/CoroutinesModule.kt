package uk.co.alt236.btlescan.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.alt236.btlescan.app.arch.compose.DispatcherProvider
import uk.co.alt236.btlescan.arch.coroutines.DefaultDispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {
    @Binds
    fun bindDispatcherProvider(defaultDispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}
