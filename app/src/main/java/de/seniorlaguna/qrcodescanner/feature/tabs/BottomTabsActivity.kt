package de.seniorlaguna.qrcodescanner.feature.tabs

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import de.seniorlaguna.barcodescanner.R
import de.seniorlaguna.barcodescanner.databinding.ActivityBottomTabsBinding
import de.seniorlaguna.qrcodescanner.extension.applySystemWindowInsets
import de.seniorlaguna.qrcodescanner.feature.BaseActivity
import de.seniorlaguna.qrcodescanner.feature.tabs.create.CreateBarcodeFragment
import de.seniorlaguna.qrcodescanner.feature.tabs.history.BarcodeHistoryFragment
import de.seniorlaguna.qrcodescanner.feature.tabs.scan.ScanBarcodeFromCameraFragment
import de.seniorlaguna.qrcodescanner.feature.tabs.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomTabsActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val ACTION_CREATE_BARCODE = "de.seniorlaguna.qrcodescanner.CREATE_BARCODE"
        private const val ACTION_HISTORY = "de.seniorlaguna.qrcodescanner.HISTORY"
    }

    lateinit var binding : ActivityBottomTabsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportEdgeToEdge()
        initBottomNavigationView()

        if (savedInstanceState == null) {
            showInitialFragment()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == binding.bottomNavigationView.selectedItemId) {
            return false
        }
        showFragment(item.itemId)
        return true
    }

    override fun onBackPressed() {
        if (binding.bottomNavigationView.selectedItemId == R.id.item_scan) {
            super.onBackPressed()
        } else {
            binding.bottomNavigationView.selectedItemId = R.id.item_scan
        }
    }

    private fun supportEdgeToEdge() {
        binding.bottomNavigationView.applySystemWindowInsets(applyBottom = true)
    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.apply {
            setOnNavigationItemSelectedListener(this@BottomTabsActivity)
        }
    }

    private fun showInitialFragment() {
        when (intent?.action) {
            ACTION_CREATE_BARCODE -> binding.bottomNavigationView.selectedItemId = R.id.item_create
            ACTION_HISTORY -> binding.bottomNavigationView.selectedItemId = R.id.item_history
            else -> showFragment(R.id.item_scan)
        }
    }

    private fun showFragment(bottomItemId: Int) {
        val fragment = when (bottomItemId) {
            R.id.item_scan -> ScanBarcodeFromCameraFragment()
            R.id.item_create -> CreateBarcodeFragment()
            R.id.item_history -> BarcodeHistoryFragment()
            R.id.item_settings -> SettingsFragment()
            else -> null
        }
        fragment?.apply(::replaceFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_fragment_container, fragment)
            .setReorderingAllowed(true)
            .commit()
    }
}