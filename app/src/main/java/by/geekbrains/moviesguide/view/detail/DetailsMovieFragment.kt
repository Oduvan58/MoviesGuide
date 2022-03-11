package by.geekbrains.moviesguide.view.detail

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.geekbrains.moviesguide.databinding.FragmentDetailsMovieBinding
import by.geekbrains.moviesguide.model.Movie
import by.geekbrains.moviesguide.model.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val MY_API_KEY = "4e3468a93f21d09f726c2de3e52a1896"

class DetailsMovieFragment : Fragment() {

    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieBundle: Movie

    companion object {
        const val BUNDLE_KEY = "show_detail_movie"
        fun nInstance(bundle: Bundle): DetailsMovieFragment {
            val fragment = DetailsMovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieBundle = arguments?.getParcelable(BUNDLE_KEY) ?: Movie()
        loadMovie()
    }

    private fun displayMovie(movieDTO: MovieDTO) {
        with(binding) {
            titleMovieTextView.text = movieDTO.results?.title
            detailMovieDateTextView.text = movieDTO.results?.releaseDate
            detailMovieDescriptionTextView.text = movieDTO.results?.overview
            detailMovieRatingTextView.text = movieDTO.results?.voteAverage.toString()
            genreMovieTextView.text = movieDTO.results?.genreIds.toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie() {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movies/get-movie-details/${movieBundle.id}?api_key=4e3468a93f21d09f726c2de3e52a1896")
//            https://api.themoviedb.org/3/movie/now_playing?ru-Ru=${movieBundle.name}
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
//                    urlConnection.addRequestProperty(
//                        "X-The-Movie-DB-API-Key",
//                        MY_API_KEY
//                    )
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val movieDTO: MovieDTO =
                        Gson().fromJson(getLines(bufferedReader),
                            MovieDTO::class.java)
                    handler.post { displayMovie(movieDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}