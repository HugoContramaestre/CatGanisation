package com.task.catganisation.ui.common.extensions

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.task.catganisation.R


private const val DEFAULT_ANIMATION_DURATION = 1000L

fun View.show() {
    visibility = View.VISIBLE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.visible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.showSnackbarSimple(message: Int, isError: Boolean = false) {
    showSnackbarSimple(context.getString(message), isError)
}

fun View.showSnackbarSimple(message: String, isError: Boolean = false) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.white))
    with(snackbar.view) {
        setBackgroundColor(
            ContextCompat.getColor(
                context,
                if (isError) {
                    R.color.error
                } else {
                    R.color.teal_700
                }
            )
        )
        findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            setTextColor(ContextCompat.getColor(context, R.color.white))
            maxLines = 5
        }
        setOnClickListener {
            snackbar.dismiss()
        }
    }
    snackbar.show()
}

fun TextInputLayout.showError(text: String? = null) {
    isErrorEnabled = true
    error = text ?: " "
}

fun TextInputLayout.hideError() {
    error = ""
    isErrorEnabled = false
}

fun ImageView.loadUrl(url: String?, placeholder: Int = -1) {
    val userAgent = "Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.181 Mobile Safari/537.36"
    val glideUrl = GlideUrl(
        url ?: "",
        LazyHeaders.Builder().addHeader("User-Agent", userAgent).build()
    )
    if (placeholder > 0)
        Glide.with(context).load(glideUrl).placeholder(placeholder).into(this)
    else
        Glide.with(context).load(glideUrl).into(this)
}

fun ImageView.loadRoundedCornersUrl(url: String) {
    val r =
        context.resources.getDimensionPixelOffset(R.dimen.item_image_background_corner_radius)
    Glide.with(context).load(url).transform(CenterInside(), RoundedCorners(r)).into(this)
}

fun <T : ViewDataBinding> ViewGroup.bindingInflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = true
): T = DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToRoot)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)