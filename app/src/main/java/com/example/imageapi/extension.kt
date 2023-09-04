package com.example.imageapi

import android.widget.ImageView
import com.squareup.picasso.Picasso

// Using Extension Function
fun ImageView.loadImage(url:String){
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.noimage)
        .into(this)
}