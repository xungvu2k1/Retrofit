package com.example.rxandroid_vs_retrofit.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rxandroid_vs_retrofit.api.APIService
import com.example.rxandroid_vs_retrofit.databinding.ActivityMainBinding
import com.example.rxandroid_vs_retrofit.models.BeerAle
import com.example.rxandroid_vs_retrofit.models.Comment
import com.example.rxandroid_vs_retrofit.models.Favorites
import com.example.rxandroid_vs_retrofit.models.User
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val BASE_URL1: String = "https://api.sampleapis.com/"
    val BASE_URL2: String = "https://jsonplaceholder.typicode.com/"
    val TAG: String = "retrofit_intro"

    private lateinit var mActivityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mActivityMainBinding.root)

        val job = com.example.rxandroid_vs_retrofit.models.Job(1, "engineer")
        val favorite1 = Favorites(1, "candy")
        val favorites2 = Favorites(2, "cake")

        val user1 = User(1, "xung", job, listOf(favorite1, favorites2))

        val gson = Gson()
        val user1GsonStr = gson.toJson(user1)
        //kiem tra cau truc json trong log
        Log.e(TAG, "${user1GsonStr}")

        //check jsonformatter: https://jsonformatter.org/

        /*------------------------------------------------------------------------------
        ----------------link json để thực hành đọc dữ liệu qua api retrofit-------------
        -------------------------------------------------------------------------------*/
        mActivityMainBinding.btnCallapi.setOnClickListener{
            getAllBeerAle()
        }
        mActivityMainBinding.btnCallapicomment.setOnClickListener{
            getComments()
        }
    }

    private fun getComments() {
        val apiService: APIService = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

        //Đầu tiên, vào khai báo permission internet trong manifest
        //Sau đó:
        apiService.getComments().enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        mActivityMainBinding.tvPostId.text = it[0].postId.toString()
                        mActivityMainBinding.tvNameComment.text = it[0].name
                        mActivityMainBinding.tvEmailComment.text = it[0].email
                        mActivityMainBinding.tvBodyComment.text = it[0].body
                    }
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun getAllBeerAle() {
        val apiService: APIService = Retrofit.Builder()
            .baseUrl(BASE_URL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

        apiService.getBeerAles().enqueue(object : Callback<List<BeerAle>> {
            override fun onResponse(call: Call<List<BeerAle>>, response: Response<List<BeerAle>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val firstBeerAleItem = it[0]
                        mActivityMainBinding.tvPrice.text = firstBeerAleItem.price
                        mActivityMainBinding.tvName.text = firstBeerAleItem.name
                        mActivityMainBinding.tvRatingAverage.text =
                            firstBeerAleItem.rating.average.toString()
                        mActivityMainBinding.tvRatingReviews.text =
                            firstBeerAleItem.rating.reviews.toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<BeerAle>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
