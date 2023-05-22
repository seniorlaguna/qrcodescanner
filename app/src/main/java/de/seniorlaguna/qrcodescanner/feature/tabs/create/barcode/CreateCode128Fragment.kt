package de.seniorlaguna.qrcodescanner.feature.tabs.create.barcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import de.seniorlaguna.barcodescanner.databinding.FragmentCreateCode128Binding
import de.seniorlaguna.qrcodescanner.extension.isNotBlank
import de.seniorlaguna.qrcodescanner.extension.textString
import de.seniorlaguna.qrcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import de.seniorlaguna.qrcodescanner.model.schema.Other
import de.seniorlaguna.qrcodescanner.model.schema.Schema

class CreateCode128Fragment : BaseCreateBarcodeFragment() {

    private var binding : FragmentCreateCode128Binding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateCode128Binding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.editText.requestFocus()
        binding!!.editText.addTextChangedListener {
            parentActivity.isCreateBarcodeButtonEnabled = binding!!.editText.isNotBlank()
        }
    }

    override fun getBarcodeSchema(): Schema {
        return Other(binding!!.editText.textString)
    }
}