package com.example.musicstore.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicstore.services.ITunesAlbum
import com.example.musicstore.services.ITunesService
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _results: MutableLiveData<List<ITunesAlbum>> = MutableLiveData(listOf())
    val results: LiveData<List<ITunesAlbum>> get() = _results

    init {
        viewModelScope.launch {
            _results.value = ITunesService.searchAlbums().body()?.results ?: listOf()
        }
    }

    fun newSearch(query: String?) {
        if (query.isNullOrEmpty()) return

        viewModelScope.launch {
            _results.value =  ITunesService.searchAlbums(query).body()?.results ?: listOf()
        }
    }

}