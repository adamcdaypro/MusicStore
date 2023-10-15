package com.example.musicstore.presentation.songs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.musicstore.services.ITunesService
import com.example.musicstore.services.ITunesSong
import kotlinx.coroutines.launch

class SongsViewModel(id: String) : ViewModel() {

    private val _songs: MutableLiveData<List<ITunesSong>> = MutableLiveData()
    val songs: LiveData<List<ITunesSong>> get() = _songs

    init {
        viewModelScope.launch {
            _songs.value = ITunesService.lookupSongs(id).body()?.results?.drop(1) ?: listOf()
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val id: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SongsViewModel(id) as T
        }

    }
}