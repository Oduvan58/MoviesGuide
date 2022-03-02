package by.geekbrains.moviesguide.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.geekbrains.moviesguide.R
import by.geekbrains.moviesguide.databinding.ActivityMainBinding
import by.geekbrains.moviesguide.view.detail.DetailsMovieFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main__details_movie_fragment_container,
                    DetailsMovieFragment.nInstance())
                .commit()
        }
    }
}