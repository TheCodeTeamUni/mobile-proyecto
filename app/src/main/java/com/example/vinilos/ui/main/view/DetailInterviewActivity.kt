package com.example.vinilos.ui.main.view

import android.content.Context
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
import com.example.vinilos.data.model.InterviewResponse
import com.example.vinilos.data.model.InterviewResultResponse
import com.example.vinilos.data.model.ProjectDetailResponse
import com.example.vinilos.network.CacheManager
import com.example.vinilos.ui.main.adapter.ID
import com.example.vinilos.ui.main.adapter.InterviewDetailAdapter
import com.example.vinilos.ui.main.adapter.NAME
import com.example.vinilos.ui.main.adapter.ProjectDetailAdapter
import com.example.vinilos.ui.main.viewmodel.InterviewViewModel
import com.example.vinilos.ui.main.viewmodel.InterviewViewModelFactory
import com.example.vinilos.ui.main.viewmodel.ProjectViewModel
import com.example.vinilos.ui.main.viewmodel.ProjectViewModelFactory
import com.example.vinilos.utils.Status
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailInterviewBinding
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailProjectBinding

class DetailInterviewActivity : AppCompatActivity() {
    lateinit var context: Context

    private lateinit var interviewViewModel: InterviewViewModel
    private lateinit var adapter: InterviewDetailAdapter

    private lateinit var binding: ActivityDetailInterviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailInterviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getStringExtra(ID)!!
        setupViewModel()
        setupInterviewObservers(id)
    }


    private fun setupViewModel() {
        interviewViewModel = ViewModelProviders.of(
            this,
            InterviewViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(InterviewViewModel::class.java)
    }

    private fun setupInterviewObservers(id:String) {
        interviewViewModel.getInterviewDetail(id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { interviewDetail -> retrieveInterviewDetail(interviewDetail) }
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

    private fun retrieveInterviewDetail(interviewDetail: InterviewResultResponse) {
        adapter = InterviewDetailAdapter(interviewDetail)
        adapter.adaptData(binding)
        supportActionBar?.title =interviewDetail.result
        context.startActivity(intent)
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