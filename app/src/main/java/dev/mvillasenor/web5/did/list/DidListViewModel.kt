package dev.mvillasenor.web5.did.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mvillasenor.ssi_sdk.DidService
import dev.mvillasenor.ssi_sdk.models.KeyType
import dev.mvillasenor.storage.WalletStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DidListViewModel @Inject constructor(private val walletStore: WalletStore) : ViewModel() {

    private val didService = DidService()
    private val _stateFlow =
        MutableStateFlow(ViewState(supportedKeyTypes = didService.getSupportedKeyTypes()))
    val stateFlow: StateFlow<ViewState> = _stateFlow

    init {
        viewModelScope.launch {
            walletStore.observeDids().collect { dids ->
                _stateFlow.update { it.copy(dids = dids) }
            }
        }

    }

    fun onShowDropdown() {
        _stateFlow.update {
            it.copy(dropdownOpened = true)
        }
    }

    fun onDismissDropdown() {
        _stateFlow.update {
            it.copy(dropdownOpened = false)
        }
    }

    fun generateIdentity(keyType: KeyType) {
        if (didService.isSupportedKeyType(keyType)) {
            viewModelScope.launch {
                didService.generateDidKey(keyType).fold(
                    onSuccess = { walletStore.storeDidKey(it) },
                    onFailure = { error -> _stateFlow.update { it.copy(errorMessage = error.localizedMessage) } }
                )
            }
        }
        onDismissDropdown()
    }

    fun onErrorDismissed() {
        _stateFlow.update { it.copy(errorMessage = null) }
    }

    data class ViewState(
        val dropdownOpened: Boolean = false,
        val supportedKeyTypes: List<KeyType> = emptyList(),
        val dids: List<String> = emptyList(),
        val errorMessage: String? = null,
    )
}