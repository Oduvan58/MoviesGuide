package by.geekbrains.moviesguide.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.geekbrains.moviesguide.databinding.FragmentMainBinding
import by.geekbrains.moviesguide.model.Movie
import by.geekbrains.moviesguide.viewmodel.AppState
import by.geekbrains.moviesguide.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    // This fragment will be use later

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun nInstance(): MainFragment = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
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
                val movieData = appState.movieData
                binding.loadingLayout.visibility = View.GONE
                setData(movieData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainFragmentLayout, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") {
                        viewModel.getMovieFromLocalSource()
                    }.show()
            }
        }
    }

    private fun setData(movieData: Movie) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
