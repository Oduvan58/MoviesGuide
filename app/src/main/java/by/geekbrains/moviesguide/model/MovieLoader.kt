package by.geekbrains.moviesguide.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

@RequiresApi(Build.VERSION_CODES.N)
class MovieLoader(private val loader: MovieLoader.MovieLoaderListener) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie(isNow: Boolean) {
        try {
            val uri = if (isNow) URL("https://api.themoviedb.org/3/movie/now_playing?api_key=4e3468a93f21d09f726c2de3e52a1896")
            else URL("https://api.themoviedb.org/3/movie/upcoming?api_key=4e3468a93f21d09f726c2de3e52a1896")
            Thread {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    val stream = InputStreamReader(urlConnection.inputStream)
                    val bufferedReader = BufferedReader(stream)
                    val movies = Gson().fromJson(getLines(bufferedReader), MoviesDTO::class.java).results
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
