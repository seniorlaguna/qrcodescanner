package de.seniorlaguna.qrcodescanner.extension

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}