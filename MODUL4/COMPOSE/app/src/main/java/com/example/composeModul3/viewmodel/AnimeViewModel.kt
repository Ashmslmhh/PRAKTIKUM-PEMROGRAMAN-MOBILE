package com.example.composeModul3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composeModul3.data.animeList as animeDataList
import com.example.composeModul3.model.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class AnimeViewModel(private val username: String) : ViewModel() {
    private val _animeList = MutableStateFlow(animeDataList)
    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    private val _selectedAnime = MutableStateFlow<String?>(null)
    val selectedAnime: StateFlow<String?> = _selectedAnime.asStateFlow()

    private val _toUrl = MutableStateFlow<String?>(null)
    val toUrl: StateFlow<String?> = _toUrl.asStateFlow()

    init {
        Timber.d("User : $username - loading anime list...")
        Timber.d("Anime list loaded: ${_animeList.value.size} items")
    }

    fun onAnimeSelected(title: String) {
        Timber.d("Anime selected: $title")
        _selectedAnime.value = title
    }

    fun clearSelection(){
        _selectedAnime.value = null
    }

    fun onLinkClicked(url: String) {
        Timber.d("Link clicked: $url")
        _toUrl.value = url
    }

    fun onUrlNavigated() {
        _toUrl.value = null
    }
}

class AnimeViewModelFactory(private val username: String) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}