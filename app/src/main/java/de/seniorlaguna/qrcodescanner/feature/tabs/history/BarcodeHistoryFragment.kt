package de.seniorlaguna.qrcodescanner.feature.tabs.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.seniorlaguna.barcodescanner.R
import de.seniorlaguna.barcodescanner.databinding.FragmentBarcodeHistoryBinding
import de.seniorlaguna.qrcodescanner.di.barcodeDatabase
import de.seniorlaguna.qrcodescanner.extension.applySystemWindowInsets
import de.seniorlaguna.qrcodescanner.extension.showError
import de.seniorlaguna.qrcodescanner.feature.common.dialog.DeleteConfirmationDialogFragment
import de.seniorlaguna.qrcodescanner.feature.tabs.history.export.ExportHistoryActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


class BarcodeHistoryFragment : Fragment(), DeleteConfirmationDialogFragment.Listener {
    private val disposable = CompositeDisposable()

    private var binding : FragmentBarcodeHistoryBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBarcodeHistoryBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportEdgeToEdge()
        initTabs()
        handleMenuClicked()
    }

    override fun onDeleteConfirmed() {
        clearHistory()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
        disposable.clear()
    }

    private fun supportEdgeToEdge() {
        binding!!.appBarLayout.applySystemWindowInsets(applyTop = true)
    }

    private fun initTabs() {
        binding!!.viewPager.adapter = BarcodeHistoryViewPagerAdapter(requireContext(), childFragmentManager)
        binding!!.tabLayout.setupWithViewPager(binding!!.viewPager)
    }

    private fun handleMenuClicked() {
        binding!!.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_export_history -> navigateToExportHistoryScreen()
                R.id.item_clear_history -> showDeleteHistoryConfirmationDialog()
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun navigateToExportHistoryScreen() {
        ExportHistoryActivity.start(requireActivity())
    }

    private fun showDeleteHistoryConfirmationDialog() {
        val dialog = DeleteConfirmationDialogFragment.newInstance(R.string.dialog_delete_clear_history_message)
        dialog.show(childFragmentManager, "")
    }

    private fun clearHistory() {
        barcodeDatabase.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { },
                ::showError
            )
            .addTo(disposable)
    }
}