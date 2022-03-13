package by.geekbrains.moviesguide.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.geekbrains.moviesguide.model.MovieLoader
import by.geekbrains.moviesguide.model.ResultsMovie

class MainViewModel(
    private val liveDataToObserveNow: MutableLiveData<AppState> = MutableLiveData(),
    private val liveDataToObserveSoon: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {

    private val onLoadListener: MovieLoader.MovieLoaderListener =
        object : MovieLoader.MovieLoaderListener {
            override fun onLoaded(movies: List<ResultsMovie>, isNow: Boolean) {
                when (isNow) {
                    true -> liveDataToObserveNow.postValue(AppState.Success(movies))
                    false -> liveDataToObserveSoon.postValue(AppState.Success(movies))
                }
            }

            override fun onFailed(throwable: Throwable) {
                throwable.message?.let { Log.d("TAG", it) }
            }
        }
    private val movieLoader: MovieLoader = MovieLoader(onLoadListener)

    fun getLiveDataNow() = liveDataToObserveNow
    fun getLiveDataSoon() = liveDataToObserveSoon

    fun getMovieFromLocalSourceNow() {
        liveDataToObserveNow.postValue(AppState.Loading)
        movieLoader.loadMovie(true)
    }

    fun getMovieFromLocalSourceSoon() {
        liveDataToObserveNow.postValue(AppState.Loading)
        movieLoader.loadMovie(false)
    }
}