package de.seniorlaguna.qrcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.seniorlaguna.barcodescanner.databinding.FragmentCreateQrCodeMecardBinding
import de.seniorlaguna.qrcodescanner.extension.textString
import de.seniorlaguna.qrcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import de.seniorlaguna.qrcodescanner.model.Contact
import de.seniorlaguna.qrcodescanner.model.schema.MeCard
import de.seniorlaguna.qrcodescanner.model.schema.Schema

class CreateQrCodeMeCardFragment : BaseCreateBarcodeFragment() {

    private var binding : FragmentCreateQrCodeMecardBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateQrCodeMecardBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.editTextFirstName.requestFocus()
        parentActivity.isCreateBarcodeButtonEnabled = true
    }

    override fun getBarcodeSchema(): Schema {
        return MeCard(
            firstName = binding!!.editTextFirstName.textString,
            lastName = binding!!.editTextLastName.textString,
            email = binding!!.editTextEmail.textString,
            phone = binding!!.editTextPhone.textString
        )
    }

    override fun showContact(contact: Contact) {
        binding!!.editTextFirstName.setText(contact.firstName)
        binding!!.editTextLastName.setText(contact.lastName)
        binding!!.editTextEmail.setText(contact.email)
        binding!!.editTextPhone.setText(contact.phone)
    }
}