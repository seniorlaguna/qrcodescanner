package de.seniorlaguna.qrcodescanner.feature.tabs.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.seniorlaguna.qrcodescanner.databinding.FragmentCreateBarcodeBinding
import de.seniorlaguna.qrcodescanner.extension.applySystemWindowInsets
import de.seniorlaguna.qrcodescanner.extension.clipboardManager
import de.seniorlaguna.qrcodescanner.extension.orZero
import de.seniorlaguna.qrcodescanner.feature.tabs.create.barcode.CreateBarcodeAllActivity
import de.seniorlaguna.qrcodescanner.feature.tabs.create.qr.CreateQrCodeAllActivity
import de.seniorlaguna.qrcodescanner.model.schema.BarcodeSchema
import com.google.zxing.BarcodeFormat

class CreateBarcodeFragment : Fragment() {

    private var binding : FragmentCreateBarcodeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateBarcodeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportEdgeToEdge()
        handleButtonsClicked()
    }

    private fun supportEdgeToEdge() {
        binding!!.appBarLayout.applySystemWindowInsets(applyTop = true)
    }

    private fun handleButtonsClicked() {
        // QR code
        binding!!.buttonClipboard.setOnClickListener {
            CreateBarcodeActivity.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.OTHER,
                getClipboardContent()
            )
        }
        binding!!.buttonText.setOnClickListener {
            CreateBarcodeActivity.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.OTHER
            )
        }
        binding!!.buttonUrl.setOnClickListener {
            CreateBarcodeActivity.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.URL
            )
        }
        binding!!.buttonWifi.setOnClickListener {
            CreateBarcodeActivity.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.WIFI
            )
        }
        binding!!.buttonLocation.setOnClickListener {
            CreateBarcodeActivity.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.GEO
            )
        }
        binding!!.buttonContactVcard.setOnClickListener {
            CreateBarcodeActivity.start(
                requireActivity(),
                BarcodeFormat.QR_CODE,
                BarcodeSchema.VCARD
            )
        }
        binding!!.buttonShowAllQrCode.setOnClickListener { CreateQrCodeAllActivity.start(requireActivity()) }

        // Barcode
        binding!!.buttonCreateBarcode.setOnClickListener { CreateBarcodeAllActivity.start(requireActivity()) }
    }

    private fun getClipboardContent(): String {
        val clip = requireActivity().clipboardManager?.primaryClip ?: return ""
        return when (clip.itemCount.orZero()) {
            0 -> ""
            else -> clip.getItemAt(0).text.toString()
        }
    }
}