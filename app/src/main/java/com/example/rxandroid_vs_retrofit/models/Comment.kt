package com.example.rxandroid_vs_retrofit.models

data class Comment(
    val postId : Int,
    val id : Int,
    val name : String,
    val email : String,
    val body : String
)
