package by.geekbrains.moviesguide.view.detail

import androidx.fragment.app.Fragment
import by.geekbrains.moviesguide.databinding.FragmentDetailsMovieBinding

class DetailsMovieFragment : Fragment() {

    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun nInstance(): DetailsMovieFragment = DetailsMovieFragment()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}