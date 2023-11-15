package com.example.vinilos.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinilos.data.model.ProjectResponse
import com.example.vinilos.ui.main.view.DetailProjectActivity
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ItemLayoutBinding

const val ID = "id"
const val NAME = "name"

class HomeAdapter(
    private val albums: ArrayList<ProjectResponse>
) : RecyclerView.Adapter<HomeAdapter.DataViewHolder>() {

    lateinit var context: Context

    class DataViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val bindPar = binding
        fun bind(album: ProjectResponse) {
            bindPar.root.apply {
                bindPar.textViewElementTitle.text = album.nameProject
                bindPar.textElementDetail.text = album.description
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

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindPar.root.setOnClickListener {
            val intent = Intent(context, DetailProjectActivity::class.java).apply {
                putExtra(ID, albums[position].aspirants.toString())
                putExtra(NAME, albums[position].nameProject)
            }
            context.startActivity(intent)
        }
        holder.bind(albums[position])
    }

    fun addAlbums(albums: List<ProjectResponse>) {
        this.albums.apply {
            clear()
            addAll(albums)
        }
    }
}