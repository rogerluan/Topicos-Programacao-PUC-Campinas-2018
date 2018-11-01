package br.com.hoov.imdbbrowser.View

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import br.com.hoov.imdbbrowser.Model.Movie
import br.com.hoov.imdbbrowser.R
import br.com.hoov.imdbbrowser.Utilities.isGone
import br.com.hoov.imdbbrowser.Utilities.loadImage
import kotlinx.android.synthetic.main.row_movie.view.*

class MovieRow : Row {

    // Lifecycle
    constructor(context: Context) : super(context) { initialize() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { initialize() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize() }

    private fun initialize() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.row_movie, this)
    }

    // Update
    override fun handleItemChanged() {
        val movie = item as? Movie ?: return
        title = movie.title
        subtitle = movie.year
        imageView.isGone = false
        imageView.loadImage(movie.poster)
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
