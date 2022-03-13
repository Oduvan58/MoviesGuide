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
class MovieLoader(private val listener: MovieLoaderListener, private val baseUrl: String) {
    companion object {
        const val NOW = "https://api.themoviedb.org/3/movie/now_playing"
        const val UPCOMING = "https://api.themoviedb.org/3/movie/upcoming"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie() {
        try {
            val uri =
                URL("$baseUrl?api_key=4e3468a93f21d09f726c2de3e52a1896")
            val handler = Handler(Looper.getMainLooper())
            Thread {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    val stream = InputStreamReader(urlConnection.inputStream)
                    val bufferedReader = BufferedReader(stream)
                    val movieDTO: MoviesDTO =
                        Gson().fromJson(
                            getLines(bufferedReader),
                            MoviesDTO::class.java
                        )
                    handler.post { listener.onLoaded(movieDTO, baseUrl) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface MovieLoaderListener {
        fun onLoaded(movieDTO: MoviesDTO, baseUrl: String)
        fun onFailed(throwable: Throwable)
    }
}
