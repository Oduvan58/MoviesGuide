package by.geekbrains.moviesguide.viewmodel

import by.geekbrains.moviesguide.model.Movie
import by.geekbrains.moviesguide.model.ResultsMovie

sealed class AppState {
    data class Success(val movieData: List<ResultsMovie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
