package com.aymensoft.customviews.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.aymensoft.customviews.R
import com.aymensoft.customviews.databinding.ItemMoviesBinding
import com.bumptech.glide.Glide

class MoviesItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle){

    private lateinit var binding: ItemMoviesBinding

    var onItemClickListener: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.item_movies, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ItemMoviesBinding.bind(this)
        binding.root.setOnClickListener {
            onItemClickListener?.invoke()
        }
    }

    fun setMovieName(name: String){
        binding.tvName.text = name
    }
    fun setMovieReleaseDate(releaseDate: String){
        binding.tvRelease.text = context.getString(R.string.release, releaseDate)
    }
    fun setMovieVoteAverage(voteAverage: Double){
        binding.tvVote.text = context.getString(R.string.vote, voteAverage)
    }
    fun setMoviePoster(poster: String){
        Glide.with(context)
            .load(poster)
            .placeholder(R.drawable.ic_no_poster)
            .into(binding.imgPoster)
    }

}