package de.seniorlaguna.qrcodescanner.feature.tabs.settings.permissions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import de.seniorlaguna.qrcodescanner.databinding.ActivityAllPermissionsBinding
import de.seniorlaguna.qrcodescanner.extension.applySystemWindowInsets
import de.seniorlaguna.qrcodescanner.feature.BaseActivity

class AllPermissionsActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AllPermissionsActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding : ActivityAllPermissionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }
}