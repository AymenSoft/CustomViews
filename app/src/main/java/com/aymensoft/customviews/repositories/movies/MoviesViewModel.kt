package com.aymensoft.customviews.repositories.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aymensoft.customviews.repositories.movies.models.MovieDetails
import com.aymensoft.customviews.repositories.movies.models.MoviesItem
import com.aymensoft.customviews.repositories.movies.models.MoviesList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel constructor(private val repository: MoviesRepository): ViewModel() {

    val moviesList = MutableLiveData<List<MoviesItem>>()
    val movieDetails = MutableLiveData<MovieDetails>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies(page: Int){
        val response = repository.getMovies(page)
        response.enqueue(object : Callback<MoviesList>{
            override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {
                moviesList.postValue(response.body()!!.results)
            }
            override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getMovieDetails(id: Int){
        val response = repository.getMovieDetails(id)
        response.enqueue(object : Callback<MovieDetails>{
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                movieDetails.postValue(response.body())
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

}