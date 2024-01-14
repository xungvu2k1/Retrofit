package com.example.rxandroid_vs_retrofit.api

import com.example.rxandroid_vs_retrofit.models.BeerAle
import com.example.rxandroid_vs_retrofit.models.Comment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIService {
    @GET("beers/ale")
    fun getBeerAles(): Call<List<BeerAle>>

    @GET("comments")
    fun getComments() : Call<List<Comment>>
}