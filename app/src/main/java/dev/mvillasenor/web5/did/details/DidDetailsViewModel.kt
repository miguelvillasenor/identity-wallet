package dev.mvillasenor.web5.did.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mvillasenor.ssi_sdk.DidService
import dev.mvillasenor.ssi_sdk.expand
import dev.mvillasenor.ssi_sdk.models.DidDocument
import dev.mvillasenor.ssi_sdk.models.DidKey
import dev.mvillasenor.storage.WalletStore
import dev.mvillasenor.storage.db.RoomWalletStore
import dev.mvillasenor.web5.did.DidsRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DidDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val walletStore: WalletStore,
) : ViewModel() {

    private val did: String = checkNotNull(savedStateHandle[DidsRoutes.DID_PARAM])
    private val _stateFlow =
        MutableStateFlow(ViewState())
    val stateFlow: StateFlow<ViewState> = _stateFlow

    init {
        viewModelScope.launch {
            val didKey = walletStore.getDidKey(did)
            _stateFlow.update {
                it.copy(didKey = didKey, didDocument = didKey.expand())
            }
        }
    }

    data class ViewState(
        val didKey: DidKey? = null,
        val didDocument: DidDocument? = null
    )
}