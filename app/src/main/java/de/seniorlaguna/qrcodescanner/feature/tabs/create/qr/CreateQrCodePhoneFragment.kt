package de.seniorlaguna.qrcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import de.seniorlaguna.barcodescanner.databinding.FragmentCreateQrCodePhoneBinding
import de.seniorlaguna.qrcodescanner.extension.isNotBlank
import de.seniorlaguna.qrcodescanner.extension.textString
import de.seniorlaguna.qrcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import de.seniorlaguna.qrcodescanner.model.schema.Phone
import de.seniorlaguna.qrcodescanner.model.schema.Schema

class CreateQrCodePhoneFragment : BaseCreateBarcodeFragment() {

    private var binding : FragmentCreateQrCodePhoneBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateQrCodePhoneBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditText()
        handleTextChanged()
    }

    override fun showPhone(phone: String) {
        binding!!.editText.apply {
            setText(phone)
            setSelection(phone.length)
        }
    }

    override fun getBarcodeSchema(): Schema {
        return Phone(binding!!.editText.textString)
    }

    private fun initEditText() {
        binding!!.editText.requestFocus()
    }

    private fun handleTextChanged() {
        binding!!.editText.addTextChangedListener {
            parentActivity.isCreateBarcodeButtonEnabled = binding!!.editText.isNotBlank()
        }
    }
}