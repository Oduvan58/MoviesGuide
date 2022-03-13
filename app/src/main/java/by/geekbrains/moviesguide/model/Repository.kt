package by.geekbrains.moviesguide.model

interface Repository {
    fun getMovieFromServer(nowMovie: Boolean): List<ResultsMovie>
}