package com.example.imageapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val quotesApi = RetrofitHelper().getInstance().create(ImageApi::class.java)

        adapter = MyAdapter(emptyList())
        recyclerView.adapter = adapter

        // launching a new coroutine
        GlobalScope.launch {
            val response = quotesApi.getImages()
            runOnUiThread {
                response.body()?.let {
                    adapter.updateData(it.photos)
                }
            }
        }

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterByTitle(newText.orEmpty())
                return true
            }
        })
    }
}