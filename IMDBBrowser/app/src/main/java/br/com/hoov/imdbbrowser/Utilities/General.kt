package br.com.hoov.imdbbrowser.Utilities

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.Toast

// Convenience
//private fun showToast(message: String) {
//    val context = application!!
//    val toast =
//    Handler(Looper.getMainLooper()).post { // TODO: Reconsider
//        toast.show()
//    }
//}

var View.isGone: Boolean
    get() = visibility == View.GONE
    set(value) { visibility = if (value) View.GONE else View.VISIBLE }
