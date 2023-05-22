package de.seniorlaguna.qrcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import de.seniorlaguna.barcodescanner.databinding.FragmentCreateQrCodeUrlBinding
import de.seniorlaguna.qrcodescanner.extension.isNotBlank
import de.seniorlaguna.qrcodescanner.extension.textString
import de.seniorlaguna.qrcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import de.seniorlaguna.qrcodescanner.model.schema.Schema
import de.seniorlaguna.qrcodescanner.model.schema.Url

class CreateQrCodeUrlFragment : BaseCreateBarcodeFragment() {

    private var binding : FragmentCreateQrCodeUrlBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateQrCodeUrlBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUrlPrefix()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        return Url(binding!!.editText.textString)
    }

    private fun showUrlPrefix() {
        val prefix = "https://"
        binding!!.editText.apply {
            setText(prefix)
            setSelection(prefix.length)
            requestFocus()
        }
    }

    private fun handleTextChanged() {
        binding!!.editText.addTextChangedListener {
            parentActivity.isCreateBarcodeButtonEnabled = binding!!.editText.isNotBlank()
        }
    }
}