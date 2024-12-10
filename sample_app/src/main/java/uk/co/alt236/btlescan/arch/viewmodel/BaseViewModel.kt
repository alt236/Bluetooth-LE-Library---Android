package uk.co.alt236.btlescan.arch.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<ACTION, STATE>(
    initialState: STATE,
) : ViewModel() {
    @Suppress("PropertyName")
    protected val _uiState = MutableStateFlow(initialState)

    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    abstract fun perform(action: ACTION)
}
