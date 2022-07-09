package com.aymensoft.customviews.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.aymensoft.customviews.R
import com.aymensoft.customviews.databinding.MovieDetailsBinding
import com.bumptech.glide.Glide

class MovieDetails @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle){

    private lateinit var binding: MovieDetailsBinding

    var onBackClickListener: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.movie_details, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = MovieDetailsBinding.bind(this)
        binding.imgBack.setOnClickListener {
            onBackClickListener?.invoke()
        }
    }

    fun setMovieName(name: String){
        binding.tvName.text = name
    }
    fun setMovieReleaseDate(releaseDate: String){
        binding.tvRelease.text = context.getString(R.string.release, releaseDate)
    }
    fun setMovieOverview(overview: String){
        binding.tvOverview.text = context.getString(R.string.overview, overview)
    }
    fun setMoviePoster(poster: String){
        Glide.with(context)
            .load(poster)
            .into(binding.imgPoster)
    }
    fun setMovieCategories(genres: List<String>){
        val categories = mutableListOf<String>()
        categories.addAll(genres)
        binding.tagCategories.tags = categories
    }

}