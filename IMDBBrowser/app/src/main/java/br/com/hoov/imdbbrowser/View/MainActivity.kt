package br.com.hoov.imdbbrowser.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import br.com.hoov.imdbbrowser.Model.Movie
import br.com.hoov.imdbbrowser.R
import br.com.hoov.imdbbrowser.Store.Server
import kotlinx.android.synthetic.main.activity_main.*

typealias Void = Unit

class MainActivity : AppCompatActivity() {
    private var movies: List<Movie> = listOf()
        set(value) { field = value; movieList.items = value }

    // Components
    private val movieList by lazy {
        val result = RecyclerView(this, MovieRow::class.java, movies)
        result.itemSelectedHandler = { showMovie(it) }
        result
    }

    // Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set up action bar
        supportActionBar?.title = "IMDB Browser"
        // Set up movie list
        movieListContainer.addView(movieList)
        searchView.didSettleOnTextHandler = { searchMovies(it) }
    }

    // Interactions
    private fun searchMovies(query: String) {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isBlank()) {
            movies = listOf()
            return
        }
        Server.fetchMovies(normalizedQuery) { movies, error ->
            if (error == null) {
                this.movies = movies ?: listOf()
            } else {
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showMovie(movie: Movie) {
        MovieActivity.startActivity(this, movie)
    }
}
