package by.geekbrains.moviesguide.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import by.geekbrains.moviesguide.BuildConfig
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

@RequiresApi(Build.VERSION_CODES.N)
class MovieLoader(private val loader: MovieLoaderListener) {

    companion object {
        const val NOW = "https://api.themoviedb.org/3/movie/now_playing"
        const val UPCOMING = "https://api.themoviedb.org/3/movie/upcoming"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie(isNow: Boolean) {
        try {
            val uri = if (isNow) URL("$NOW?api_key=${BuildConfig.MOVIE_API_KEY}")
            else URL("$UPCOMING?api_key=${BuildConfig.MOVIE_API_KEY}")
            Thread {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    val stream = InputStreamReader(urlConnection.inputStream)
                    val bufferedReader = BufferedReader(stream)
                    val movies =
                        Gson().fromJson(getLines(bufferedReader), MoviesDTO::class.java).results
                    loader.onLoaded(movies, isNow)
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    loader.onFailed(e)
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            loader.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface MovieLoaderListener {
        fun onLoaded(movies: List<ResultsMovie>, isNow: Boolean)
        fun onFailed(throwable: Throwable)
    }
}
