package by.geekbrains.moviesguide.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.geekbrains.moviesguide.databinding.FragmentDetailsMovieBinding
import by.geekbrains.moviesguide.model.Movie
import by.geekbrains.moviesguide.viewmodel.AppState
import by.geekbrains.moviesguide.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsMovieFragment : Fragment() {

    private var _binding: FragmentDetailsMovieBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun nInstance(): DetailsMovieFragment = DetailsMovieFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                isLoad(false)
                setData(appState.movieData)
            }
            is AppState.Loading -> {
                isLoad(true)
            }
            is AppState.Error -> {
                isLoad(false)
                Snackbar
                    .make(binding.detailFragmentLayout, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") {
                        viewModel.getMovieFromLocalSource()
                    }.show()
            }
        }
    }

    private fun setData(movieData: Movie) {
        binding.titleMovieTextView.text = movieData.name
        binding.dateMovieTextView.text = movieData.date
    }

    fun isLoad(isShow: Boolean) {
        binding.loadingLayout.isVisible = isShow
        binding.detailFragmentLayout.isVisible = !isShow
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}