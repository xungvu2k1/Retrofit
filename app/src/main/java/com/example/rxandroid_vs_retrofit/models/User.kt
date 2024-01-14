package com.example.rxandroid_vs_retrofit.models

data class User(
    val id:Int,
    val name:String,
    val job: Job,
    val favorites: List<Favorites>
)
