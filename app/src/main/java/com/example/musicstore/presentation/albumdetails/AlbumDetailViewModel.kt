package com.example.musicstore.presentation.search.albumdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.musicstore.services.ITunesAlbum
import com.example.musicstore.services.ITunesService
import kotlinx.coroutines.launch

class AlbumDetailViewModel(private val id: String): ViewModel() {

    private val _album: MutableLiveData<ITunesAlbum> = MutableLiveData()
    val album: LiveData<ITunesAlbum> get() = _album

    init {
        viewModelScope.launch {
            _album.value = ITunesService.lookupAlbumById(id).body()?.results?.get(0) ?: ITunesAlbum()
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(val id: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AlbumDetailViewModel(id) as T
        }
    }

}