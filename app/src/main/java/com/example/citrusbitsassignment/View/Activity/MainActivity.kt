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
import com.emedinaa.kotlinmvvm.model.User
import com.emedinaa.kotlinmvvm.viewmodel.UserViewModel
import com.emedinaa.kotlinmvvm.viewmodel.ViewModelFactory
import com.example.citrusbitsassignment.R
import com.example.citrusbitsassignment.View.Adapter.UserAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI(){
        adapter= UserAdapter(viewModel.user.value?: emptyList(),this)
        userRecyclerView.layoutManager= LinearLayoutManager(this)
        val divider = DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this@MainActivity,R.drawable.item_separator)!!)
        userRecyclerView.addItemDecoration(divider)
        userRecyclerView.adapter= adapter
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(UserViewModel::class.java)
        viewModel = ViewModelProvider(this, ViewModelFactory(Injection.providerRepository())).get(UserViewModel::class.java)
        viewModel = ViewModelProviders.of(this,ViewModelFactory(Injection.providerRepository())).get(UserViewModel::class.java)
        viewModel.user.observe(this,renderUsers)

        viewModel.isViewLoading.observe(this,isViewLoadingObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
        viewModel.isEmptyList.observe(this,emptyListObserver)
    }

    //observers
    private val renderUsers= Observer<List<User>> {
        layoutError.visibility=View.GONE
        layoutEmpty.visibility=View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver= Observer<Boolean> {
        val visibility=if(it) View.VISIBLE else View.GONE
        progressBar.visibility= visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        layoutError.visibility=View.VISIBLE
        layoutEmpty.visibility=View.GONE
    }

    private val emptyListObserver= Observer<Boolean> {
        layoutError.visibility=View.GONE
        layoutEmpty.visibility=View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUsersData()
    }
}