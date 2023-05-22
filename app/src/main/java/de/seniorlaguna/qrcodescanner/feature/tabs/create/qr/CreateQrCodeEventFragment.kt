package de.seniorlaguna.qrcodescanner.feature.tabs.create.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.seniorlaguna.barcodescanner.databinding.FragmentCreateQrCodeVeventBinding
import de.seniorlaguna.qrcodescanner.extension.textString
import de.seniorlaguna.qrcodescanner.feature.tabs.create.BaseCreateBarcodeFragment
import de.seniorlaguna.qrcodescanner.model.schema.Schema
import de.seniorlaguna.qrcodescanner.model.schema.VEvent

class CreateQrCodeEventFragment : BaseCreateBarcodeFragment() {

    private var binding : FragmentCreateQrCodeVeventBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateQrCodeVeventBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.editTextTitle.requestFocus()
        parentActivity.isCreateBarcodeButtonEnabled = true
    }

    override fun getBarcodeSchema(): Schema {
        return VEvent(
            uid = binding!!.editTextTitle.textString,
            organizer = binding!!.editTextOrganizer.textString,
            summary = binding!!.editTextSummary.textString,
            startDate = binding!!.buttonDateTimeStart.dateTime,
            endDate = binding!!.buttonDateTimeEnd.dateTime
        )
    }
}