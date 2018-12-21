package com.example.countries.ui.bindingAdapters

import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.caverock.androidsvg.SVGImageView
import com.example.countries.util.svgSupport.GlideApp
import com.example.countries.util.svgSupport.SvgSoftwareLayerSetter


object ImageBindings {

    @JvmStatic
    @BindingAdapter("imgUrl", "imgPlaceholder", "imgError")
    fun ImageView.setImage(url: String?, placeholder: Drawable, error: Drawable) {
        GlideApp.with(this)
            .`as`(PictureDrawable::class.java)
            .placeholder(placeholder)
            .error(error)
            .listener(SvgSoftwareLayerSetter())
            .centerCrop()
            .load(url)
            .into(this)
    }

}