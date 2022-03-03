package by.geekbrains.moviesguide.model

interface Repository {

    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorageNow(): List<Movie>
    fun getMovieFromLocalStorageSoon(): List<Movie>
}