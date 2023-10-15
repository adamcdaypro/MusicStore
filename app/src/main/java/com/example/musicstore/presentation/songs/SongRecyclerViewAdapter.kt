package com.example.musicstore.presentation.songs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.musicstore.R
import com.example.musicstore.databinding.FragmentSongsListItemBinding
import com.example.musicstore.presentation.search.songs.SongsFragment
import com.example.musicstore.services.ITunesSong
import com.example.musicstore.utilities.TimeUtility
import java.util.concurrent.TimeUnit

class SongRecyclerViewAdapter(
    private val songs: List<ITunesSong>,
    private val songPlayback: (path: String) -> Unit
) : RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder>() {

    private var playingSongPosition: Int = INVALID_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentSongsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songs[position]
        with(holder) {
            name.text = song.trackName
            explicitness.visibility =
                if (song.trackExplicitness == "notExplicit") View.GONE else View.VISIBLE
            trackLength.text = TimeUtility.formatTimeToHoursMinutesSecondsFrom(song.trackTimeMillis.toLong())
            resetStopAndPlayImages(position, stopSongImage, playSongImage)
            playSongImage.setOnClickListener {
                songPlayback(startPlaybackAndGetValidUrl(position, song))
                resetStopAndPlayImages(position, stopSongImage, playSongImage)
            }
            stopSongImage.setOnClickListener {
                songPlayback(stopPlaybackAndGetInvalidUrl(position, song))
                resetStopAndPlayImages(position, stopSongImage, playSongImage)

            }
        }
    }

    private fun songIsPlaying(position: Int): Boolean {
        return playingSongPosition == position
    }

    private fun resetStopAndPlayImages(position: Int, stopImage: ImageView, playImage: ImageView) {
        val isPlaying = songIsPlaying(position)
        stopImage.visibility = if (isPlaying) View.VISIBLE else View.INVISIBLE
        playImage.visibility = if (isPlaying) View.INVISIBLE else View.VISIBLE
    }

    private fun stopPlaybackAndGetInvalidUrl(position: Int, song: ITunesSong): String {
        setPlayingSongPosition(INVALID_POSITION)
        return INVALID_URL
    }

    private fun startPlaybackAndGetValidUrl(position: Int, song: ITunesSong): String {
        notifyItemChanged(playingSongPosition)
        setPlayingSongPosition(position)
        return song.previewUrl ?: ""
    }

    private fun setPlayingSongPosition(position: Int) {
        playingSongPosition = position
    }

    companion object {

        private const val INVALID_POSITION = -1
        private const val INVALID_URL = ""

    }

    inner class ViewHolder(binding: FragmentSongsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val explicitness = binding.explicitness
        val trackLength = binding.time
        val playSongImage = binding.playSongImage
        val stopSongImage = binding.stopSongImage

    }

}