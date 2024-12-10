package uk.co.alt236.btlescan.ui.details

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alt236.bluetoothlelib.device.BluetoothLeDevice
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.co.alt236.btlescan.app.ui.view.details.model.DetailsScreenItems
import uk.co.alt236.btlescan.arch.coroutines.DispatcherProvider
import uk.co.alt236.btlescan.arch.viewmodel.BaseViewModel
import uk.co.alt236.btlescan.ui.details.mapper.DetailsUiMapper
import javax.inject.Inject

@HiltViewModel
internal class DeviceDetailsViewModel
    @Inject
    constructor(
        private val dispatcherProvider: DispatcherProvider,
        private val mapper: DetailsUiMapper,
    ) : BaseViewModel<Action, UiState>(UiState.Loading) {
        override fun perform(action: Action) {
            viewModelScope.launch {
                when (action) {
                    is Action.ShowDeviceDetails -> showDeviceDetails(action)
                }
            }
        }

        private suspend fun showDeviceDetails(action: Action.ShowDeviceDetails) {
            withContext(dispatcherProvider.io()) {
                val items = mapper.map(action.device)
                _uiState.emit(UiState.ShowData(items))
            }
        }
    }

sealed interface UiState {
    data object Loading : UiState

    data class ShowData(
        val uiItems: List<DetailsScreenItems>,
    ) : UiState
}

sealed interface Action {
    data class ShowDeviceDetails(
        val device: BluetoothLeDevice?,
    ) : Action
}
