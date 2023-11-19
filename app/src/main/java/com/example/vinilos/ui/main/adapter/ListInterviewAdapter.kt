package com.example.vinilos.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinilos.data.model.InterviewResponse
import com.example.vinilos.ui.main.view.DetailInterviewActivity
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ItemLayoutBinding

const val ID_INTERVIEW = "id"
const val NAME_INTERVIEW = "name"

class ListInterviewAdapter(
    private val interviews: ArrayList<InterviewResponse>
) : RecyclerView.Adapter<ListInterviewAdapter.DataViewHolder>() {

    lateinit var context: Context

    class DataViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val bindPar = binding
        fun bind(interview: InterviewResponse) {
            bindPar.root.apply {
                bindPar.textViewElementTitle.text = interview.nameAspirant + " " + interview.lastNameAspirant
                bindPar.textElementDetail.text = interview.date
                Glide.with(bindPar.imageElementList.context).load(R.drawable.list_interviews_icon)
                    .into(bindPar.imageElementList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        context = parent.context
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = interviews.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindPar.root.setOnClickListener {
            val intent = Intent(context, DetailInterviewActivity::class.java).apply {
                putExtra(ID_INTERVIEW, interviews[position].nameAspirant.toString())
                putExtra(NAME_INTERVIEW, interviews[position].date)
            }
            context.startActivity(intent)
        }
        holder.bind(interviews[position])
    }

    fun addInterviews(interviews: List<InterviewResponse>) {
        this.interviews.apply {
            clear()
            addAll(interviews)
        }
    }
}