package de.seniorlaguna.qrcodescanner.feature.tabs.create.qr

import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import de.seniorlaguna.barcodescanner.databinding.ItemAppBinding

class AppAdapter(private val listener: Listener) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    interface Listener {
        fun onAppClicked(packageName: String)
    }

    var apps: List<ResolveInfo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return apps.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemAppBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = apps[position]
        val isLastPosition = position == apps.lastIndex
        holder.show(app, isLastPosition)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val packageManager: PackageManager
            get() = itemView.context.applicationContext.packageManager

        private lateinit var binding : ItemAppBinding

        constructor(binding : ItemAppBinding) : this(binding.root) {
            this.binding = binding
        }

        fun show(app: ResolveInfo, isLastPosition: Boolean) {
            showName(app)
            showIcon(app)
            showDelimiter(isLastPosition)
            handleItemClicked(app)
        }

        private fun showName(app: ResolveInfo) {
            binding.textView.text = app.loadLabel(packageManager)
        }

        private fun showIcon(app: ResolveInfo) {
            binding.imageView.setImageDrawable(app.loadIcon(packageManager))
        }

        private fun showDelimiter(isLastPosition: Boolean) {
            binding.delimiter.isInvisible = isLastPosition
        }

        private fun handleItemClicked(app: ResolveInfo) {
            itemView.setOnClickListener {
                listener.onAppClicked(app.activityInfo?.packageName.orEmpty())
            }
        }
    }
}