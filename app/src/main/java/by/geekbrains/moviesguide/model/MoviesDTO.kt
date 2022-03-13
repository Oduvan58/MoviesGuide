package by.geekbrains.moviesguide.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesDTO(
    val page: Int,
    val results: List<ResultsMovie>
) : Parcelable
