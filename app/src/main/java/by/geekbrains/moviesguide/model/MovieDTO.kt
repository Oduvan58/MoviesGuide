package by.geekbrains.moviesguide.model

data class MovieDTO(
    val page: Int?,
    val results: ArrayList<ResultsMovie> = ArrayList(),
    val total_pages: Int?,
    val total_results: Int?
)
