package de.seniorlaguna.qrcodescanner

import androidx.multidex.MultiDexApplication
import de.seniorlaguna.qrcodescanner.di.settings

class App : MultiDexApplication() {

    override fun onCreate() {
        applyTheme()
        super.onCreate()
    }

    private fun applyTheme() {
        settings.reapplyTheme()
    }

}