package com.example.citrusbitsassignment.View.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.emedinaa.kotlinmvvm.model.Photos
import com.example.citrusbitsassignment.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photos_adapter_layout.view.*

class PhotosAdapter(private var photosList:List<Photos>, val context: Context):RecyclerView.Adapter<PhotosAdapter.MViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photos_adapter_layout, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        vh.bind(photosList[position],context)

    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    fun update(data:List<Photos>){
        photosList= data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val photoImageView:ImageView = view.photoImageView
        fun bind(photos: Photos,context: Context){
            Picasso.get()
                .load(photos.thumbnailUrl)
                .into(photoImageView)
            photoImageView.setOnClickListener(View.OnClickListener {
                val intent = Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(photos.url),"image/*");
                context.startActivity(Intent.createChooser(intent, "Image"));
            })

        }
    }
}