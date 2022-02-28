package by.geekbrains.moviesguide.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.geekbrains.moviesguide.databinding.FragmentFavoriteMoviesBinding

class FavoriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun nInstance(): FavoriteMoviesFragment = FavoriteMoviesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}