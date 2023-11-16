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
import com.example.vinilos.data.model.InterviewResponse
import com.example.vinilos.ui.base.InterviewViewModelFactory
import com.example.vinilos.ui.base.ViewModelFactory
import com.example.vinilos.ui.main.adapter.ListInterviewAdapter
import com.example.vinilos.ui.main.viewmodel.InterviewViewModel
import com.example.vinilos.utils.Status
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinylsMobile.vinylsapplication.databinding.FragmentInterviewListBinding


/**
 * A simple [Fragment] subclass.
 * Use the [InterviewListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InterviewListFragment : Fragment() {
    private lateinit var interviewViewModel: InterviewViewModel
    private lateinit var adapter: ListInterviewAdapter
    private lateinit var binding: FragmentInterviewListBinding

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = ListInterviewAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        interviewViewModel.getInterviews().observe(viewLifecycleOwner, {
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
        interviewViewModel = ViewModelProviders.of(
            this,
            InterviewViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[InterviewViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInterviewListBinding.inflate(layoutInflater, container, false)
        setupUI()
        bindMenuEvents()
        setupViewModel()
        setupObservers()
        return binding.root
    }

    private fun bindMenuEvents() {
        val createInterviewMenuButton: FloatingActionButton = binding.btnFabCreateInterview
        createInterviewMenuButton.setOnClickListener { view ->
            launchInterviewCreateActivity(view)
        }

    }

    private fun launchInterviewCreateActivity(view: View) {
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
        fun newInstance(): InterviewListFragment {
            return InterviewListFragment()
        }
    }

    private fun retrieveList(interviews: List<InterviewResponse>) {
        adapter.apply {
            addInterviews(interviews)
            notifyDataSetChanged()
        }
    }
}