package com.ezatpanah.retrofit_youtube.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.retrofit_youtube.adapter.MoviesAdapter
import com.ezatpanah.retrofit_youtube.api.ApiClient
import com.ezatpanah.retrofit_youtube.api.ApiServices
import com.ezatpanah.retrofit_youtube.databinding.ActivityMainBinding
import com.ezatpanah.retrofit_youtube.response.MoviesListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val moviesAdapter by lazy { MoviesAdapter() }

    private val api: ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitViews
        binding.apply {
            //show loading
            prgBarMovies.visibility = View.VISIBLE
            //Call movies api
            val callMoviesApi = api.getPopularMovie(1)
            callMoviesApi.enqueue(object : Callback<MoviesListResponse> {
                override fun onResponse(call: Call<MoviesListResponse>, response: Response<MoviesListResponse>) {
                    Log.e("onFailure", "Err : ${response.code()}")

                    prgBarMovies.visibility = View.GONE
                    if (response.isSuccessful) {
                        response.body()?.let { itBody ->
                            itBody.results.let { itData ->
                                if (itData.isNotEmpty()) {
                                    moviesAdapter.differ.submitList(itData)
                                    //Recycler
                                    rlMovies.apply {
                                        layoutManager = LinearLayoutManager(this@MainActivity)
                                        adapter = moviesAdapter
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }
            })
        }
    }
}