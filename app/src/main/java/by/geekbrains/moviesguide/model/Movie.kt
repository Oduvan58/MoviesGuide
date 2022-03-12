package by.geekbrains.moviesguide.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var name: String? = null,
    var date: String? = null,
    var description: String? = null,
    var genre: String? = null,
    var rating: Double? = null,
    var id: String? = null
) : Parcelable
