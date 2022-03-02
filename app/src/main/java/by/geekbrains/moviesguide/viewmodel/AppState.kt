package by.geekbrains.moviesguide.viewmodel

import by.geekbrains.moviesguide.model.Movie

sealed class AppState {
    data class Success(val movieData: Movie) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
