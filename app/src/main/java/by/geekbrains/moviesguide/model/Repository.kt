package by.geekbrains.moviesguide.model

interface Repository {

    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorage(): Movie
}