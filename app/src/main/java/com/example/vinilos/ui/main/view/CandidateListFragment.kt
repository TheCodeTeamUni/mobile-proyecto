package com.example.vinilos.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vinilos.data.api.ApiHelper
import com.example.vinilos.data.api.RetrofitBuilder
import com.example.vinilos.data.model.CandidatesResponse
import com.example.vinilos.ui.main.adapter.ListCandidateAdapter
import com.example.vinilos.ui.main.viewmodel.CandidateViewModel
import com.example.vinilos.ui.main.viewmodel.CandidateViewModelFactory
import com.example.vinilos.utils.Status
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinylsMobile.vinylsapplication.databinding.FragmentCandidateListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CandidateListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CandidateListFragment : Fragment() {
    private lateinit var candidateViewModel: CandidateViewModel
    private lateinit var adapter: ListCandidateAdapter
    private lateinit var binding: FragmentCandidateListBinding

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = ListCandidateAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        candidateViewModel.getCandidates().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { candidate -> retrieveList(candidate) }
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
        candidateViewModel = ViewModelProviders.of(
            this,
            CandidateViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[CandidateViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCandidateListBinding.inflate(layoutInflater, container, false)
        setupUI()
        bindMenuEvents()
        setupViewModel()
        setupObservers()
        return binding.root
    }

    private fun bindMenuEvents() {
        val createCandidateMenuButton: FloatingActionButton = binding.btnFabCreateCandidate
        createCandidateMenuButton.setOnClickListener { view ->
            launchCandidateCreateActivity(view)
        }

    }

    private fun launchCandidateCreateActivity(view: View) {
        val intent = Intent(activity, CreateInterviewActivity::class.java)
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
        fun newInstance(): CandidateListFragment {
            return CandidateListFragment()
        }
    }

    private fun retrieveList(candidates: List<CandidatesResponse>) {
        adapter.apply {
            addCandidates(candidates)
            notifyDataSetChanged()
        }
    }
}