package by.geekbrains.moviesguide.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.geekbrains.moviesguide.R
import by.geekbrains.moviesguide.model.Movie

class MainAdapter(private var onClickItemMovie: OnClickItemMovie?) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var movieData: List<Movie> = listOf()

    fun setMovie(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount() = movieData.size

    fun removeListener() {
        onClickItemMovie = null
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = itemView.findViewById<TextView>(R.id.item_movie_title_text_view)
        private val date = itemView.findViewById<TextView>(R.id.item_movie_date_text_view)
        private val rating = itemView.findViewById<TextView>(R.id.item_movie_rating_text_view)

        fun bind(movie: Movie) = with(itemView) {
            title.text = movie.name
            date.text = movie.date
            rating.text = movie.rating.toString()
            setOnClickListener {
                onClickItemMovie?.onItemClick(movie)
            }
        }
    }
}

interface OnClickItemMovie {
    fun onItemClick(movie: Movie)
}