package dev.mvillasenor.ssi_sdk

import dev.mvillasenor.ssi_sdk.models.DidDocument
import dev.mvillasenor.ssi_sdk.models.DidKey
import dev.mvillasenor.ssi_sdk.models.toStringList
import ssi.Ssi

fun DidKey.expand(): DidDocument {
    val document = Ssi.expandDIDKey(didKey)
    return DidDocument(
        context = document.context.toStringList(),
        id = document.id,
        controller = document.controller,
        alsoKnownAs = document.alsoKnownAs
    )
}