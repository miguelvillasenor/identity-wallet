package dev.mvillasenor.ssi_sdk.models

import android.util.Base64
import ssi.DIDKeyWrapper


data class DidKey(
    val didKey: String,
    val base64privateJWK: String,
)

fun DIDKeyWrapper.toDidKey() = DidKey(
    didKey = this.didKey,
    base64privateJWK = Base64.encodeToString(this.privateJSONWebKey, Base64.DEFAULT)
)