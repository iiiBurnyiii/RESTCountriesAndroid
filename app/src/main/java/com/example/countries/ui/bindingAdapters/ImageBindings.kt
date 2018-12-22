package com.example.countries.ui.bindingAdapters

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.countries.util.svgSupport.GlideApp
import com.example.countries.util.svgSupport.SvgSoftwareLayerSetter


object ImageBindings {

    @JvmStatic
    @BindingAdapter("imgUrl", "imgPlaceholder", "imgError")
    fun ImageView.setImage(url: String?, placeholder: Drawable, error: Drawable) {
        GlideApp.with(this.context.applicationContext)
            .`as`(PictureDrawable::class.java)
            .placeholder(placeholder)
            .error(error)
            .listener(SvgSoftwareLayerSetter())
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(url)
            .into(this)
    }

}