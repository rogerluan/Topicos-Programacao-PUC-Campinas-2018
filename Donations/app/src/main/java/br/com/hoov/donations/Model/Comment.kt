package br.com.hoov.donations.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize data class Comment(
    var id: Int = 0,
    var donationID: Int = 0,
    var creationDate: Date = Date(),
    var comment: String = ""
) : Parcelable
