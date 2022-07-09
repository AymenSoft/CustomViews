package com.aymensoft.customviews.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aymensoft.customviews.databinding.ActivityMovieDetailsBinding
import com.aymensoft.customviews.repositories.movies.models.Genre
import com.aymensoft.customviews.repositories.movies.MoviesModelFactory
import com.aymensoft.customviews.repositories.movies.MoviesRepository
import com.aymensoft.customviews.repositories.movies.MoviesViewModel
import com.aymensoft.customviews.utils.BIG_POSTER_URL

/**
 * show movie details
 * @author Aymen Masmoudi
 * */
@Suppress("DEPRECATION")
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    private var movieId = 0

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesViewModel =
            ViewModelProvider(this, MoviesModelFactory(MoviesRepository(), application))
                .get(MoviesViewModel::class.java)

        //extend interface layout to full screen
        binding.layoutDetails.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        //get movie id
        val data = intent
        movieId = data.getIntExtra("movieId", 0)
        if (movieId > 0) {
            moviesViewModel.getMovieDetails(movieId)
            observeMovieDetails()
        } else finish()

    }

    private fun observeMovieDetails() {
        moviesViewModel.movieDetails.observe(this) {
            binding.movieDetails.apply {
                setMovieName(it.title)
                setMovieReleaseDate(it.releaseDate)
                setMovieOverview(it.overview)
                val poster = "$BIG_POSTER_URL${it.posterPath}"
                setMoviePoster(poster)
                setMovieCategories(movieCategories(it.genres))
                onBackClickListener = {
                    finish()
                }
            }
        }
        moviesViewModel.errorMessage.observe(this) {
            finish()
        }
    }

    //get movie categories
    private fun movieCategories(genres: List<Genre>): List<String> {
        val categories = mutableListOf<String>()
        for (genre in genres) {
            categories.add(genre.name)
        }
        return categories
    }

}