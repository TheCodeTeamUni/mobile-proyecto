package com.example.vinilos.ui.main.view

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.CandidateDetailResponse
import com.example.vinilos.ui.main.adapter.CandidateDetailAdapter
import com.example.vinilos.ui.main.adapter.ID
import com.example.vinilos.ui.main.viewmodel.CandidateViewModel
import com.example.vinilos.ui.main.viewmodel.CandidateViewModelFactory
import com.example.vinilos.utils.Status
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailCandidateBinding

class DetailCandidateActivity : AppCompatActivity() {
    lateinit var context: Context

    private lateinit var candidateViewModel: CandidateViewModel
    private lateinit var adapter: CandidateDetailAdapter

    private lateinit var binding: ActivityDetailCandidateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailCandidateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getStringExtra(ID)!!
        setupViewModel()
        setupCandidateObservers(id)
    }


    private fun setupViewModel() {
        candidateViewModel = ViewModelProviders.of(
            this,
            CandidateViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(CandidateViewModel::class.java)
    }

    private fun setupCandidateObservers(id:String) {
        candidateViewModel.getCandidateDetail(id).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { candidateDetail -> retrieveCandidateDetail(candidateDetail) }
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

    private fun retrieveCandidateDetail(candidateDetail: CandidateDetailResponse) {
        adapter = CandidateDetailAdapter(candidateDetail)
        adapter.adaptData(binding)
        supportActionBar?.title =candidateDetail.name
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