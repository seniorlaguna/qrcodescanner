package de.seniorlaguna.qrcodescanner.feature.tabs.create

import androidx.fragment.app.Fragment
import de.seniorlaguna.qrcodescanner.model.Contact
import de.seniorlaguna.qrcodescanner.model.schema.Other
import de.seniorlaguna.qrcodescanner.model.schema.Schema
import de.seniorlaguna.qrcodescanner.extension.unsafeLazy

abstract class BaseCreateBarcodeFragment : Fragment() {
    protected val parentActivity by unsafeLazy { requireActivity() as CreateBarcodeActivity }

    open val latitude: Double? = null
    open val longitude: Double? = null

    open fun getBarcodeSchema(): Schema = Other("")
    open fun showPhone(phone: String) {}
    open fun showContact(contact: Contact) {}
    open fun showLocation(latitude: Double?, longitude: Double?) {}
}