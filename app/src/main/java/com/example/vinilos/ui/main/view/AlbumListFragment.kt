package com.example.vinilos.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.AlbumResponse
import com.example.vinilos.ui.base.ViewModelFactory
import com.example.vinilos.ui.main.adapter.HomeAdapter
import com.example.vinilos.ui.main.viewmodel.HomeViewModel
import com.vinylsMobile.vinylsapplication.databinding.FragmentAlbumListBinding
import com.example.vinilos.utils.Status
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumListFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter
    private lateinit var binding: FragmentAlbumListBinding

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = HomeAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        homeViewModel.getAlbums().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { albums -> retrieveList(albums) }
                    }
                    Status.ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAlbumListBinding.inflate(layoutInflater, container, false)
        setupUI()
        bindMenuEvents()
        setupViewModel()
        setupObservers()
        return binding.root
    }

    private fun bindMenuEvents(){
        val createAlbumMenuButton: FloatingActionButton = binding.btnFabCreateAlbum
        createAlbumMenuButton.setOnClickListener { view ->
            launchAlbumCreateActivity(view)
        }

    }

    private fun launchAlbumCreateActivity(view: View) {
        val intent = Intent(activity, CreateAlbumActivity::class.java)
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(): AlbumListFragment {
            return AlbumListFragment()
        }
    }

    private fun retrieveList(albums: List<AlbumResponse>) {
        adapter.apply {
            addAlbums(albums)
            notifyDataSetChanged()
        }
    }
}