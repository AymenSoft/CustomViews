package com.aymensoft.customviews.view.activities.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.aymensoft.customviews.databinding.ItemsMoviesBinding
import com.aymensoft.customviews.repositories.movies.models.MoviesItem
import com.aymensoft.customviews.utils.SMALL_POSTER_URL

/**
 * show movies list as a recycler view
 * @author Aymen Masmoudi
 * */
@SuppressLint("NotifyDataSetChanged")
class MoviesAdapter(listener: ClickListener) : RecyclerView.Adapter<MoviesAdapter.MoviesHolder>(), Filterable {

    private lateinit var context: Context
    private var defList: ArrayList<MoviesItem>
    private var movies: ArrayList<MoviesItem>
    private val listener: ClickListener

    init {
        this.defList = ArrayList()
        this.movies = ArrayList()
        this.listener = listener
    }

    //set movies list and notify adapter to refresh
    fun setMovies(list: ArrayList<MoviesItem>){
        this.defList = ArrayList(list)
        this.movies = defList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.MoviesHolder {
        val binding = ItemsMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.MoviesHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = movies.size

    inner class MoviesHolder(private val binding: ItemsMoviesBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            val movie = movies[position]
            binding.moviesItem.apply {
                setMovieName(movie.title)
                setMovieReleaseDate(movie.releaseDate)
                setMovieVoteAverage(movie.voteAverage)
                val poster = "$SMALL_POSTER_URL${movie.posterPath}"
                setMoviePoster(poster)
                onItemClickListener = {
                    listener.onItemClickListener(position)
                }
            }

        }
    }

    //detect item click
    interface ClickListener {
        fun onItemClickListener(position: Int)
    }

    //filter movies list by movie name
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val result = ArrayList<MoviesItem>()
                if (constraint!!.isNotEmpty()){
                    for (movie in defList){
                        if (movie.title.lowercase().contains(constraint.toString().lowercase())){
                            result.add(movie)
                        }
                    }
                } else {
                    result.addAll(defList)
                }
                val filterResult = FilterResults()
                filterResult.values = result
                filterResult.count = result.size
                return filterResult
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movies = results!!.values as ArrayList<MoviesItem>
                notifyDataSetChanged()
            }
        }
    }

}