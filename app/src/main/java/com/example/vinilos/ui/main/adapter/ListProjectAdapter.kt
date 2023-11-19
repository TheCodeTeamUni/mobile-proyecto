package com.example.vinilos.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinilos.data.model.ProjectResponse
import com.example.vinilos.ui.main.view.AssignedCandidateActivity
import com.example.vinilos.ui.main.view.DetailProjectActivity
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ItemLayoutBinding

const val ID = "id"
const val NAME = "name"

class ListProjectAdapter(
    private val projects: ArrayList<ProjectResponse>
) : RecyclerView.Adapter<ListProjectAdapter.DataViewHolder>() {

    lateinit var context: Context

    class DataViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val bindPar = binding
        fun bind(project: ProjectResponse) {
            bindPar.root.apply {
                bindPar.textViewElementTitle.text = project.nameProject
                println("En esta parte imprime esto: " + project.nameProject)
                bindPar.textElementDetail.text = project.description
                Glide.with(bindPar.imageElementList.context).load(R.drawable.list_project_icon)
                    .into(bindPar.imageElementList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        context = parent.context
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = projects.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindPar.root.setOnClickListener {
            val intent = Intent(context, AssignedCandidateActivity::class.java).apply {
                putExtra(ID, projects[position].id.toString())
                putExtra(NAME, projects[position].nameProject)
            }
            context.startActivity(intent)
        }
        holder.bind(projects[position])
    }

    fun addProjects(projects: List<ProjectResponse>) {
        this.projects.apply {
            clear()
            addAll(projects)
        }
    }
}