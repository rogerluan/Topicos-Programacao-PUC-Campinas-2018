package br.com.hoov.donations.Utilities

import android.view.View

typealias Void = Unit

var View.isGone: Boolean
    get() = visibility == View.GONE
    set(value) { visibility = if (value) View.GONE else View.VISIBLE }
