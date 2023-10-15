package com.example.musicstore.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.musicstore.R
import com.example.musicstore.databinding.FragmentSearchResultsListItemBinding
import com.example.musicstore.services.ITunesAlbum

class SearchRecyclerViewAdapter(
    private val values: List<ITunesAlbum>,
    private val onClick: (id: String) -> Unit
) : RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentSearchResultsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = values[position]
        holder.artistName.text = album.artistName
        holder.collectionName.text = album.collectionName
        holder.albumArtwork.load(album.artworkUrl60) {
            crossfade(true)
            placeholder(R.drawable.baseline_library_music_24)
        }
        holder.itemView.setOnClickListener { onClick(album.collectionId.toString()) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentSearchResultsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val artistName: TextView = binding.artistName
        val collectionName: TextView = binding.collectionName
        val albumArtwork: ImageView = binding.albumArtwork

        override fun toString(): String {
            return super.toString() + artistName + " '" + collectionName + "'"
        }
    }

}