package de.seniorlaguna.qrcodescanner.extension

fun Double?.orZero(): Double {
    return this ?: 0.0
}