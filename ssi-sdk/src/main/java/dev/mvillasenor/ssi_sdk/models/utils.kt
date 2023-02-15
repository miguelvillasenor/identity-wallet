package dev.mvillasenor.ssi_sdk.models

import ssi.StringArray

fun StringArray.toStringList(): List<String> {
    val list = mutableListOf<String>()
    for (index in 0 until this.size()) {
        this[index].let {
            list.add(it)
        }
    }
    return list.toList()
}