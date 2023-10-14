package com.example.vinilos.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.CollectorResponse
import com.example.vinilos.network.CacheManager
import com.example.vinilos.ui.base.CollectorViewModelFactory
import com.example.vinilos.ui.main.adapter.DetailCollectorAdapter
import com.example.vinilos.ui.main.adapter.IdCollector
import com.example.vinilos.ui.main.viewmodel.CollectorViewModel
import com.example.vinilos.utils.Status
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailCollectorBinding

class DetailCollectorActivity : AppCompatActivity() {
    private lateinit var collectorViewModel: CollectorViewModel
    private lateinit var adapter: DetailCollectorAdapter

    private lateinit var binding: ActivityDetailCollectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailCollectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViewModel()
        getCollectorsObservers(intent.getStringExtra(IdCollector)!!)


    }

    private fun setupViewModel() {
        collectorViewModel = ViewModelProviders.of(
            this,
            CollectorViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[CollectorViewModel::class.java]
    }

    private fun getCollectorsObservers(id: String) {
        var potentialResp = CacheManager.getInstance(application.applicationContext).getCollectors(id.toInt())

        if(potentialResp==null){
            Log.d("Cache decision", "Se saca de la red")
            setupCollectorObservers(id)
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.name} elements from cache")
            retrieveCollectorDetail(
                potentialResp,
                false
            )
        }
    }

    private fun setupCollectorObservers(id: String) {
        collectorViewModel.getCollectorsDetail(id).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { collectorDetail ->
                            retrieveCollectorDetail(
                                collectorDetail,
                                false
                            )
                        }
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

    private fun retrieveCollectorDetail(collector: CollectorResponse, b: Boolean) {
        CacheManager.getInstance(application.applicationContext).addCollector(collector.id.toInt(), collector)
        supportActionBar?.title = collector.name
        supportActionBar?.subtitle = "Coleccionista"
        adapter = DetailCollectorAdapter(collector)
        adapter.adaptData(binding)

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

    // Override  supportActionBar?.setDisplayHomeAsUpEnabled , close current activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}