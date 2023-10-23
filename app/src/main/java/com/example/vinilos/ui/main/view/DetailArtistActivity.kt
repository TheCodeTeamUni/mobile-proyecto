package com.example.vinilos.ui.main.view

import android.R
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.ArtistResponse
import com.example.vinilos.network.CacheManager
import com.example.vinilos.ui.base.ArtistViewModelFactory
import com.example.vinilos.ui.main.adapter.DetailArtistAdapter
import com.example.vinilos.ui.main.adapter.IdArtist
import com.example.vinilos.ui.main.adapter.artist
import com.example.vinilos.ui.main.viewmodel.ArtistViewModel
import com.example.vinilos.utils.Status
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailArtistBinding

class DetailArtistActivity : AppCompatActivity() {
    private lateinit var artistViewModel: ArtistViewModel
    private lateinit var adapter: DetailArtistAdapter

    private lateinit var binding: ActivityDetailArtistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityDetailArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val artistType = intent.getStringExtra(artist)!!

        setupViewModel()

        if (artistType == "Band") {
            setupBandObservers(intent.getStringExtra(IdArtist)!!)
        } else {
            getMusiciansObservers(intent.getStringExtra(IdArtist)!!)
        }

    }

    private fun setupViewModel() {
        artistViewModel = ViewModelProviders.of(
            this,
            ArtistViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[ArtistViewModel::class.java]
    }

    private fun getMusiciansObservers(id: String) {
        var potentialResp =
            CacheManager.getInstance(application.applicationContext).getArtist(id.toInt())

        if (potentialResp == null) {
            Log.d("Cache decision", "Se saca de la red")
            setupMusiciansObservers(id)
        } else {
            Log.d("Cache decision", "return ${potentialResp.name} elements from cache")
            retrieveArtistDetail(
                potentialResp,
                false
            )
        }
    }

    private fun setupMusiciansObservers(id: String) {
        artistViewModel.getMusiciansDetail(id).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { artistDetail ->
                            retrieveArtistDetail(
                                artistDetail,
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

    private fun setupBandObservers(id: String) {
        artistViewModel.getBandsDetail(id).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { artistDetail ->
                            retrieveArtistDetail(
                                artistDetail,
                                true
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

    private fun retrieveArtistDetail(artist: ArtistResponse, isBand: Boolean) {
        CacheManager.getInstance(application.applicationContext)
            .addArtist(artist.id.toInt(), artist)
        supportActionBar?.title = artist.name
        supportActionBar?.subtitle = "Artista"
        adapter = DetailArtistAdapter(artist, isBand)
        adapter.adaptData(binding)

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    // Override  supportActionBar?.setDisplayHomeAsUpEnabled , close current activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}