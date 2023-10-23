package com.example.vinilos.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.AlbumResponse
import com.example.vinilos.network.CacheManager
import com.example.vinilos.ui.base.ViewModelFactory
import com.example.vinilos.ui.main.adapter.DetailAdapter
import com.example.vinilos.ui.main.adapter.ID
import com.example.vinilos.ui.main.adapter.NAME
import com.example.vinilos.ui.main.viewmodel.HomeViewModel
import com.example.vinilos.utils.Status
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailAlbumBinding

class DetailAlbumActivity : AppCompatActivity() {
    private lateinit var mainViewModel: HomeViewModel
    private lateinit var adapter: DetailAdapter

    private lateinit var binding: ActivityDetailAlbumBinding
    private lateinit var idAlbum: String
    private lateinit var nameAlbum: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getStringExtra(ID)!!
        idAlbum = id
        val name = intent.getStringExtra(NAME)!!
        nameAlbum = name

        setupViewModel()
        setupObservers(id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.submenu_album, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_album_add_song -> {
                launchAlbumTrackActivityView(idAlbum, nameAlbum)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[HomeViewModel::class.java]
    }

    private fun getArtistObservers(id: String) {
        var potentialResp =
            CacheManager.getInstance(application.applicationContext).getAlbum(id.toInt())

        if (potentialResp == null) {
            Log.d("Cache decision", "Se saca de la red")
            setupObservers(id)
        } else {
            Log.d("Cache decision", "return ${potentialResp.name} elements from cache")
            retrieveAlbumDetail(
                potentialResp,
                false
            )
        }
    }

    private fun setupObservers(id: String) {
        mainViewModel.getAlbumDetail(id).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { albumDetail ->
                            retrieveAlbumDetail(
                                albumDetail,
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

    private fun retrieveAlbumDetail(album: AlbumResponse, b: Boolean) {
        CacheManager.getInstance(application.applicationContext).addAlbum(album.id.toInt(), album)
        adapter = DetailAdapter(album)
        adapter.adaptData(binding)
        supportActionBar?.title = album.name
        supportActionBar?.subtitle = "Album"
    }

    private fun launchAlbumTrackActivityView(albumId: String, albumName: String) {
        val intent = Intent(this, AlbumTrackActivity::class.java)
        intent.putExtra("idAlbum", albumId)
        intent.putExtra("nameAlbum", albumName)
        startActivity(intent)
//        this.finish()
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