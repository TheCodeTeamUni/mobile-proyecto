package com.example.vinilos.ui.main.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.CandidateDetailResponse
import com.example.vinilos.network.CacheManager
import com.example.vinilos.ui.main.adapter.CandidateDetailAdapter
import com.example.vinilos.ui.main.adapter.ID
import com.example.vinilos.ui.main.viewmodel.CandidateViewModel
import com.example.vinilos.ui.main.viewmodel.CandidateViewModelFactory
import com.example.vinilos.utils.Status
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailCandidateBinding

class DetailCandidateActivity : AppCompatActivity() {
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
        //getCandidateObservers(id)
    }


    private fun setupViewModel() {
        candidateViewModel = ViewModelProviders.of(
            this,
            CandidateViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(CandidateViewModel::class.java)
    }

    /*private fun getCandidateObservers(id: String) {
        var potentialResp = CacheManager.getInstance(application.applicationContext).getCandidates(id.toInt())

        if(potentialResp==null){
            Log.d("Cache decision", "Se saca de la red")
            setupCandidateObservers(id)
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.name} elements from cache")
            retrieveCandidateDetail(
                potentialResp,
                false
            )
        }
    }*/

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
        supportActionBar?.title = candidateDetail.name
        //context.startActivity(intent)
    }

    /*private fun retrieveCandidateDetail(candidate: CandidateDetailResponse, b: Boolean) {
        CacheManager.getInstance(application.applicationContext).addCandidate(candidate.idUser.toInt(), candidate)
        adapter = CandidateDetailAdapter(candidate)
        adapter.adaptData(binding)
        supportActionBar?.title = candidate.name
        supportActionBar?.subtitle = "Candidate"
    }*/

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