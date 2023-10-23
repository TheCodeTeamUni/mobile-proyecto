package com.example.vinilos.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.vinilos.data.model.CollectorResponse
import com.example.vinilos.ui.main.view.DetailCollectorActivity
import com.vinylsMobile.vinylsapplication.R
import com.vinylsMobile.vinylsapplication.databinding.ItemLayoutBinding

class CollectorAdapter(
    private val collectors: ArrayList<CollectorResponse>
) : RecyclerView.Adapter<CollectorAdapter.DataViewHolder>() {

    lateinit var context: Context

    class DataViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val bindPar = binding
        fun bind(collector: CollectorResponse) {
            bindPar.root.apply {
                bindPar.textViewElementTitle.text = collector.name
                bindPar.textElementDetail.text = collector.email
                Glide.with(bindPar.imageElementList.context)
                    .load(R.drawable.ic_baseline_construction_collector)
                    .error(R.drawable.ic_baseline_construction_collector)
                    .placeholder(R.drawable.ic_baseline_construction_collector)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(bindPar.imageElementList)
                bindPar.imageElementList.setColorFilter(
                    Color.rgb(93, 193, 185),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        context = parent.context
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = collectors.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindPar.root.setOnClickListener {
            val intent = Intent(context, DetailCollectorActivity::class.java).apply {
                putExtra(IdCollector, collectors[position].id.toString())
            }

            context.startActivity(intent)

        }

        holder.bind(collectors[position])
    }

    fun addCollectors(collectors: List<CollectorResponse>) {
        this.collectors.apply {
            clear()
            addAll(collectors)
        }

    }
}