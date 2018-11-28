package br.com.hoov.donations.View

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * A Row base class. Meant to be subclassed.
 */
open class Row : FrameLayout {
    open var item: Any? = null
        set(value) { field = value; handleItemChanged() }

    // Lifecycle
    constructor(context: Context) : super(context) { initialize() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { initialize() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize() }

    private fun initialize() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        if (!hasOnClickListeners()) {
            setOnClickListener { handleDidSelect() }
        }
    }

    // Updating
    open fun handleItemChanged() {
        // To be overridden by subclasses
    }

    // Interaction
    open fun handleDidSelect() {
        // To be overridden by subclasses
    }
}
