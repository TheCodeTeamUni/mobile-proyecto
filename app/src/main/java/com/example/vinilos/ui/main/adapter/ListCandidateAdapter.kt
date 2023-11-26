package com.example.vinilos.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinilos.data.model.CandidatesResponse
import com.example.vinilos.ui.main.view.DetailCandidateActivity
import com.vinylsMobile.vinylsapplication.databinding.ItemLayoutBinding

const val ID_CANDIDATE = "id"
const val NAME_CANDIDATE = "name"

class ListCandidateAdapter (
    private val candidates: ArrayList<CandidatesResponse>
) : RecyclerView.Adapter<ListCandidateAdapter.DataViewHolder>() {

    lateinit var context: Context

    class DataViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val bindPar = binding
        fun bind(candidate: CandidatesResponse) {
            bindPar.root.apply {
                bindPar.textViewElementTitle.text = candidate.name + " " + candidate.lastName
                println("En esta parte imprime esto: " + candidate.name)
                bindPar.textElementDetail.text = candidate.telephone
                Glide.with(bindPar.imageElementList.context)
                    .load(candidate.photo)
                    .into(bindPar.imageElementList)
                bindPar.textElementContext.text = candidate.country
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        context = parent.context
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = candidates.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindPar.root.setOnClickListener {
            val intent = Intent(context, DetailCandidateActivity::class.java).apply {
                putExtra(ID_CANDIDATE, candidates[position].idUser.toString())
                putExtra(NAME_CANDIDATE, candidates[position].name)
            }
            context.startActivity(intent)
        }
        holder.bind(candidates[position])
    }

    fun addCandidates(candidates: List<CandidatesResponse>) {
        this.candidates.apply {
            clear()
            addAll(candidates)
        }
    }
}