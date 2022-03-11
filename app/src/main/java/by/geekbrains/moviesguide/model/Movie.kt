package by.geekbrains.moviesguide.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Movie(
    var name: String? = null,
    var date: String? = null,
    var description: String? = null,
    var genre: String? = null,
    var rating: Double? = null,
    var id: String? = UUID.randomUUID().toString()
) : Parcelable
