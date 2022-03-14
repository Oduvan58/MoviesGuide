package by.geekbrains.moviesguide.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.geekbrains.moviesguide.model.MovieLoader
import by.geekbrains.moviesguide.model.ResultsMovie
import by.geekbrains.moviesguide.view.detail.AlertDialogFragment

class MainViewModel(
    private val liveDataToObserveNow: MutableLiveData<AppState> = MutableLiveData(),
    private val liveDataToObserveSoon: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    private val fragment: AlertDialogFragment = AlertDialogFragment()

    private val onLoadListener: MovieLoader.MovieLoaderListener =
        object : MovieLoader.MovieLoaderListener {
            override fun onLoaded(movies: List<ResultsMovie>, isNow: Boolean) {
                when (isNow) {
                    true -> liveDataToObserveNow.postValue(AppState.Success(movies))
                    false -> liveDataToObserveSoon.postValue(AppState.Success(movies))
                }
            }

            override fun onFailed(throwable: Throwable) {
                throwable.message?.let {
                    fragment.show(fragment.childFragmentManager,
                        AlertDialogFragment.DIALOG_FRAGMENT_TAG)
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    private val movieLoader: MovieLoader = MovieLoader(onLoadListener)

    fun getLiveDataNow() = liveDataToObserveNow
    fun getLiveDataSoon() = liveDataToObserveSoon

    @RequiresApi(Build.VERSION_CODES.N)
    fun getMovieFromLocalSourceNow() {
        liveDataToObserveNow.postValue(AppState.Loading)
        movieLoader.loadMovie(true)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getMovieFromLocalSourceSoon() {
        liveDataToObserveNow.postValue(AppState.Loading)
        movieLoader.loadMovie(false)
    }
}