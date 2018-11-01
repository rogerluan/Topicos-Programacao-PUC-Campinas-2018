package br.com.hoov.imdbbrowser.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize data class Movie(
    @SerializedName("Title") val title: String = "",
    @SerializedName("Year") var year: String = "",
    @SerializedName("Rated") var rated: String = "",
    @SerializedName("Released") var released: String = "",
    @SerializedName("Runtime") var runtime: String = "",
    @SerializedName("Genre") var genre: String = "",
    @SerializedName("Director") var director: String = "",
    @SerializedName("Writer") var writer: String = "",
    @SerializedName("Actors") var actors: String = "",
    @SerializedName("Plot") var plot: String = "",
    @SerializedName("Language") var language: String = "",
    @SerializedName("Country") var country: String = "",
    @SerializedName("Awards") var awards: String = "",
    @SerializedName("Poster") var poster: String = "",
    @SerializedName("Metascore") var metascore: String = "",
    @SerializedName("Type") var type: String = "",
    @SerializedName("Response") var response: String = "",
    var imdbRating: String = "",
    var imdbVotes: String = "",
    var imdbID: String = ""
) : Parcelable

@Parcelize data class SearchResults(
    @SerializedName("Search") val movies: List<Movie> = listOf()
) : Parcelable
