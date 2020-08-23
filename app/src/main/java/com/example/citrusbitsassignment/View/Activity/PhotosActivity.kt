package com.example.citrusbitsassignment.View.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.emedinaa.kotlinmvvm.di.Injection
import com.emedinaa.kotlinmvvm.model.Photos
import com.emedinaa.kotlinmvvm.viewmodel.PhotosModelFactory
import com.emedinaa.kotlinmvvm.viewmodel.PhotosViewModel
import com.example.citrusbitsassignment.R
import com.example.citrusbitsassignment.View.Adapter.PhotosAdapter
import kotlinx.android.synthetic.main.activity_albums.*
import kotlinx.android.synthetic.main.activity_photos.*

class PhotosActivity : AppCompatActivity() {
    private var albumsId: Int = 0
    private lateinit var viewModel: PhotosViewModel
    private lateinit var adapter: PhotosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        setupViewModel()
        setupUI()
    }
    private fun setupUI(){

        albumsId = intent.getIntExtra("albumsId",0)
        adapter= PhotosAdapter(viewModel.photo.value?: emptyList(),this)
        photoRecyclerView.layoutManager = GridLayoutManager(getApplicationContext(),3);//LinearLayoutManager(this)
        val divider = DividerItemDecoration(this@PhotosActivity, DividerItemDecoration.HORIZONTAL)
        divider.setDrawable(ContextCompat.getDrawable(this@PhotosActivity,R.drawable.item_separator)!!)
        photoRecyclerView.addItemDecoration(divider)
        photoRecyclerView.adapter= adapter
    }
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, Injection.providePhotosViewModelFactory()).get(PhotosViewModel::class.java)
        viewModel = ViewModelProvider(this, PhotosModelFactory(Injection.providerPhotosRepository())).get(PhotosViewModel::class.java)
        viewModel = ViewModelProviders.of(this, PhotosModelFactory(Injection.providerPhotosRepository())).get(PhotosViewModel::class.java)
        viewModel.photo.observe(this,renderPhotos)

        viewModel.isViewLoading.observe(this,isViewLoadingObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
        viewModel.isEmptyList.observe(this,emptyListObserver)
    }

    //observers
    private val renderPhotos= Observer<List<Photos>> {
        val albumsList = arrayListOf<Photos>()
        for (photo in it) {
            if(photo.albumId == albumsId)
                albumsList.add(photo)
        }
        layoutPhotosEmpty.visibility=View.GONE
        layoutPhotosError.visibility=View.GONE
        adapter.update(albumsList)
    }

    private val isViewLoadingObserver= Observer<Boolean> {
        val visibility=if(it) View.VISIBLE else View.GONE
        photosProgressBar.visibility= visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        layoutPhotosEmpty.visibility=View.GONE
        layoutPhotosError.visibility=View.VISIBLE

    }

    private val emptyListObserver= Observer<Boolean> {
        layoutPhotosEmpty.visibility=View.VISIBLE
        layoutPhotosError.visibility=View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUsersData()
    }
}