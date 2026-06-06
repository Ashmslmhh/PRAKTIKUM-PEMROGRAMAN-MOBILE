package com.example.composeModul3

import androidx.lifecycle.ViewModel
import com.example.composeModul3.data.animeList as animeDataList
import com.example.composeModul3.model.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AnimeViewModel : ViewModel() {
    private val _animeList = MutableStateFlow(animeDataList)
    val animeList: StateFlow<List<Anime>> = _animeList
}