package by.geekbrains.moviesguide.view.detail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.geekbrains.moviesguide.databinding.FragmentDetailsMovieBinding
import by.geekbrains.moviesguide.model.ResultsMovie

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_MOVIE_GENRE_EXTRA = "MOVIE GENRE"
const val DETAILS_MOVIE_ID_EXTRA = "MOVIE ID"
const val DETAILS_OVERVIEW_EXTRA = "OVERVIEW"
const val DETAILS_RELEASE_DATE_EXTRA = "RELEASE DATE"
const val DETAILS_TITLE_EXTRA = "TITLE"
const val DETAILS_VOTE_AVERAGE_EXTRA = "VOTE AVERAGE"
private const val MOVIE_VOTE_AVERAGE_INVALID = 0.0
private const val PROCESS_ERROR = "Обработка ошибки"


class DetailsMovieFragment : Fragment() {

    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!
    private var movieBundle: ResultsMovie? = null

    companion object {
        const val BUNDLE_KEY = "show_detail_movie"
        fun nInstance(bundle: Bundle): DetailsMovieFragment {
            val fragment = DetailsMovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> displayMovie(
                    ResultsMovie(
                        intent.getIntegerArrayListExtra(DETAILS_MOVIE_GENRE_EXTRA),
                        intent.getIntExtra(DETAILS_MOVIE_ID_EXTRA, 0),
                        intent.getStringExtra(DETAILS_OVERVIEW_EXTRA),
                        intent.getStringExtra(DETAILS_RELEASE_DATE_EXTRA),
                        intent.getStringExtra(DETAILS_TITLE_EXTRA),
                        intent.getDoubleExtra(DETAILS_VOTE_AVERAGE_EXTRA,
                            MOVIE_VOTE_AVERAGE_INVALID)
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.registerReceiver(
            loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER)
        )
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
        movieBundle = arguments?.getParcelable(BUNDLE_KEY)
        movieBundle?.let { displayMovie(it) }
        getMovie()
    }

    private fun getMovie() {
        context?.let {
            it.startService(Intent(it, DetailsService::class.java).apply {
                putExtra(
                    ID_MOVIE_EXTRA,
                    movieBundle?.id
                )
            })
        }
    }

    private fun displayMovie(movieDTO: ResultsMovie) {
        with(binding) {
            titleMovieTextView.text = movieDTO.title
            detailMovieDateTextView.text = movieDTO.releaseDate
            detailMovieDescriptionTextView.text = movieDTO.overview
            detailMovieRatingTextView.text = movieDTO.voteAverage.toString()
            genreMovieTextView.text = movieDTO.genreIds.toString()
        }
    }

    override fun onDestroy() {
        context?.unregisterReceiver(loadResultsReceiver)
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}