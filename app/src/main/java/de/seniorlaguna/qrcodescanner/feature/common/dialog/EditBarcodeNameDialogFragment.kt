package de.seniorlaguna.qrcodescanner.feature.common.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import de.seniorlaguna.qrcodescanner.R
import de.seniorlaguna.qrcodescanner.databinding.DialogEditBarcodeNameBinding

class EditBarcodeNameDialogFragment : DialogFragment() {

    interface Listener {
        fun onNameConfirmed(name: String)
    }

    companion object {
        private const val NAME_KEY = "NAME_KEY"

        fun newInstance(name: String?): EditBarcodeNameDialogFragment {
            return EditBarcodeNameDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_KEY, name)
                }
            }
        }
    }

    private var binding : DialogEditBarcodeNameBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogEditBarcodeNameBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = requireActivity() as? Listener
        val name = arguments?.getString(NAME_KEY).orEmpty()

        val dialog = AlertDialog.Builder(requireActivity(), R.style.DialogTheme)
            .setTitle(R.string.dialog_edit_barcode_name_title)
            .setView(view)
            .setPositiveButton(R.string.dialog_edit_barcode_name_positive_button) { _, _ ->
                val newName = binding!!.editTextBarcodeName.text.toString()
                listener?.onNameConfirmed(newName)
            }
            .setNegativeButton(R.string.dialog_edit_barcode_name_negative_button, null)
            .create()

        dialog.setOnShowListener {
            initNameEditText(binding!!.editTextBarcodeName, name)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }

        return dialog
    }

    private fun initNameEditText(editText: EditText, name: String) {
        editText.apply {
            setText(name)
            setSelection(name.length)
            requestFocus()
        }

        val manager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        manager?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}