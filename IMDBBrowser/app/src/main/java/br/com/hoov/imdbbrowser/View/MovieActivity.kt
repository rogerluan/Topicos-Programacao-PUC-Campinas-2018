package br.com.hoov.imdbbrowser.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import br.com.hoov.imdbbrowser.Model.Movie
import br.com.hoov.imdbbrowser.R
import br.com.hoov.imdbbrowser.Store.Server

class MovieActivity : AppCompatActivity() {
    private lateinit var movie: Movie

    companion object {
        private const val MOVIE_ID = "MOVIE"

        @JvmStatic fun startActivity(context: Context, movie: Movie) {
            context.startActivity(Intent(context, MovieActivity::class.java).apply {
                putExtra(MOVIE_ID, movie)
            })
        }
    }

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        // Unwrap parcelable
        movie = intent!!.getParcelableExtra(MOVIE_ID)!!
        // Set up action bar
        supportActionBar?.title = movie.title
        supportActionBar?.subtitle = movie.year
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Fetch full movie data
        fetchMovieByID(movie.imdbID)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    // Updating
    private fun handleMovieChanged() {
        // TODO: Set up rest of the UI
    }

    // Interaction
    private fun fetchMovieByID(id: String) {
        Server.fetchMovie(id) { movie, error ->
            if (error == null && movie != null) {
                this.movie = movie
                handleMovieChanged()
            } else {
                Toast.makeText(this, error?.message ?: "An error occurred.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
