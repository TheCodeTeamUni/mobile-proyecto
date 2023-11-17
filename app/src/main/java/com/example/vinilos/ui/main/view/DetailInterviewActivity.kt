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
import com.example.vinilos.data.model.InterviewResponse
import com.example.vinilos.network.CacheManager
import com.example.vinilos.ui.base.InterviewViewModelFactory
import com.example.vinilos.ui.main.adapter.ID
import com.example.vinilos.ui.main.adapter.InterviewDetailAdapter
import com.example.vinilos.ui.main.adapter.NAME
import com.example.vinilos.ui.main.viewmodel.InterviewViewModel
import com.example.vinilos.utils.Status
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailInterviewBinding

class DetailInterviewActivity : AppCompatActivity() {
    private lateinit var interviewViewModel: InterviewViewModel
    private lateinit var adapter: InterviewDetailAdapter

    private lateinit var binding: ActivityDetailInterviewBinding
    private lateinit var idInterview: String
    private lateinit var nameInterview: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailInterviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getStringExtra(ID)!!
        idInterview = id
        val name = intent.getStringExtra(NAME)!!
        nameInterview = name

        setupViewModel()
        setupObservers(id)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.submenu_project, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_project_add_song -> {
                launchAlbumTrackActivityView(idInterview, nameInterview)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        interviewViewModel = ViewModelProviders.of(
            this,
            InterviewViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[InterviewViewModel::class.java]
    }

    private fun getArtistObservers(id: String) {
        var potentialResp =
            CacheManager.getInstance(application.applicationContext).getInterview(id.toInt())

        if (potentialResp == null) {
            Log.d("Cache decision", "Se saca de la red")
            setupObservers(id)
        } else {
            Log.d("Cache decision", "return ${potentialResp.nameProject} elements from cache")
            retrieveInterviewDetail(
                potentialResp,
                false
            )
        }
    }

    private fun setupObservers(id: String) {
        interviewViewModel.getInterviewDetail(id).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { interviewDetail ->
                            retrieveInterviewDetail(
                                interviewDetail,
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

    private fun retrieveInterviewDetail(interview: InterviewResponse, b: Boolean) {
        CacheManager.getInstance(application.applicationContext)
            .addInterview(interview.aspirants.toInt(), interview)
        adapter = InterviewDetailAdapter(interview)
        adapter.adaptData(binding)
        supportActionBar?.title = interview.nameProject
        supportActionBar?.subtitle = "Album"
    }

    private fun launchAlbumTrackActivityView(albumId: String, albumName: String) {
        val intent = Intent(this, ProjectTrackActivity::class.java)
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