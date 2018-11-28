package br.com.hoov.donations.View

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import br.com.hoov.donations.Model.Donation
import br.com.hoov.donations.R
import br.com.hoov.donations.Utilities.isGone
import kotlinx.android.synthetic.main.row_donation.view.*

class DonationRow : Row {

    // Lifecycle
    constructor(context: Context) : super(context) { initialize() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { initialize() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize() }

    private fun initialize() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.row_donation, this)
    }

    // Update
    override fun handleItemChanged() {
        val donation = item as? Donation ?: return
        title = donation.title
        subtitle = "${donation.city} ${donation.state}"
    }

    // Accessors
    var title: String
        get() = titleTextView.text.toString()
        set(value) { titleTextView.text = value; hideTitleTextViewIfNeeded() }

    var titleTextViewHint: String
        get() = titleTextView.hint?.toString() ?: ""
        set(value) { titleTextView.hint = value; hideTitleTextViewIfNeeded() }

    var subtitle: String
        get() = subtitleTextView.text.toString()
        set(value) { subtitleTextView.text = value; hideSubtitleTextViewIfNeeded() }

    var subtitleTextViewHint: String
        get() = subtitleTextView.hint?.toString() ?: ""
        set(value) { subtitleTextView.hint = value; hideSubtitleTextViewIfNeeded() }

    // Convenience
    private fun hideTitleTextViewIfNeeded() {
        titleTextView.isGone = title.isBlank() && titleTextViewHint.isBlank()
    }

    private fun hideSubtitleTextViewIfNeeded() {
        subtitleTextView.isGone = subtitle.isBlank() && subtitleTextViewHint.isBlank()
    }
}
