package br.com.hoov.imdbbrowser.Store

import br.com.hoov.imdbbrowser.Model.Movie
import br.com.hoov.imdbbrowser.Model.SearchResults
import br.com.hoov.imdbbrowser.View.Void
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val server: ServerInterface by lazy {
    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Server.baseURL)
        .client(client)
        .build()

    retrofit.create(ServerInterface::class.java)
}

class Server {

    companion object {
        internal const val baseURL = "http://www.omdbapi.com"
        internal const val apiKey = "7c9c0724"

        @JvmStatic fun fetchMovies(query: String, completionHandler: (List<Movie>?, Throwable?) -> Void) {
            server.getMoviesByQuery(query).enqueue(object : Callback<SearchResults> {

                override fun onResponse(p0: Call<SearchResults>, response: Response<SearchResults>) {
                    val results = response.body() ?: return completionHandler(null, Error("An unknown response was received."))
                    completionHandler(results.movies, null)
                }

                override fun onFailure(p0: Call<SearchResults>?, t: Throwable?) {
                    completionHandler(null, t)
                }
            })
        }

        @JvmStatic fun fetchMovie(id: String, completionHandler: (Movie?, Throwable?) -> Void) {
            server.getMovieByID(id).enqueue(object : Callback<Movie> {

                override fun onResponse(p0: Call<Movie>, response: Response<Movie>) {
                    val movie = response.body() ?: return completionHandler(null, Error("An unknown response was received."))
                    completionHandler(movie, null)
                }

                override fun onFailure(p0: Call<Movie>?, t: Throwable?) {
                    completionHandler(null, t)
                }
            })
        }
    }
}

private interface ServerInterface {
    @GET("/")
    fun getMovieByID(@Query("i") title: String, @Query("apiKey") apiKey: String = Server.apiKey): Call<Movie>
    @GET("/")
    fun getMoviesByQuery(@Query("s") title: String, @Query("type") type: String = "movie", @Query("apiKey") apiKey: String = Server.apiKey): Call<SearchResults>
}
