package com.ezatpanah.retrofit_youtube.api

import com.ezatpanah.retrofit_youtube.response.MovieDetails
import com.ezatpanah.retrofit_youtube.response.MoviesListResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    //    https://api.themoviedb.org/3/movie/550?api_key=***
    //    https://api.themoviedb.org/3/movie/popular?api_key=***
    //    https://api.themoviedb.org/3/

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Call<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Call<MoviesListResponse>

}