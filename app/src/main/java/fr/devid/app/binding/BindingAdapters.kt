package fr.devid.app.binding

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("isGone")
    fun bindIsGone(view: View, isGone: Boolean) {
        view.visibility = if (isGone) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("loadBitmap")
    fun loadBitmap(imageView: ImageView, bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }
}
