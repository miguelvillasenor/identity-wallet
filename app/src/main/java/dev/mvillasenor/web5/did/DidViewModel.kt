package dev.mvillasenor.web5.did

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mvillasenor.ssi_sdk.DidService
import dev.mvillasenor.ssi_sdk.models.DidKey
import dev.mvillasenor.ssi_sdk.models.KeyType
import dev.mvillasenor.storage.WalletStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DidViewModel @Inject constructor(private val walletStore: WalletStore) : ViewModel() {

    private val didService = DidService()
    private val _stateFlow =
        MutableStateFlow(ViewState(supportedKeyTypes = didService.getSupportedKeyTypes()))
    val stateFlow: StateFlow<ViewState> = _stateFlow

    init {
        viewModelScope.launch {
            walletStore.observeDidKeys().collect { dids ->
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
            val didKey = didService.generateDidKey(keyType)
            viewModelScope.launch {
                walletStore.storeDidKey(didKey)
            }
        }
        onDismissDropdown()
    }

    data class ViewState(
        val dropdownOpened: Boolean = false,
        val supportedKeyTypes: List<KeyType> = emptyList(),
        val dids: List<DidKey> = emptyList()
    )
}