package by.geekbrains.moviesguide.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.geekbrains.moviesguide.databinding.FragmentMainBinding
import by.geekbrains.moviesguide.model.Movie
import by.geekbrains.moviesguide.viewmodel.AppState
import by.geekbrains.moviesguide.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun nInstance(): MainFragment = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

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
        val manager = activity?.supportFragmentManager
        if (manager != null) {
            val bundle = Bundle()
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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initRecycler(binding.mainFragmentNowRecyclerView, adapterNow)
        initRecycler(binding.mainFragmentUpcomingRecyclerView, adapterSoon)

        viewModel.getLiveDataNow()
            .observe(viewLifecycleOwner, Observer { renderData(it, adapterNow) })
        viewModel.getDataFromLocalSourceNow()
        viewModel.getLiveDataSoon()
            .observe(viewLifecycleOwner, Observer { renderData(it, adapterSoon) })
        viewModel.getDataFromLocalSourceSoon()
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
            }
        }
    }

    private fun isLoad(isShow: Boolean) {
        binding.loadingLayout.isVisible = isShow
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
        super.onDestroy()
    }
}
