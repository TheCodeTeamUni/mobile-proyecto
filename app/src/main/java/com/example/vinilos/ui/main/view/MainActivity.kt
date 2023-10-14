package com.example.vinilos.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinylsMobile.vinylsapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.visitorButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, SelectProfileActivity::class.java)
            startActivity(intent)
        }

    }
}
/*private lateinit var mainViewModel: MainViewModel
private lateinit var adapter: MainAdapter
private lateinit var binding: ActivityMainBinding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
    setupUI()
    setupViewModel()
    setupObservers()
}

private fun setupUI() {
    binding.recyclerView.layoutManager = LinearLayoutManager(this)
    adapter = MainAdapter(arrayListOf())
    binding.recyclerView.addItemDecoration(
        DividerItemDecoration(
            binding.recyclerView.context,
            (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
        )
    )
    binding.recyclerView.adapter = adapter
}

private fun setupObservers() {
    mainViewModel.getAlbums().observe(this, Observer {
        it?.let { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    //binding.progressBar.visibility = View.GONE
                    resource.data?.let { albums -> retrieveList(albums) }
                }
                Status.ERROR-> {
                    binding.recyclerView.visibility = View.VISIBLE
                    //binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    //binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        }
    })
}


private fun setupViewModel() {
    mainViewModel = ViewModelProviders.of(
        this,
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    ).get(MainViewModel::class.java)
}

private fun retrieveList(albums: List<albumResponse>) {
    adapter.apply {
        addAlbums(albums)
        notifyDataSetChanged()
    }
}
}*/