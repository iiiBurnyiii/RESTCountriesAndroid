package com.example.countries.ui.bindingAdapters

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.countries.util.GlideApp
import com.example.countries.util.svgSupport.SvgSoftwareLayerSetter


object ImageBindings {

    @JvmStatic
    @BindingAdapter("imgFlagName", "imgPlaceholder", "imgError")
    fun ImageView.setImage(flagName: String?, placeholder: Drawable, error: Drawable) {
        if (flagName != null && flagName in context.fileList()) {
            GlideApp.with(this)
                .`as`(PictureDrawable::class.java)
                .load(context.openFileInput(flagName).readBytes())
                .placeholder(placeholder)
                .error(error)
                .listener(SvgSoftwareLayerSetter())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(this)
        }
    }
}