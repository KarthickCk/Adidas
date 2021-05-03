package com.app.adidas.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DataBindingUtils {

    @JvmStatic
    @BindingAdapter("loadSrc", "placeHolder", requireAll = false)
    fun downloadImage(imageView: ImageView, url: String?, @DrawableRes placeHolder: Int) {
        Glide.with(imageView)
            .load(url)
            .placeholder(placeHolder)
            .into(imageView)
    }
}