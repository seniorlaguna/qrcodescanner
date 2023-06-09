package de.seniorlaguna.qrcodescanner.feature.tabs.history.export

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import de.seniorlaguna.qrcodescanner.R
import de.seniorlaguna.qrcodescanner.databinding.ActivityExportHistoryBinding
import de.seniorlaguna.qrcodescanner.di.barcodeDatabase
import de.seniorlaguna.qrcodescanner.di.barcodeSaver
import de.seniorlaguna.qrcodescanner.di.permissionsHelper
import de.seniorlaguna.qrcodescanner.extension.applySystemWindowInsets
import de.seniorlaguna.qrcodescanner.extension.isNotBlank
import de.seniorlaguna.qrcodescanner.extension.showError
import de.seniorlaguna.qrcodescanner.extension.textString
import de.seniorlaguna.qrcodescanner.feature.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class ExportHistoryActivity : BaseActivity() {
    private val disposable = CompositeDisposable()

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 101
        private val PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        fun start(context: Context) {
            val intent = Intent(context, ExportHistoryActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding : ActivityExportHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExportHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportEdgeToEdge()
        initToolbar()
        initExportTypeSpinner()
        initFileNameEditText()
        initExportButton()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionsHelper.areAllPermissionsGranted(grantResults)) {
            exportHistory()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun supportEdgeToEdge() {
        binding.rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initExportTypeSpinner() {
        binding.spinnerExportAs.adapter = ArrayAdapter.createFromResource(
            this, R.array.activity_export_history_types, R.layout.item_spinner
        ).apply {
            setDropDownViewResource(R.layout.item_spinner_dropdown)
        }
    }

    private fun initFileNameEditText() {
        binding.editTextFileName.addTextChangedListener {
            binding.buttonExport.isEnabled = binding.editTextFileName.isNotBlank()
        }
    }

    private fun initExportButton() {
        binding.buttonExport.setOnClickListener {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        permissionsHelper.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS_CODE)
    }

    private fun exportHistory() {
        val fileName = binding.editTextFileName.textString
        val saveFunc = when (binding.spinnerExportAs.selectedItemPosition) {
            0 -> barcodeSaver::saveBarcodeHistoryAsCsv
            1 -> barcodeSaver::saveBarcodeHistoryAsJson
            else -> return
        }

        showLoading(true)

        barcodeDatabase
            .getAllForExport()
            .flatMapCompletable { barcodes ->
                saveFunc(this, fileName, barcodes)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    showHistoryExported()
                },
                { error ->
                    showLoading(false)
                    showError(error)
                }
            )
            .addTo(disposable)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLoading.isVisible = isLoading
        binding.scrollView.isVisible = isLoading.not()
    }

    private fun showHistoryExported() {
        Toast.makeText(this, R.string.activity_export_history_exported, Toast.LENGTH_LONG).show()
        finish()
    }
}