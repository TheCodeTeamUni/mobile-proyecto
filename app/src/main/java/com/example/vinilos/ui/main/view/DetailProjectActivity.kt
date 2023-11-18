package com.example.vinilos.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.ProjectDetailResponse
import com.example.vinilos.network.CacheManager
import com.example.vinilos.ui.main.adapter.ID
import com.example.vinilos.ui.main.adapter.NAME
import com.example.vinilos.ui.main.adapter.ProjectDetailAdapter
import com.example.vinilos.ui.main.viewmodel.ProjectViewModel
import com.example.vinilos.ui.main.viewmodel.ProjectViewModelFactory
import com.example.vinilos.utils.Status
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailProjectBinding

class DetailProjectActivity : AppCompatActivity() {
    private lateinit var projectViewModel: ProjectViewModel
    private lateinit var adapter: ProjectDetailAdapter

    private lateinit var binding: ActivityDetailProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getStringExtra(ID)!!
        setupViewModel()
        setupProjectObservers(id)
    }


    private fun setupViewModel() {
        projectViewModel = ViewModelProviders.of(
            this,
            ProjectViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(ProjectViewModel::class.java)
    }

    private fun setupProjectObservers(id:String) {
        projectViewModel.getProjectDetail(id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { projectDetail -> retrieveProjectDetail(projectDetail) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun retrieveProjectDetail(projectDetail: ProjectDetailResponse) {
        adapter = ProjectDetailAdapter(projectDetail)
        adapter.adaptData(binding)
        supportActionBar?.title =projectDetail.nameProject
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

}