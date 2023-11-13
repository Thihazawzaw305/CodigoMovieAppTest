package com.codingtest.moviecodingtest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.codingtest.moviecodingtest.R
import com.codingtest.moviecodingtest.adapters.MovieAdapter
import com.codingtest.moviecodingtest.adapters.PopularMovieAdapter
import com.codingtest.moviecodingtest.data.vo.MovieVO
import com.codingtest.moviecodingtest.databinding.ActivityHomeBinding
import com.codingtest.moviecodingtest.mvp.presenters.HomePresenterImpl
import com.codingtest.moviecodingtest.mvp.views.HomeView
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity(), HomeView{
    lateinit var binding: ActivityHomeBinding
    lateinit var mPopularMovieAdapter: PopularMovieAdapter
    lateinit var mUpcomingMovieAdapter: MovieAdapter

    val viewModel by viewModels<HomePresenterImpl>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        setUpTabLayout()

        viewModel.initView(this)
        viewModel.onUiReady(this, this)

    }

    private fun setUpRecyclerView(){
        mUpcomingMovieAdapter = MovieAdapter(viewModel)
        binding.rvUpcomingMovie.adapter = mUpcomingMovieAdapter

        mPopularMovieAdapter = PopularMovieAdapter(viewModel)
        binding.rvPopularMovie.adapter = mPopularMovieAdapter
    }

    private fun setUpTabLayout(){
        listOf("Movies","Events","Plays","Sports","Activities").forEach { genre ->
            binding.tabLayoutGeneres.newTab().apply {
                text = genre
                binding.tabLayoutGeneres.addTab(this)
            }
        }
    }

    override fun navigateToMovieDetailScreen(id: Int) {
        startActivity(MovieDetailActivity.newIntent(this,id))
    }

    override fun showLoading() {
       Snackbar.make(window.decorView,"Loading",Snackbar.LENGTH_SHORT).show()
    }

    override fun showPopularMovies(movieList: List<MovieVO>) {
        mPopularMovieAdapter.setNewData(movieList)
    }

    override fun showUpcomingMovies(movieList: List<MovieVO>) {
        mUpcomingMovieAdapter.setNewData(movieList)

    }

    override fun showErrorMessage(message: String) {
        Snackbar.make(window.decorView,message,Snackbar.LENGTH_SHORT).show()
    }
}