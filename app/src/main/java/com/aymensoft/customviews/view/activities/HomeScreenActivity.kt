package com.aymensoft.customviews.view.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aymensoft.customviews.R
import com.aymensoft.customviews.databinding.ActivityHomeScreenBinding
import com.aymensoft.customviews.repositories.movies.models.MoviesItem
import com.aymensoft.customviews.repositories.movies.MoviesModelFactory
import com.aymensoft.customviews.repositories.movies.MoviesRepository
import com.aymensoft.customviews.repositories.movies.MoviesViewModel
import com.aymensoft.customviews.view.activities.adapters.MoviesAdapter

/**
 * show list of movies
 * click on movie to show details
 * @author Aymen Masmoudi
 * */
class HomeScreenActivity: AppCompatActivity(), MoviesAdapter.ClickListener {

    private lateinit var binding: ActivityHomeScreenBinding

    private var page = 1

    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var adapter: MoviesAdapter
    private lateinit var arrayList: ArrayList<MoviesItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesViewModel = ViewModelProvider(this, MoviesModelFactory(MoviesRepository(), application)).get(MoviesViewModel::class.java)

        arrayList = ArrayList()
        adapter = MoviesAdapter(this@HomeScreenActivity)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            this@HomeScreenActivity,
            LinearLayoutManager.VERTICAL,
            false)
        binding.rvData.layoutManager = mLayoutManager
        binding.rvData.adapter = adapter

        //load more movies when scrolling to the bottom
        binding.rvData.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    page+=1
                    binding.srlListview.isRefreshing = true
                    moviesViewModel.getAllMovies(page)
                }
            }
        })

        //swipe to refresh movies list
        binding.srlListview.setOnRefreshListener {
            page = 1
            arrayList.clear()
            adapter.setMovies(arrayList)
            binding.tvLoading.visibility = View.VISIBLE
            binding.tvLoading.text = resources.getString(R.string.loading)
            binding.srlListview.isRefreshing = true
            moviesViewModel.getAllMovies(page)
        }

        moviesViewModel.getAllMovies(page)
        observeMoviesList()

    }

    private fun observeMoviesList(){
        moviesViewModel.moviesList.observe(this) {
            binding.srlListview.isRefreshing = false
            arrayList.addAll(it)
            if (arrayList.isEmpty()){
                binding.tvLoading.visibility= View.VISIBLE
                binding.tvLoading.text=resources.getString(R.string.empty_list)
            }else {
                binding.tvLoading.visibility= View.GONE
            }
            adapter.setMovies(arrayList)
        }
        moviesViewModel.errorMessage.observe(this){
            binding.srlListview.isRefreshing = false
            binding.tvLoading.visibility=View.VISIBLE
            binding.tvLoading.text=resources.getString(R.string.empty_list)
        }
    }

    //use search from action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_screen, menu)
        // Associate searchable configuration with the SearchView
        val searchViewItem = menu!!.findItem(R.id.menu_search_name)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchViewItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_name)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isIconifiedByDefault = false // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    //start movie details activity when click from the list
    override fun onItemClickListener(position: Int) {
        val details = Intent(this@HomeScreenActivity, MovieDetailsActivity::class.java)
        details.putExtra("movieId", arrayList[position].id)
        startActivity(details)
    }

}