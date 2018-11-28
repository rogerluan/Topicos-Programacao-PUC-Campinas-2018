package br.com.hoov.donations.View

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import br.com.hoov.donations.Model.Comment
import br.com.hoov.donations.Model.Donation
import br.com.hoov.donations.R
import br.com.hoov.donations.Utilities.autoFormatted
import br.com.hoov.donations.Utilities.isGone
import kotlinx.android.synthetic.main.row_comment.view.*

class CommentRow : Row {

    // Lifecycle
    constructor(context: Context) : super(context) { initialize() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { initialize() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize() }

    private fun initialize() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.row_comment, this)
    }

    // Update
    override fun handleItemChanged() {
        val commentItem = item as? Comment ?: return
        comment = commentItem.comment
        timestamp = commentItem.creationDate.autoFormatted(context)
    }

    // Accessors
    var comment: String
        get() = commentTextView.text.toString()
        set(value) { commentTextView.text = value }

    var timestamp: String
        get() = timestampTextView.text.toString()
        set(value) { timestampTextView.text = value }
}
