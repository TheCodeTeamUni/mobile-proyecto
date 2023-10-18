package com.example.vinilos.ui.main.view

import android.annotation.SuppressLint
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
import com.example.vinilos.data.model.CollectorResponse
import com.example.vinilos.ui.base.CollectorViewModelFactory
import com.example.vinilos.ui.main.adapter.CollectorAdapter
import com.example.vinilos.ui.main.viewmodel.CollectorViewModel
import com.example.vinilos.utils.Status
import com.vinylsMobile.vinylsapplication.databinding.FragmentCollectorListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CollectorListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CollectorListFragment : Fragment() {

    private lateinit var binding: FragmentCollectorListBinding
    private lateinit var collectorViewModel: CollectorViewModel
    private lateinit var adapter: CollectorAdapter

    private fun setupUI() {
        binding.recyclerViewCollector.layoutManager = LinearLayoutManager(this.context)
        adapter = CollectorAdapter(arrayListOf())
        binding.recyclerViewCollector.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerViewCollector.context,
                (binding.recyclerViewCollector.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerViewCollector.adapter = adapter
    }

    private fun setupObservers() {
        collectorViewModel.getColelctors().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerViewCollector.visibility = View.VISIBLE
                        binding.progressBarCollector.visibility = View.GONE
                        resource.data?.let { collectors -> retrieveList(collectors) }
                    }
                    Status.ERROR -> {
                        binding.recyclerViewCollector.visibility = View.VISIBLE
                        binding.progressBarCollector.visibility = View.GONE
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBarCollector.visibility = View.VISIBLE
                        binding.recyclerViewCollector.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        collectorViewModel = ViewModelProviders.of(
            this,
            CollectorViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[CollectorViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCollectorListBinding.inflate(layoutInflater, container, false)
        setupUI()
        setupViewModel()
        setupObservers()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveList(collectors: List<CollectorResponse>) {
        adapter.apply {
            addCollectors(collectors)
            notifyDataSetChanged()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ArtistListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(): CollectorListFragment {
            return CollectorListFragment()
        }
    }

}