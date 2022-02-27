package by.geekbrains.moviesguide.model

class RepositoryImpl : Repository {
    override fun getMovieFromServer(): Movie {
        return Movie("Железный человек", "2008")
    }

    override fun getMovieFromLocalStorage(): Movie {
        return Movie("Железный человек", "2008")
    }
}