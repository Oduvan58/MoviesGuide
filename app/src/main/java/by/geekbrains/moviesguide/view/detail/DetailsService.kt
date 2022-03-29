package by.geekbrains.moviesguide.view.detail

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import by.geekbrains.moviesguide.BuildConfig
import by.geekbrains.moviesguide.model.ResultsMovie
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val ID_MOVIE_EXTRA = "ID MOVIE"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000
private const val REQUEST_API_KEY = "X-THE_MOVIE_DB-API-KEY"

class DetailsService(name: String = "DetailService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getIntExtra(ID_MOVIE_EXTRA, 0)
            if (id == 0) {
                onEmptyData()
            } else {
                loadMovie(id.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie(id: String) {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/${id}")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                    addRequestProperty(REQUEST_API_KEY, BuildConfig.MOVIE_API_KEY)
                }
                val stream = InputStreamReader(urlConnection.inputStream)
                val bufferedReader = BufferedReader(stream)
                val movies =
                    Gson().fromJson(getLines(bufferedReader), ResultsMovie::class.java)
                onResponse(movies)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(movies: ResultsMovie) {
        val fact = movies
        if (fact == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(fact.genreIds,
                fact.overview,
                fact.releaseDate,
                fact.title,
                fact.voteAverage)
        }
    }

    private fun onSuccessResponse(
        genreIds: List<Int?>?, overview: String?,
        releaseDate: String?, title: String?, voteAverage: Double?,
    ) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putIntegerArrayListExtra(DETAILS_MOVIE_GENRE_EXTRA,
            genreIds as ArrayList<Int>?)
        broadcastIntent.putExtra(DETAILS_OVERVIEW_EXTRA, overview)
        broadcastIntent.putExtra(DETAILS_RELEASE_DATE_EXTRA, releaseDate)
        broadcastIntent.putExtra(DETAILS_TITLE_EXTRA, title)
        broadcastIntent.putExtra(DETAILS_VOTE_AVERAGE_EXTRA, voteAverage)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }
}