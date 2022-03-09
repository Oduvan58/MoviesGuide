package by.geekbrains.moviesguide.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.geekbrains.moviesguide.R
import by.geekbrains.moviesguide.databinding.FragmentMainBinding
import by.geekbrains.moviesguide.model.Movie
import by.geekbrains.moviesguide.showSnackBar
import by.geekbrains.moviesguide.view.detail.DetailsMovieFragment
import by.geekbrains.moviesguide.view.detail.DetailsMovieFragment.Companion.BUNDLE_KEY
import by.geekbrains.moviesguide.viewmodel.AppState
import by.geekbrains.moviesguide.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun nInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val adapterNow = MainAdapter(object : OnClickItemMovie {
        override fun onItemClick(movie: Movie) {
            showDetailsMovie(movie)
        }
    })

    private val adapterSoon = MainAdapter(object : OnClickItemMovie {
        override fun onItemClick(movie: Movie) {
            showDetailsMovie(movie)
        }
    })

    private fun showDetailsMovie(movie: Movie) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.activity_main__details_movie_fragment_container,
                    DetailsMovieFragment.nInstance(Bundle().apply {
                        putParcelable(BUNDLE_KEY, movie)
                    }))
                .addToBackStack("")
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler(binding.mainFragmentNowRecyclerView, adapterNow)
        initRecycler(binding.mainFragmentUpcomingRecyclerView, adapterSoon)

        viewModel.getLiveDataNow()
            .observe(viewLifecycleOwner) { appState ->
                renderData(appState, adapterNow)
            }
        viewModel.getMovieFromLocalSourceNow()
        viewModel.getLiveDataSoon()
            .observe(viewLifecycleOwner) { appState ->
                renderData(appState, adapterSoon)
            }
        viewModel.getMovieFromLocalSourceSoon()
    }

    private fun renderData(appState: AppState, adapter: MainAdapter) {
        when (appState) {
            is AppState.Success -> {
                isLoad(false)
                adapter.setMovie(appState.movieData)
            }
            is AppState.Loading -> {
                isLoad(true)
            }
            is AppState.Error -> {
                isLoad(false)
                binding.mainFragmentLayout.showSnackBar(getString(R.string.error))
            }
        }
    }

    private fun isLoad(isShow: Boolean) {
        binding.loadingNow.isVisible = isShow
        binding.loadingSoon.isVisible = isShow
    }

    private fun initRecycler(recyclerView: RecyclerView, adapter: MainAdapter) {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        adapterNow.removeListener()
        adapterSoon.removeListener()
        super.onDestroy()
    }
}
