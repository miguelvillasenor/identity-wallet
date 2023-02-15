package dev.mvillasenor.ssi_sdk.models

import ssi.DIDDocumentMobile

// TODO: add remaining fields once the mobile sdk supports them
data class DidDocument(
    val context: List<String>,
    val id: String,
    val controller: String,
    val alsoKnownAs: String,
)

internal fun DIDDocumentMobile.toDidDocument(): DidDocument = DidDocument(
    context.toStringList(),
    id = id,
    controller = controller,
    alsoKnownAs = alsoKnownAs
)