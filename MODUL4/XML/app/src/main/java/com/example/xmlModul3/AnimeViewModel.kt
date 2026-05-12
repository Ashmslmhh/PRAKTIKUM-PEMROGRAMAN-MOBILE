package com.example.xmlModul3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animexml.model.Anime
import com.example.xmlModul3.data.animeList as animeDataList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class AnimeViewModel(private val username: String) : ViewModel() {
    private val _animeList = MutableStateFlow(animeDataList)
    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    init {
        Timber.d("User : $username - loading anime list")
        Timber.d("Anime list loaded: ${_animeList.value.size} items")
    }
}

class AnimeViewModelFactory(private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}