package by.geekbrains.moviesguide.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var name: String,
    var date: String,
    var description: String,
    var genre: String,
    var rating: Double,
) : Parcelable
