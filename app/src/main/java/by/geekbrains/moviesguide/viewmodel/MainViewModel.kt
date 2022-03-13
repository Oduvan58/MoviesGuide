package by.geekbrains.moviesguide.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.geekbrains.moviesguide.model.MovieLoader
import by.geekbrains.moviesguide.model.MoviesDTO

class MainViewModel(
    private val liveDataToObserveNow: MutableLiveData<AppState> = MutableLiveData(),
    private val liveDataToObserveSoon: MutableLiveData<AppState> = MutableLiveData(),
    private val onLoadListener: MovieLoader.MovieLoaderListener =
        object : MovieLoader.MovieLoaderListener {
            override fun onLoaded(movieDTO: MoviesDTO, baseUrl: String) {

            }

            override fun onFailed(throwable: Throwable) {

            }
        },
    private var movieData: MoviesDTO
) : ViewModel() {

    fun getLiveDataNow() = liveDataToObserveNow
    fun getLiveDataSoon() = liveDataToObserveSoon

    @RequiresApi(Build.VERSION_CODES.N)
    fun getMovieFromLocalSourceNow() {
        liveDataToObserveNow.postValue(AppState.Loading)
//        Thread {
//            sleep(1000)
//            liveDataToObserveNow.postValue(AppState.Success(
//                onLoadListener.onLoaded(movieData.results, MovieLoader.NOW)
//            ))
//        }.start()
    }

    fun getMovieFromLocalSourceSoon() {
        liveDataToObserveNow.postValue(AppState.Loading)
//        Thread {
//            sleep(1000)
//            liveDataToObserveSoon.postValue(AppState.Success(
//                repositoryImpl.getMovieFromServer(false)))
//        }.start()
    }
}