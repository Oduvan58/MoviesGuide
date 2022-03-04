package by.geekbrains.moviesguide.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.geekbrains.moviesguide.databinding.FragmentDetailsMovieBinding
import by.geekbrains.moviesguide.model.Movie

class DetailsMovieFragment : Fragment() {

    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>(BUNDLE_KEY)
        if (movie != null) {
            binding.titleMovieTextView.text = movie.name
            binding.detailMovieDateTextView.text = movie.date
            binding.detailMovieDescriptionTextView.text = movie.description
            binding.detailMovieRatingTextView.text = movie.rating.toString()
            binding.genreMovieTextView.text = movie.genre
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}