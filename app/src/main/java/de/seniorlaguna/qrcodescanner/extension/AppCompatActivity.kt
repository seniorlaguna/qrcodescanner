package de.seniorlaguna.qrcodescanner.extension

import androidx.appcompat.app.AppCompatActivity
import de.seniorlaguna.qrcodescanner.feature.common.dialog.ErrorDialogFragment

fun AppCompatActivity.showError(error: Throwable?) {
    val errorDialog =
        ErrorDialogFragment.newInstance(
            this,
            error
        )
    errorDialog.show(supportFragmentManager, "")
}