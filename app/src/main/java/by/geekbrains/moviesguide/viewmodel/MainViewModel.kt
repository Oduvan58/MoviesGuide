package by.geekbrains.moviesguide.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.geekbrains.moviesguide.model.Repository
import by.geekbrains.moviesguide.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserveNow: MutableLiveData<AppState> = MutableLiveData(),
    private val liveDataToObserveSoon: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
) : ViewModel() {

    fun getLiveDataNow() = liveDataToObserveNow
    fun getLiveDataSoon() = liveDataToObserveSoon

    fun getMovieFromLocalSourceNow() {
        liveDataToObserveNow.postValue(AppState.Loading)
        Thread {
            sleep(1000)
            liveDataToObserveNow.postValue(AppState.Success(
                repositoryImpl.getMovieFromServer(true)))
        }.start()
    }

    fun getMovieFromLocalSourceSoon() {
        liveDataToObserveNow.postValue(AppState.Loading)
        Thread {
            sleep(1000)
            liveDataToObserveSoon.postValue(AppState.Success(
                repositoryImpl.getMovieFromServer(false)))
        }.start()
    }
}