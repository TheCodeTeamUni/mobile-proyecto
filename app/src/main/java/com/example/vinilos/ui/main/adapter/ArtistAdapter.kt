package com.example.vinilos.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinilos.ui.main.view.DetailArtistActivity
import com.example.vinilos.data.model.ArtistResponse
import com.vinylsMobile.vinylsapplication.databinding.ItemLayoutBinding

const val IdArtist = "id"
const val artist = "band"
const val bandText = "Band"
const val musicText = "Musician"

class ArtistAdapter(
    private val artists: ArrayList<ArtistResponse>
) : RecyclerView.Adapter<ArtistAdapter.DataViewHolder>() {

    lateinit var context: Context


    class DataViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val bindPar = binding
        fun bind(artist: ArtistResponse) {
            bindPar.root.apply {
                bindPar.textViewElementTitle.text = artist.name
                bindPar.textElementDetail.text =
                    if (artist.birthDate == null) bandText else musicText
                Glide.with(bindPar.imageElementList.context)
                    .load(artist.image)
                    .into(bindPar.imageElementList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        context = parent.context
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = artists.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindPar.root.setOnClickListener {
            val intent = Intent(context, DetailArtistActivity::class.java).apply {
                putExtra(IdArtist, artists[position].id.toString())
                putExtra(artist, if (artists[position].birthDate == null) bandText else musicText)
            }

            context.startActivity(intent)

        }

        holder.bind(artists[position])
    }

    fun addArtists(artists: List<ArtistResponse>) {
        this.artists.apply {
            clear()
            addAll(artists)
        }

    }
}