package com.example.vinilos.ui.main.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.AlbumModel
import com.example.vinilos.ui.base.ViewModelFactory
import com.example.vinilos.ui.main.viewmodel.HomeViewModel
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ActivityCreateAlbumBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class CreateAlbumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAlbumBinding
    private lateinit var homeViewModel: HomeViewModel

    private var datePickerDialog: DatePickerDialog? = null
    private var selected_date: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Crear Album"
        supportActionBar?.subtitle = "Album"


        val generos = resources.getStringArray(R.array.list_generos)
        val arrayAdapter_genero = ArrayAdapter(this, R.layout.drop_items, generos)
        binding.autoCompleteGeneros.setAdapter(arrayAdapter_genero)

        val nameAlbum = intent.getStringExtra("nameAlbum")
        val postButton: Button = binding.btnCreateTrackAlbum

        val records = resources.getStringArray(R.array.list_records)
        val arrayAdapter_records = ArrayAdapter(this, R.layout.drop_items, records)
        binding.autoCompleteRecords.setAdapter(arrayAdapter_records)

        postButton.setOnClickListener {
            val albumName = binding.txtNameAlbum.text.toString()
            val coverImage = binding.txtUrlCoverAlbum.text.toString()
            val genreText = binding.textInputLayoutGenero.editText!!.text.toString()
            val recordLabel = binding.textInputLayoutRecord.editText!!.text.toString()
            val description = binding.txtDescriptionAlbum.text.toString()
            val dateCreation = binding.txtDateAlbum.text.toString()

            val dateFormatted = SimpleDateFormat("yyyy-MM-dd").format(
                SimpleDateFormat("dd/MM/yyyy").parse(dateCreation)
            )
            val releaseDate = dateFormatted.toString() + "T00:00:00-05:00"
            val album =
                AlbumModel(albumName, coverImage, genreText, recordLabel, description, releaseDate)
            createAlbum(album)
            this.finish()
        }


    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[HomeViewModel::class.java]
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
        if (id == R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createAlbum(album: AlbumModel) {
        lifecycleScope.launch {
            homeViewModel.createAlbumPost(album)
            val toast =
                Toast.makeText(applicationContext, "Se ha creado el album", Toast.LENGTH_LONG)
            toast.show()
        }

    }

}