package com.example.musicstore.presentation.search.songs

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.musicstore.R
import com.example.musicstore.presentation.songs.SongRecyclerViewAdapter
import com.example.musicstore.presentation.songs.SongsViewModel
import kotlinx.coroutines.launch

class SongsFragment : Fragment() {

    private val viewModel: SongsViewModel by viewModels {
        SongsViewModel.Factory(arguments?.getString(ID) ?: "")
    }

    private var _mediaPlayer: MediaPlayer? = null
    private val mediaPlayer get() = _mediaPlayer!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mediaPlayer = MediaPlayer()
        return inflater.inflate(R.layout.fragment_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.songs.observe(viewLifecycleOwner) { songs ->
                        with(view as RecyclerView) {
                            adapter = SongRecyclerViewAdapter(
                                songs = songs,
                                songPlayback = { path -> songPlayback(path) })
                        }
                    }
                }
            }
        }
    }

    private fun songPlayback(path: String) {
        mediaPlayer.reset()
        if (path.isEmpty()) return
        mediaPlayer.apply {
            setDataSource(path)
            prepare()
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mediaPlayer?.release()
        _mediaPlayer = null
    }

    companion object {

        const val ID = "id"

        fun newInstance(id: String): SongsFragment {
            return SongsFragment().apply {
                arguments = Bundle(1).apply{
                    putString(ID, id)
                }
            }
        }
    }

}