package de.seniorlaguna.qrcodescanner.feature.tabs.settings.formats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import de.seniorlaguna.barcodescanner.databinding.ActivitySupportedFormatsBinding
import de.seniorlaguna.qrcodescanner.di.settings
import de.seniorlaguna.qrcodescanner.extension.applySystemWindowInsets
import de.seniorlaguna.qrcodescanner.extension.unsafeLazy
import de.seniorlaguna.qrcodescanner.feature.BaseActivity
import de.seniorlaguna.qrcodescanner.usecase.SupportedBarcodeFormats
import com.google.zxing.BarcodeFormat

class SupportedFormatsActivity : BaseActivity(), FormatsAdapter.Listener {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SupportedFormatsActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val formats by unsafeLazy { SupportedBarcodeFormats.FORMATS }
    private val formatSelection by unsafeLazy { formats.map(settings::isFormatSelected) }
    private val formatsAdapter by unsafeLazy { FormatsAdapter(this, formats, formatSelection) }

    private lateinit var binding : ActivitySupportedFormatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportedFormatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportEdgeToEdge()
        initRecyclerView()
        handleToolbarBackClicked()
    }

    override fun onFormatChecked(format: BarcodeFormat, isChecked: Boolean) {
        settings.setFormatSelected(format, isChecked)
    }

    private fun supportEdgeToEdge() {
        binding.rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun initRecyclerView() {
        binding.recyclerViewFormats.apply {
            layoutManager = LinearLayoutManager(this@SupportedFormatsActivity)
            adapter = formatsAdapter
        }
    }

    private fun handleToolbarBackClicked() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}