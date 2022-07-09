package com.aymensoft.customviews.repositories.movies

import com.aymensoft.customviews.repositories.API
import com.aymensoft.customviews.utils.API_KEY

class MoviesRepository{

    fun getMovies(page: Int) = API.MOVIES_API.getMoviesList(API_KEY, "popularity.desc", page)

    fun getMovieDetails(id: Int) = API.MOVIES_API.getMovieDetails(id, API_KEY)

}