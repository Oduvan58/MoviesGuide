package by.geekbrains.moviesguide.model

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    val page: Int?,
    val results: ResultsMovie?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)
