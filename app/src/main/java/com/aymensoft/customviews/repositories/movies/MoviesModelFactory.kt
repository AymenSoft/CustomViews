package com.aymensoft.customviews.repositories.movies

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MoviesModelFactory constructor(private val repository: MoviesRepository, application: Application
): ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MoviesViewModel::class.java)){
            MoviesViewModel(this.repository) as T
        }else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}