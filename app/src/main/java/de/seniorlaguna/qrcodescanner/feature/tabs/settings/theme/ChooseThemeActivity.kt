package de.seniorlaguna.qrcodescanner.feature.tabs.settings.theme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import de.seniorlaguna.qrcodescanner.databinding.ActivityChooseThemeBinding
import de.seniorlaguna.qrcodescanner.di.settings
import de.seniorlaguna.qrcodescanner.extension.applySystemWindowInsets
import de.seniorlaguna.qrcodescanner.extension.unsafeLazy
import de.seniorlaguna.qrcodescanner.feature.BaseActivity
import de.seniorlaguna.qrcodescanner.usecase.Settings

class ChooseThemeActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ChooseThemeActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val buttons by unsafeLazy {
        listOf(binding.buttonSystemTheme, binding.buttonLightTheme, binding.buttonDarkTheme)
    }

    private lateinit var binding : ActivityChooseThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportEdgeToEdge()
        initToolbar()
    }

    override fun onResume() {
        super.onResume()
        showInitialSettings()
        handleSettingsChanged()
    }

    private fun supportEdgeToEdge() {
        binding.rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun showInitialSettings() {
        val theme = settings.theme
        binding.buttonSystemTheme.isChecked = theme == Settings.THEME_SYSTEM
        binding.buttonLightTheme.isChecked = theme == Settings.THEME_LIGHT
        binding.buttonDarkTheme.isChecked = theme == Settings.THEME_DARK
    }

    private fun handleSettingsChanged() {
        binding.buttonSystemTheme.setCheckedChangedListener { isChecked ->
            if (isChecked.not()) {
                return@setCheckedChangedListener
            }

            uncheckOtherButtons(binding.buttonSystemTheme)
            settings.theme = Settings.THEME_SYSTEM
        }

        binding.buttonLightTheme.setCheckedChangedListener { isChecked ->
            if (isChecked.not()) {
                return@setCheckedChangedListener
            }

            uncheckOtherButtons(binding.buttonLightTheme)
            settings.theme = Settings.THEME_LIGHT
        }

        binding.buttonDarkTheme.setCheckedChangedListener { isChecked ->
            if (isChecked.not()) {
                return@setCheckedChangedListener
            }

            uncheckOtherButtons(binding.buttonDarkTheme)
            settings.theme = Settings.THEME_DARK
        }
    }

    private fun uncheckOtherButtons(checkedButton: View) {
        buttons.forEach { button ->
            if (checkedButton !== button) {
                button.isChecked = false
            }
        }
    }
}