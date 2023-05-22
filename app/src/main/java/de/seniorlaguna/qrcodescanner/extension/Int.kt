package de.seniorlaguna.qrcodescanner.extension

fun Int?.orZero(): Int {
    return this ?: 0
}