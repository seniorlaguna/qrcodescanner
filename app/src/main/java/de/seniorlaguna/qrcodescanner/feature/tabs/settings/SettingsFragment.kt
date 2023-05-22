package de.seniorlaguna.qrcodescanner.feature.tabs.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.seniorlaguna.barcodescanner.R
import de.seniorlaguna.barcodescanner.databinding.FragmentSettingsBinding
import de.seniorlaguna.qrcodescanner.di.barcodeDatabase
import de.seniorlaguna.qrcodescanner.di.settings
import de.seniorlaguna.qrcodescanner.extension.applySystemWindowInsets
import de.seniorlaguna.qrcodescanner.extension.packageManager
import de.seniorlaguna.qrcodescanner.extension.showError
import de.seniorlaguna.qrcodescanner.feature.common.dialog.DeleteConfirmationDialogFragment
import de.seniorlaguna.qrcodescanner.feature.tabs.settings.camera.ChooseCameraActivity
import de.seniorlaguna.qrcodescanner.feature.tabs.settings.formats.SupportedFormatsActivity
import de.seniorlaguna.qrcodescanner.feature.tabs.settings.permissions.AllPermissionsActivity
import de.seniorlaguna.qrcodescanner.feature.tabs.settings.search.ChooseSearchEngineActivity
import de.seniorlaguna.qrcodescanner.feature.tabs.settings.theme.ChooseThemeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


class SettingsFragment : Fragment(), DeleteConfirmationDialogFragment.Listener {
    private val disposable = CompositeDisposable()

    private var binding : FragmentSettingsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportEdgeToEdge()
    }

    override fun onResume() {
        super.onResume()
        handleButtonCheckedChanged()
        handleButtonClicks()
        showSettings()
        showAppVersion()
    }

    override fun onDeleteConfirmed() {
        clearHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
        binding = null
    }

    fun supportEdgeToEdge() {
        binding!!.appBarLayout.applySystemWindowInsets(applyTop = true)
    }

    private fun handleButtonCheckedChanged() {
        binding!!.buttonInverseBarcodeColorsInDarkTheme.setCheckedChangedListener { settings.areBarcodeColorsInversed = it }
        binding!!.buttonOpenLinksAutomatically.setCheckedChangedListener { settings.openLinksAutomatically = it }
        binding!!.buttonCopyToClipboard.setCheckedChangedListener { settings.copyToClipboard = it }
        binding!!.buttonSimpleAutoFocus.setCheckedChangedListener { settings.simpleAutoFocus = it }
        binding!!.buttonFlashlight.setCheckedChangedListener { settings.flash = it }
        binding!!.buttonVibrate.setCheckedChangedListener { settings.vibrate = it }
        binding!!.buttonContinuousScanning.setCheckedChangedListener { settings.continuousScanning = it }
        binding!!.buttonConfirmScansManually.setCheckedChangedListener { settings.confirmScansManually = it }
        binding!!.buttonSaveScannedBarcodes.setCheckedChangedListener { settings.saveScannedBarcodesToHistory = it }
        binding!!.buttonSaveCreatedBarcodes.setCheckedChangedListener { settings.saveCreatedBarcodesToHistory = it }
        binding!!.buttonDoNotSaveDuplicates.setCheckedChangedListener { settings.doNotSaveDuplicates = it }
    }

    private fun handleButtonClicks() {
        binding!!.buttonChooseTheme.setOnClickListener { ChooseThemeActivity.start(requireActivity()) }
        binding!!.buttonChooseCamera.setOnClickListener { ChooseCameraActivity.start(requireActivity()) }
        binding!!.buttonSelectSupportedFormats.setOnClickListener { SupportedFormatsActivity.start(requireActivity()) }
        binding!!.buttonClearHistory.setOnClickListener { showDeleteHistoryConfirmationDialog() }
        binding!!.buttonChooseSearchEngine.setOnClickListener { ChooseSearchEngineActivity.start(requireContext()) }
        binding!!.buttonPermissions.setOnClickListener { AllPermissionsActivity.start(requireActivity()) }
        binding!!.buttonCheckUpdates.setOnClickListener { showAppInMarket() }
        binding!!.buttonSourceCode.setOnClickListener { showSourceCode() }
    }

    private fun clearHistory() {
        binding!!.buttonClearHistory.isEnabled = false

        barcodeDatabase.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    binding!!.buttonClearHistory.isEnabled = true
                },
                { error ->
                    binding!!.buttonClearHistory.isEnabled = true
                    showError(error)
                }
            )
            .addTo(disposable)
    }

    private fun showSettings() {
        settings.apply {
            binding!!.buttonInverseBarcodeColorsInDarkTheme.isChecked = areBarcodeColorsInversed
            binding!!.buttonOpenLinksAutomatically.isChecked = openLinksAutomatically
            binding!!.buttonCopyToClipboard.isChecked = copyToClipboard
            binding!!.buttonSimpleAutoFocus.isChecked = simpleAutoFocus
            binding!!.buttonFlashlight.isChecked = flash
            binding!!.buttonVibrate.isChecked = vibrate
            binding!!.buttonContinuousScanning.isChecked = continuousScanning
            binding!!.buttonConfirmScansManually.isChecked = confirmScansManually
            binding!!.buttonSaveScannedBarcodes.isChecked = saveScannedBarcodesToHistory
            binding!!.buttonSaveCreatedBarcodes.isChecked = saveCreatedBarcodesToHistory
            binding!!.buttonDoNotSaveDuplicates.isChecked = doNotSaveDuplicates
        }
    }

    private fun showDeleteHistoryConfirmationDialog() {
        val dialog = DeleteConfirmationDialogFragment.newInstance(R.string.dialog_delete_clear_history_message)
        dialog.show(childFragmentManager, "")
    }

    private fun showAppInMarket() {
        val uri = Uri.parse("market://details?id=" + requireContext().packageName)
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun showSourceCode() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/wewewe718/QrAndBarcodeScanner"))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun showAppVersion() {
        binding!!.buttonAppVersion.hint = "1.0"
    }
}