package com.marcpetit.minddenmvvm.utils

import android.view.View

object ViewUtils {

    fun View.visible() {
        isVisible(true)
    }

    fun View.gone() {
        isVisible(false)
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun View.isVisible(isVisible: Boolean) {
        visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}