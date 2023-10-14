package com.example.vinilos.ui.main.adapter


import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.example.vinilos.data.model.ArtistResponse
import com.vinylsMobile.vinylsapplication.databinding.ActivityDetailArtistBinding
import java.text.DateFormat
import java.util.*

class DetailArtistAdapter(private val artistDetail: ArtistResponse, private val isBand: Boolean) {

    @SuppressLint("SetTextI18n")
    fun adaptData(binding: ActivityDetailArtistBinding) {
        Glide.with(binding.imageViewArtist.context)
            .load(artistDetail.image)
            .into(binding.imageViewArtist)
        binding.textContentArtist.text = "${artistDetail.name} (${textBandArtist(binding)}) "
        binding.textContentDate.text =
            if (isBand) formatDate(artistDetail.creationDate) else formatDate(artistDetail.birthDate)
        binding.textContentDescription.text = artistDetail.description
        binding.listTexAlbums.text = adaptStringAbums(binding)
        binding.listTexPrizes.text = adaptStringPrizes(binding)
//        adaptTablePrizes(binding)
//        adaptTableALbums(binding)

    }

    private fun textBandArtist(binding: ActivityDetailArtistBinding):String{
        var resp = ""
        resp = if (isBand) "Banda" else "Musico"
        return resp
    }

    private fun formatDate(date: Date?): String {
        return DateFormat.getDateInstance(DateFormat.LONG).format(date).toString()
    }


//    private fun adaptTableALbums(binding: ActivityDetailArtistBinding) {
//        val row = TableRow(binding.root.context)
//
//        val nombreAlbum = TextView(binding.root.context)
//        val generoAlbum = TextView(binding.root.context)
//
//        for (i in artistDetail.albums) {
//            nombreAlbum.text = i.name
//            generoAlbum.text = i.genre
//
//            row.addView(nombreAlbum)
//            row.addView(generoAlbum)
//            binding.tableMain.addView(row)
//
//        }
//    }



    private fun adaptStringAbums(binding: ActivityDetailArtistBinding): String {
        val sb = StringBuilder()
        for (i in artistDetail.albums) {
            sb.append( "- ${i.name} (${DateFormat.getDateInstance(DateFormat.MEDIUM).format(i.releaseDate)})" + "\n")
        }
        return sb.toString()
    }
    private fun adaptStringPrizes(binding: ActivityDetailArtistBinding): String {
        val sb = StringBuilder()
        for (i in artistDetail.performerPrizes) {
            sb.append( "- ${formatDate(i.premiationDate)}" + "\n")
        }
        return sb.toString()
    }

//    private fun adaptTablePrizes(binding: ActivityDetailArtistBinding) {
//        val row = TableRow(binding.root.context)
//
//        val fecha = TextView(binding.root.context)
//        for (i in artistDetail.performerPrizes) {
//            fecha.text = i.premiationDate.toString()
//
//            row.addView(fecha)
//            binding.tablePrizes.addView(row)
//        }
//    }
}