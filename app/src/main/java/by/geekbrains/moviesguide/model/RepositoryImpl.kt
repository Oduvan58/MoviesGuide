package by.geekbrains.moviesguide.model

class RepositoryImpl : Repository {
    override fun getMovieFromServer(): Movie {
        return Movie("Железный человек", "2008", "Миллиардер-изобретатель Тони Старк " +
                "попадает в плен к Афганским террористам, которые пытаются заставить его создать " +
                "оружие массового поражения. В тайне от своих захватчиков Старк конструирует " +
                "высокотехнологичную киберброню, которая помогает ему сбежать. Однако по возвращении " +
                "в США он узнаёт, что в совете директоров его фирмы плетётся заговор, " +
                "чреватый страшными последствиями. Используя своё последнее изобретение, " +
                "Старк пытается решить проблемы своей компании радикально…", "Фантастика /" +
                " Приключения / Боевик ", 7.9)
    }

    override fun getMovieFromLocalStorageNow(): List<Movie> {
        return getMovieFromLocalStorageNow()
    }

    override fun getMovieFromLocalStorageSoon(): List<Movie> {
        return getMovieFromLocalStorageSoon()
    }
}