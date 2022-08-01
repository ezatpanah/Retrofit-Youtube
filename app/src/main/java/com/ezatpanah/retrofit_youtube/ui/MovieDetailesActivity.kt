package com.ezatpanah.retrofit_youtube.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.size.Scale
import com.ezatpanah.retrofit_youtube.R
import com.ezatpanah.retrofit_youtube.api.ApiClient
import com.ezatpanah.retrofit_youtube.api.ApiServices
import com.ezatpanah.retrofit_youtube.databinding.ActivityMovieDetailesBinding
import com.ezatpanah.retrofit_youtube.response.MovieDetails
import com.ezatpanah.retrofit_youtube.utils.Constants.POSTER_BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MovieDetailesActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailesBinding

    private val api: ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieId: Int = intent.getIntExtra("id", 1)
        binding.apply {
            //show loading
            prgBarMovies.visibility = View.VISIBLE
            //Call movies api
            val callMoviesApi = api.getMovieDetails(movieId)
            callMoviesApi.enqueue(object : Callback<MovieDetails> {
                override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                    Log.e("onFailure", "Err : ${response.code()}")
                    prgBarMovies.visibility = View.GONE
                    if (response.isSuccessful) {
                        response.body()?.let { itBody ->
                            val moviePosterURL = POSTER_BASE_URL + itBody.posterPath
                            imgMovie.load(moviePosterURL) {
                                crossfade(true)
                                placeholder(R.drawable.poster_placeholder)
                                //scale(Scale.FILL)
                                scale(Scale.FILL)
                                // xml android:scaleType="fitXY"
                                // xml android:scaleType="centerCrop"

                            }
                            imgMovieBack.load(moviePosterURL) {
                                crossfade(true)
                                placeholder(R.drawable.poster_placeholder)
                                //scale(Scale.FILL)
                                scale(Scale.FILL)
                                // xml android:scaleType="fitXY"
                                // xml android:scaleType="centerCrop"

                            }

                            tvMovieTitle.text = itBody.title
                            tvMovieTagLine.text = itBody.tagline
                            tvMovieDateRelease.text = itBody.releaseDate
                            tvMovieRating.text = itBody.voteAverage.toString()
                            tvMovieRuntime.text = itBody.runtime.toString()
                            tvMovieBudget.text = itBody.budget.toString()
                            tvMovieRevenue.text = itBody.revenue.toString()
                            tvMovieOverview.text = itBody.overview
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }
            })
        }


    }

}