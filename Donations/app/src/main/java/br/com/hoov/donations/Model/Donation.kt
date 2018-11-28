package br.com.hoov.donations.Model

import android.os.Parcelable
import br.com.hoov.donations.Model.Comment
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize data class Donation(
    var id: Int = 0,
    var creationDate: Date = Date(),
    val title: String = "",
    var description: String = "",
    var contact: String = "",
    var city: String = "",
    var state: String = "",
    var address: String = "",
    var comments: List<Comment> = listOf()
) : Parcelable
