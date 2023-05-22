package de.seniorlaguna.qrcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import de.seniorlaguna.qrcodescanner.databinding.FragmentCreateQrCodeSmsBinding
import de.seniorlaguna.qrcodescanner.extension.isNotBlank
import de.seniorlaguna.qrcodescanner.extension.textString
import de.seniorlaguna.qrcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import de.seniorlaguna.qrcodescanner.model.schema.Schema
import de.seniorlaguna.qrcodescanner.model.schema.Sms

class CreateQrCodeSmsFragment : BaseCreateBarcodeFragment() {

    private var binding : FragmentCreateQrCodeSmsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateQrCodeSmsBinding.inflate(inflater, container, false)
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

    override fun showPhone(phone: String) {
        binding!!.editTextPhone.apply {
            setText(phone)
            setSelection(phone.length)
        }
    }

    override fun getBarcodeSchema(): Schema {
       return Sms(
           phone = binding!!.editTextPhone.textString,
           message = binding!!.editTextMessage.textString
       )
    }

    private fun initTitleEditText() {
        binding!!.editTextPhone.requestFocus()
    }

    private fun handleTextChanged() {
        binding!!.editTextPhone.addTextChangedListener { toggleCreateBarcodeButton() }
        binding!!.editTextMessage.addTextChangedListener { toggleCreateBarcodeButton() }
    }

    private fun toggleCreateBarcodeButton() {
        parentActivity.isCreateBarcodeButtonEnabled = binding!!.editTextPhone.isNotBlank() || binding!!.editTextMessage.isNotBlank()
    }
}