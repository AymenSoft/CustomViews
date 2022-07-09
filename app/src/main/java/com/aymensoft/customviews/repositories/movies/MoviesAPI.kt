package com.aymensoft.customviews.repositories.movies

import com.aymensoft.customviews.repositories.movies.models.MovieDetails
import com.aymensoft.customviews.repositories.movies.models.MoviesList
import retrofit2.Call
import retrofit2.http.*

/**
 * movies api list
 * @author Aymen Masmoudi
 * */
interface MoviesAPI {

    /**
     * get movies list from server
     * @param api api key for security
     * @param sort sort movies by
     * @param page number of page for navigation
     * @return json object
     * */
    @GET("discover/movie")
    fun getMoviesList(
        @Query(APIKEY) api: String,
        @Query(SORTBY) sort: String,
        @Query(PAGE) page: Int
    ): Call<MoviesList>

    /**
     * get movie details by id from server
     * @param id actor id
     * @param api api key for security
     * @return json object
     * */
    @GET("movie/{id}")
    fun getMovieDetails(
        @Path(ID) id: Int,
        @Query(APIKEY) api: String
    ): Call<MovieDetails>

    companion object{
        const val ID = "id"
        const val APIKEY = "api_key"
        const val SORTBY = "sort_by"
        const val PAGE = "page"
    }

}
