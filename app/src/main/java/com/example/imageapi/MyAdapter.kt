package com.example.imageapi

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class MyAdapter(private val imageList: List<Result>):

    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title = view.findViewById<TextView>(R.id.imageTitle)
            val context = view.context
//            val description = view.findViewById<TextView>(R.id.imageDescription)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            val button=view.findViewById<Button>(R.id.downloadButton)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
            return MyViewHolder(layoutView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val imageItem = imageList[position]
            holder.title.text = imageItem.title
//            holder.description.text = imageItem.description
//            Picasso.get()
//                .load(imageItem.url)
//                .into(holder.imageView)
            holder.imageView.let{
                it.loadImage(imageItem.url)
            }
            holder.button.setOnClickListener{
                val request = DownloadManager.Request(Uri.parse(imageItem.url))
                    .setTitle("Sample File")
                    .setDescription("Downloading...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "sample.jpg")

                val downloadManager = holder.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)
            }

        }

        override fun getItemCount(): Int {
            return imageList.size
        }

//    // Using Extension Function
//        fun ImageView.loadImage(url:String){
//            Picasso.get()
//            .load(url)
//            .into(this)
//        }
    }
