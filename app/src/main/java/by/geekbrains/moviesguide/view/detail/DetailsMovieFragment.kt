package by.geekbrains.moviesguide.view.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.geekbrains.moviesguide.databinding.FragmentDetailsMovieBinding
import by.geekbrains.moviesguide.model.ResultsMovie

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}