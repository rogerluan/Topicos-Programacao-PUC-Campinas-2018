package br.com.hoov.imdbbrowser.View

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.SearchView
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

/**
 * To disable the autofocus, which brings up the keyboard, on start up you will need to set the
 * following the parameters in the xml. This will not work if set programmatically.
 * android:focusable="false"
 *
 * The following should always be set to allow it to show the default hint text by default. Cannot be set programmatically
 * app:iconifiedByDefault="false"
 */
class SearchView : SearchView {
    var didSettleOnTextHandler: ((String) -> Void)? = null
    // Note I tried removing the search icon with the view id, however, seems it's not possible with appcompat SearchView
    private val closeButton by lazy { findViewById<View>(android.support.v7.appcompat.R.id.search_close_btn) }
    // Settings for optional timer and delay for when user types before it triggers
    private var timer: Timer? = null
    // Expressed in milliseconds. There's a delay option because we don't want remote searches to trigger so frequently
    var delayInMs: Long? = 1000

    // Lifecycle
    constructor(context: Context) : super(context) { initialize() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { initialize() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize() }

    private fun initialize() {
        // Remove thin gray line underneath the search view
        val searchPlate = findViewById<View>(android.support.v7.appcompat.R.id.search_plate)
        searchPlate.background = null

        // Close button listener
        closeButton.setOnClickListener {
            setQuery("", true)
            clearFocus()
            hideKeyboard()
            // This must happen after clearing focus
            closeButton.visibility = View.GONE
        }

        setOnQueryTextFocusChangeListener { _, gainFocus ->
            // This is needed to remove focus on the SearchView, to stop the blinking cursor
            isFocusable = gainFocus
        }

        setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                if (delayInMs != null && !newText.isNullOrEmpty()) {
                    // If there's a delay set then set the new timer to schedule auto trigger
                    timer?.cancel()
                    timer?.purge()
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {

                        override fun run() {
                            notifyListener(newText)
                        }
                    }, delayInMs!!)
                } else notifyListener(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                notifyListener(query)
                hideKeyboard()
                return true
            }
        })
    }

    // Updating
    private fun notifyListener(query: String?) {
        // Cancel timer if needed
        timer?.cancel()
        timer?.purge()

        // Dispatch on main queue to fix incorrect thread exception when handling UI
        Handler(Looper.getMainLooper()).post {
            didSettleOnTextHandler?.invoke(query ?: "")
        }
    }

    fun hideKeyboard() {
        val inputMethodManager = context.getSystemService(android.app.Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
