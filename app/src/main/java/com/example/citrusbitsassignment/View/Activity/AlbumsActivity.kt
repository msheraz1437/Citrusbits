package com.example.citrusbitsassignment.View.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.emedinaa.kotlinmvvm.di.Injection
import com.emedinaa.kotlinmvvm.model.Albums
import com.emedinaa.kotlinmvvm.model.User
import com.emedinaa.kotlinmvvm.viewmodel.AlbumsModelFactory
import com.emedinaa.kotlinmvvm.viewmodel.AlbumsViewModel
import com.emedinaa.kotlinmvvm.viewmodel.UserViewModel
import com.emedinaa.kotlinmvvm.viewmodel.ViewModelFactory
import com.example.citrusbitsassignment.R
import com.example.citrusbitsassignment.View.Adapter.AlbumsAdapter
import com.example.citrusbitsassignment.View.Adapter.UserAdapter
import kotlinx.android.synthetic.main.activity_albums.*
import kotlinx.android.synthetic.main.activity_main.*

class AlbumsActivity : AppCompatActivity() {
    private lateinit var viewModel: AlbumsViewModel
    private lateinit var adapter: AlbumsAdapter
    private var userId:Int = 0;
    private var userName:String = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        setupViewModel()
        setUpUi()

    }

    private fun setUpUi() {
        userId = intent.getIntExtra("userId",0)
        userName = intent.getStringExtra("userName")!!
        userNameTextVIew.text = userName
        adapter= AlbumsAdapter(viewModel.albums.value?: emptyList(),this)
        albumsRecyclerView.layoutManager= LinearLayoutManager(this)
        val divider = DividerItemDecoration(this@AlbumsActivity, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this@AlbumsActivity,R.drawable.item_separator)!!)
        albumsRecyclerView.addItemDecoration(divider)
        albumsRecyclerView.adapter= adapter
    }
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, Injection.provideAlbumsViewModelFactory()).get(AlbumsViewModel::class.java)
        viewModel = ViewModelProvider(this, AlbumsModelFactory(Injection.providerAlbumsRepository())).get(AlbumsViewModel::class.java)
        viewModel = ViewModelProviders.of(this, AlbumsModelFactory(Injection.providerAlbumsRepository())).get(AlbumsViewModel::class.java)
        viewModel.albums.observe(this,renderAlbums)

        viewModel.isViewLoading.observe(this,isViewLoadingObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
        viewModel.isEmptyList.observe(this,emptyListObserver)
    }

    //observers
    private val renderAlbums= Observer<List<Albums>> {

        val albumsList = arrayListOf<Albums>()
        for (albums in it) {
            if(albums.userId == userId)
                albumsList.add(albums)
        }
        layoutAlbumsError.visibility=View.GONE
        layoutAlbumsEmpty.visibility=View.GONE
        adapter.update(albumsList)
    }

    private val isViewLoadingObserver= Observer<Boolean> {
        val visibility=if(it) View.VISIBLE else View.GONE
        albumsProgressBar.visibility= visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        layoutAlbumsError.visibility=View.VISIBLE
        layoutAlbumsEmpty.visibility=View.GONE
    }

    private val emptyListObserver= Observer<Boolean> {
        layoutAlbumsError.visibility=View.GONE
        layoutAlbumsEmpty.visibility=View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUsersData()
    }
}