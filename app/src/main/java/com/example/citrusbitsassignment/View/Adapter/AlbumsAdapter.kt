package com.example.citrusbitsassignment.View.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emedinaa.kotlinmvvm.model.Albums
import com.example.citrusbitsassignment.R
import com.example.citrusbitsassignment.View.Activity.PhotosActivity
import kotlinx.android.synthetic.main.albums_adapter_layout.view.*

class AlbumsAdapter(private var albumsList:List<Albums>, val context: Context):RecyclerView.Adapter<AlbumsAdapter.MViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.albums_adapter_layout, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        vh.bind(albumsList[position],context)

    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    fun update(data:List<Albums>){
        albumsList= data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val textViewTitle:TextView = view.title
        private val parentLayout:RelativeLayout = view.parentLayout
        fun bind(albums: Albums,context: Context){
            textViewTitle.text = albums.title
            parentLayout.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, PhotosActivity::class.java)
                intent.putExtra("albumsId",albums.id);
                context.startActivity(intent);
            })
        }
    }
}