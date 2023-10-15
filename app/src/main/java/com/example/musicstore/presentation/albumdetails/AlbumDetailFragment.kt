package com.example.musicstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.musicstore.databinding.FragmentAlbumDetailsBinding
import com.example.musicstore.presentation.search.albumdetails.AlbumDetailViewModel
import com.example.musicstore.presentation.search.songs.SongsFragment
import com.example.musicstore.services.ITunesAlbum
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AlbumDetailsFragment : Fragment() {

    val args: AlbumDetailsFragmentArgs by navArgs()

    private val viewModel: AlbumDetailViewModel by viewModels {
        AlbumDetailViewModel.Factory(args.id)
    }

    private var _binding: FragmentAlbumDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindSongsFragment()

        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.album.observe(viewLifecycleOwner) { album ->
                        setTitle(album)
                        bindViewsWith(album)
                    }
                }
            }
        }
    }

    private fun setTitle(album: ITunesAlbum) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "${album.collectionName}"

    }

    private fun bindViewsWith(album: ITunesAlbum) {
        with(binding) {
            albumTitle.text = album.collectionName
            albumArtist.text = album.artistName
            albumReleaseDate.text = album.releaseDate
            albumArtwork.load(album.artworkUrl100) {
                crossfade(true)
                placeholder(com.example.musicstore.R.drawable.baseline_library_music_24)
            }
        }
    }

    private fun bindSongsFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.songsFragment, SongsFragment.newInstance(args.id))
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}