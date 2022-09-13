package com.task.catganisation.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.task.catganisation.R
import com.task.catganisation.ui.common.extensions.loadUrl

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (!url.isNullOrEmpty()) loadUrl(url)
}

@BindingAdapter("loadImage")
fun ImageView.bindContentImage(url: String?) {
    if (!url.isNullOrEmpty()) loadUrl(url)
    else setImageResource(R.drawable.placeholder_avatar)
}