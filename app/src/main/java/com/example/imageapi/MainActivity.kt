package com.example.imageapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val quotesApi = RetrofitHelper().getInstance().create(ImageApi::class.java)
        // launching a new coroutine
        GlobalScope.launch {
            var response = quotesApi.getImages()
            runOnUiThread{
                response.body()?.let {
//                    findViewById<TextView>(R.id.ApiView).text = it.results.first()?.author
                    val adapter = MyAdapter(it.photos)
                    recyclerView.adapter=adapter
                }
            }
        }
    }
}