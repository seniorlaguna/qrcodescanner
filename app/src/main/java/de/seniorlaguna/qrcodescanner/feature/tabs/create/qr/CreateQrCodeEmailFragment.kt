package de.seniorlaguna.qrcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import de.seniorlaguna.barcodescanner.databinding.FragmentCreateQrCodeEmailBinding
import de.seniorlaguna.qrcodescanner.extension.isNotBlank
import de.seniorlaguna.qrcodescanner.extension.textString
import de.seniorlaguna.qrcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import de.seniorlaguna.qrcodescanner.model.schema.Email
import de.seniorlaguna.qrcodescanner.model.schema.Schema

class CreateQrCodeEmailFragment : BaseCreateBarcodeFragment() {

    private var binding : FragmentCreateQrCodeEmailBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateQrCodeEmailBinding.inflate(inflater, container, false)
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
        return Email(
            email = binding!!.editTextEmail.textString,
            subject = binding!!.editTextSubject.textString,
            body = binding!!.editTextMessage.textString
        )
    }

    private fun initTitleEditText() {
        binding!!.editTextEmail.requestFocus()
    }

    private fun handleTextChanged() {
        binding!!.editTextEmail.addTextChangedListener { toggleCreateBarcodeButton() }
        binding!!.editTextSubject.addTextChangedListener { toggleCreateBarcodeButton() }
        binding!!.editTextMessage.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = binding!!.editTextEmail.isNotBlank() || binding!!.editTextSubject.isNotBlank() || binding!!.editTextMessage.isNotBlank()
    }
}