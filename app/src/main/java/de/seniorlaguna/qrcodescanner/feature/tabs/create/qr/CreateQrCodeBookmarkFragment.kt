package de.seniorlaguna.qrcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import de.seniorlaguna.barcodescanner.databinding.FragmentCreateQrCodeBookmarkBinding
import de.seniorlaguna.qrcodescanner.extension.isNotBlank
import de.seniorlaguna.qrcodescanner.extension.textString
import de.seniorlaguna.qrcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import de.seniorlaguna.qrcodescanner.model.schema.Bookmark
import de.seniorlaguna.qrcodescanner.model.schema.Schema

class CreateQrCodeBookmarkFragment : BaseCreateBarcodeFragment() {

    private var binding : FragmentCreateQrCodeBookmarkBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateQrCodeBookmarkBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleEditText()
        handleTextChanged()
    }

    override fun getBarcodeSchema(): Schema {
        return Bookmark(
            title = binding!!.editTextTitle.textString,
            url = binding!!.editTextUrl.textString
        )
    }

    private fun initTitleEditText() {
        binding!!.editTextTitle.requestFocus()
    }

    private fun handleTextChanged() {
        binding!!.editTextTitle.addTextChangedListener { toggleCreateBarcodeButton() }
        binding!!.editTextUrl.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = binding!!.editTextTitle.isNotBlank() || binding!!.editTextUrl.isNotBlank()
    }
}