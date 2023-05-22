package de.seniorlaguna.qrcodescanner.feature.tabs.settings.camera

import android.content.Context
import android.content.Intent
import android.os.Bundle
import de.seniorlaguna.qrcodescanner.databinding.ActivityChooseCameraBinding
import de.seniorlaguna.qrcodescanner.di.settings
import de.seniorlaguna.qrcodescanner.extension.applySystemWindowInsets
import de.seniorlaguna.qrcodescanner.feature.BaseActivity

class ChooseCameraActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ChooseCameraActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding : ActivityChooseCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportEdgeToEdge()
        handleToolbarBackClicked()
    }

    override fun onResume() {
        super.onResume()
        showSelectedCamera()
        handleBackCameraButtonChecked()
        handleFrontCameraButtonChecked()
    }

    private fun showSelectedCamera() {
        val isBackCamera = settings.isBackCamera
        binding.buttonBackCamera.isChecked = isBackCamera
        binding.buttonFrontCamera.isChecked = isBackCamera.not()
    }

    private fun supportEdgeToEdge() {
        binding.rootView.applySystemWindowInsets(applyTop = true, applyBottom = true)
    }

    private fun handleToolbarBackClicked() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun handleBackCameraButtonChecked() {
        binding.buttonBackCamera.setCheckedChangedListener { isChecked ->
            if (isChecked) {
                binding.buttonFrontCamera.isChecked = false
            }
            settings.isBackCamera = isChecked
        }
    }

    private fun handleFrontCameraButtonChecked() {
        binding.buttonFrontCamera.setCheckedChangedListener { isChecked ->
            if (isChecked) {
                binding.buttonBackCamera.isChecked = false
            }
            settings.isBackCamera = isChecked.not()
        }
    }
}